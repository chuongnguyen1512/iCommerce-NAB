package com.icommerce.nab.order.service.service;

import com.icommerce.nab.common.transform.BaseTransform;
import com.icommerce.nab.common.transform.OrderTransform;
import com.icommerce.nab.dto.builder.RestfulResponseBuilder;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.dto.OrderItemDTO;
import com.icommerce.nab.dto.dto.OrderStatusDTO;
import com.icommerce.nab.dto.dto.PaymentTypeDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.dto.restful.ICommereException;
import com.icommerce.nab.entity.order.Order;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.entity.user.Account;
import com.icommerce.nab.order.service.controller.OrderController;
import com.icommerce.nab.order.service.demo.DemoDataUtils;
import com.icommerce.nab.repository.AccountRepository;
import com.icommerce.nab.repository.OrderRepository;
import com.icommerce.nab.repository.ProductRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.icommerce.nab.order.service.config.ApplicationConfig.BEAN_ORDER_TRANSFORM_SERVICE;

/**
 * Service class for handling orders requests
 */
@Service
@Primary
public class OrderService implements DefaultOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderPaymentService orderPaymentService;
    private final OrderDeliverService orderDeliverService;
    private final OrderTransform orderTransform;

    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private int executorServicePoolSize = 3;

    @Autowired
    public OrderService(@Autowired OrderRepository orderRepository,
                        @Autowired ProductRepository productRepository,
                        @Autowired AccountRepository accountRepository,
                        @Autowired OrderDeliverService orderDeliverService,
                        @Autowired OrderPaymentService orderPaymentService,
                        @Qualifier(BEAN_ORDER_TRANSFORM_SERVICE) BaseTransform<OrderDTO, Order> orderTransform,
                        @Value("${executor.service.poolsize}") int executorServicePoolSize) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.executorServicePoolSize = executorServicePoolSize;
        this.orderTransform = (OrderTransform) orderTransform;
        this.orderDeliverService = orderDeliverService;
        this.orderPaymentService = orderPaymentService;
        accountRepository.save(DemoDataUtils.createSimpleAccount());
    }

    /**
     * Handling processing orders by saving orders and send to Deliver-Service to deliver orders to customers.
     * If order is online payment, send to Payment-Service first (Not implemented yet)
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     * @return {@link ICommerceResponse} object
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ICommerceResponse processOrders(String transactionId, List<OrderDTO> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return RestfulResponseBuilder.buildErrorResponse(transactionId, "Orders are empty or null", null);
        }

        // Verify order request data
        LOGGER.info("Verify order request data");
        for (OrderDTO order : orders) {
            String errorMsg = verifyOrder(order);
            if (StringUtils.isNotBlank(verifyOrder(order))) {
                LOGGER.warn("Request contained data is not valid: {}", errorMsg);
                return RestfulResponseBuilder.buildErrorResponse(transactionId, errorMsg, Arrays.asList(order));
            }
        }

        // Transform to process object
        List<Order> processingOrders = orders.stream()
                .map(orderDTO -> orderTransform.transform(orderDTO, OrderStatusDTO.PENDING, new OrderTransform.LookupOrderData() {
                    @Override
                    public Account lookupAccount(String username) {
                        return accountRepository.findAccountByUsername(username);
                    }

                    @Override
                    public Product lookupProduct(String prodNum) {
                        return productRepository.findProductByProductNum(prodNum);
                    }
                }))
                .collect(Collectors.toList());

        // Process orders
        return handlingOrders(transactionId, processingOrders, orders);
    }

    /**
     * Sending orders to deliver-service
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     */
    @Override
    public void sendDeliverOrders(String transactionId, List<OrderDTO> orders) {
    }

    /**
     * Sending orders to payment service
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     */
    @Override
    public void sendPaymentOrders(String transactionId, List<OrderDTO> orders) {
    }

    /**
     * - For cash orders, send to deliver-service order to ship items and send to payment-service to create payment.
     * - For online payment, send to payment-service to create payment first.
     * Then payment-service send to deliver-service later.
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     * @param orderDTOs     list of order dtos
     * @return {@link ICommerceResponse} object
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private ICommerceResponse handlingOrders(String transactionId, List<Order> orders, List<OrderDTO> orderDTOs) {
        List<Order> savedOrders = orderRepository.saveAll(orders);
        List<OrderDTO> savedOrderDTOs = savedOrders.stream().map(order -> orderTransform.transformBack(order)).collect(Collectors.toList());

        /**
         * - For cash orders, send to deliver-service order to ship items and send to payment-service to create payment.
         * - For online payment, send to payment-service to create payment first.
         * Then payment-service send to deliver-service later.
         */
        Executors.newFixedThreadPool(executorServicePoolSize).execute(() -> {
            handlingCashOrders(transactionId, savedOrderDTOs);
            handlingOnlinePaymentOrders(transactionId, savedOrderDTOs);
        });

        return RestfulResponseBuilder.buildSuccessResponse(transactionId, savedOrderDTOs);
    }

    private void handlingCashOrders(String transactionId, List<OrderDTO> savedOrderDTOs) {
        List<OrderDTO> cashOrders = savedOrderDTOs.stream().filter(y -> {
            PaymentTypeDTO paymentType = PaymentTypeDTO.getPaymentTypeDTO(y.getPaymentType());
            return Objects.nonNull(paymentType) && PaymentTypeDTO.CASH.equals(paymentType);
        }).collect(Collectors.toList());

        LOGGER.info("Sending to deliver-service for cash orders size: {}", cashOrders.size());
        orderDeliverService.sendDeliverOrders(transactionId, cashOrders);

        LOGGER.info("Sending to payment-service for cash orders size: {}", cashOrders.size());
        orderPaymentService.sendPaymentOrders(transactionId, cashOrders);
    }

    private void handlingOnlinePaymentOrders(String transactionId, List<OrderDTO> savedOrderDTOs) {
        List<OrderDTO> onlinePaymentOrders = savedOrderDTOs.stream().filter(y -> {
            PaymentTypeDTO paymentType = PaymentTypeDTO.getPaymentTypeDTO(y.getPaymentType());
            return Objects.nonNull(paymentType) && !PaymentTypeDTO.CASH.equals(paymentType);
        }).collect(Collectors.toList());

        LOGGER.info("Sending to payment-service for online payment orders size: {}", onlinePaymentOrders.size());
        //TODO: Implement later for online payment
    }

    /**
     * Verify order data is valid or not
     *
     * @param order order info
     * @return error message
     */
    private String verifyOrder(OrderDTO order) {
        String errorMsg = fieldValidate(order);
        if (StringUtils.isNotBlank(errorMsg)) {
            return errorMsg;
        }
        return customValidate(order);
    }

    private String customValidate(OrderDTO order) {
        String errorMsg = "";
        try {
            Account account = accountRepository.findAccountByUsername(order.getUserName());
            if (Objects.isNull(account)) {
                errorMsg = "Username is not existing";
                return errorMsg;
            }

            List<String> productNums = order.getItems().stream().map(OrderItemDTO::getProductNumber).collect(Collectors.toList());
            List<Object[]> existingProductNums = productRepository.findExistingProductsByProductNums(productNums);

            if (CollectionUtils.isEmpty(existingProductNums)) {
                errorMsg = "Product numbers are not existing";
                return errorMsg;
            }

        } catch (Exception e) {
            String exMsg = String.format("Cannot verify data info is existing in system");
            LOGGER.error(exMsg);
            throw new ICommereException(exMsg, e);
        }
        return null;
    }

    private String fieldValidate(OrderDTO order) {
        StringBuilder errorMsg = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        String delimiterCharacter = ",";
        violations.stream().filter(x -> StringUtils.isNotBlank(x.getMessage())).forEach(x -> errorMsg.append(x.getMessage()).append(delimiterCharacter + " "));
        String trimMsg = StringUtils.trim(errorMsg.toString());
        return trimMsg.endsWith(delimiterCharacter) ? StringUtils.substring(trimMsg, 0, trimMsg.length() - 1) : trimMsg;
    }
}
