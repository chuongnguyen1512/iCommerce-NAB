package com.icommerce.nab.deliver.service.service;

import com.icommerce.nab.dto.builder.RestfulResponseBuilder;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.entity.order.OrderStatus;
import com.icommerce.nab.repository.OrderRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for handling deliver orders requests by communicate with 3rd APIs
 */
@Service
public class DeliverService implements DefaultDeliverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverService.class);

    private OrderRepository orderRepository;

    @Autowired
    public DeliverService(@Autowired OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Processing deliver order requests
     *
     * @param transactionId transaction id
     * @param orders        list of deliver orders
     * @return {@link ICommerceResponse} object
     */
    @Override
    public ICommerceResponse deliverOrders(String transactionId, List<OrderDTO> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return RestfulResponseBuilder.buildErrorResponse(transactionId, "Orders are empty or null", null);
        }

        // Verify data
        for (OrderDTO order : orders) {
            String errorMsg = verifyOrder(order);
            if (StringUtils.isNotBlank(verifyOrder(order))) {
                LOGGER.warn("Request contained data is not valid: {}", errorMsg);
                return RestfulResponseBuilder.buildErrorResponse(transactionId, errorMsg, Arrays.asList(order));
            }
        }

        // Handling deliver orders
        handlingDeliverOrders(transactionId, orders);
        return RestfulResponseBuilder.buildSuccessResponse(transactionId, orders);
    }

    /**
     * Communicate with 3rd APIs to ship order items to customer.
     * After that, changing order to Delivering status
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void handlingDeliverOrders(String transactionId, List<OrderDTO> orders) {
        //TODO: Call third party APIs to deliver order to customer

        // Change to deliver status
        LOGGER.info("Updating orders to {}", OrderStatus.DELIVERING);
        List<String> orderNums = orders.stream().map(OrderDTO::getOrderNum).collect(Collectors.toList());
        int updatedOrdersNum = orderRepository.updateOrderStatus(OrderStatus.DELIVERING, orderNums);

        LOGGER.info("Updated orders {} successfully to {}", updatedOrdersNum, OrderStatus.DELIVERING);
    }

    /**
     * Verify order data is valid or not
     *
     * @param orders list of orders
     * @return error message
     */
    private String verifyOrder(OrderDTO orders) {
        StringBuilder stringBuilder = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orders);

        String delimiterCharacter = ",";
        violations.stream().filter(x -> StringUtils.isNotBlank(x.getMessage())).forEach(x -> stringBuilder.append(x.getMessage()).append(delimiterCharacter + " "));
        String trimMsg = StringUtils.trim(stringBuilder.toString());
        return trimMsg.endsWith(delimiterCharacter) ? StringUtils.substring(trimMsg, 0, trimMsg.length() - 1) : trimMsg;
    }
}
