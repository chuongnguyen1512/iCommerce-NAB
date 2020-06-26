package com.icommerce.nab.order.service.service;

import com.icommerce.nab.common.restful.BaseCallRestfulService;
import com.icommerce.nab.common.restful.PaymentCallRestfulService;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.order.service.config.PaymentServiceConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.icommerce.nab.order.service.config.ApplicationConfig.BEAN_PAYMENT_CALL_RESTFUL_SERVICE;

/**
 * Service class handling send orders to payment-service
 */
@Service
public class OrderPaymentService implements DefaultOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentService.class);

    private final PaymentCallRestfulService paymentCallRestfulService;
    private final PaymentServiceConfig deliverServiceConfig;

    @Autowired
    public OrderPaymentService(@Qualifier(BEAN_PAYMENT_CALL_RESTFUL_SERVICE) BaseCallRestfulService paymentCallRestfulService,
                               @Autowired PaymentServiceConfig deliverServiceConfig) {
        this.paymentCallRestfulService = (PaymentCallRestfulService) paymentCallRestfulService;
        this.deliverServiceConfig = deliverServiceConfig;
    }

    /**
     * Process orders
     *
     * @param transactionId transaction id
     * @param orders        list of orders
     * @return {@link ICommerceResponse} object
     */
    @Override
    public ICommerceResponse processOrders(String transactionId, List<OrderDTO> orders) {
        return null;
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
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }

//        ResponseEntity response = paymentCallRestfulService.executePOSTRequest(deliverServiceConfig.getPayCashOrdersUrl(),
//                paymentCallRestfulService.prepareRequestForCashOrders(transactionId, orders));
//
//        if (Objects.isNull(response) || !response.hasBody()) {
//            // TODO: Need retry mechanism right here for cash orders failed to create payment
//            LOGGER.error("Sending deliver request to deliver service but not successful");
//            return;
//        }
    }
}
