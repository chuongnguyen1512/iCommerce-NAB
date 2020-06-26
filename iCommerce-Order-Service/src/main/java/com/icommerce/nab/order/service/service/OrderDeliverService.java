package com.icommerce.nab.order.service.service;

import com.icommerce.nab.common.restful.BaseCallRestfulService;
import com.icommerce.nab.common.restful.DeliverCallRestfulService;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.entity.order.OrderStatus;
import com.icommerce.nab.order.service.config.DeliverServiceConfig;
import com.icommerce.nab.repository.OrderRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.icommerce.nab.order.service.config.ApplicationConfig.BEAN_DELIVER_CALL_RESTFUL_SERVICE;

/**
 * Service class handling send orders to deliver-service
 */
@Service
public class OrderDeliverService implements DefaultOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDeliverService.class);

    private final DeliverCallRestfulService deliverRestfulService;
    private final DeliverServiceConfig deliverServiceConfig;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDeliverService(@Autowired OrderRepository orderRepository,
                               @Qualifier(BEAN_DELIVER_CALL_RESTFUL_SERVICE) BaseCallRestfulService deliverRestfulService,
                               @Autowired DeliverServiceConfig deliverServiceConfig) {
        this.orderRepository = orderRepository;
        this.deliverRestfulService = (DeliverCallRestfulService) deliverRestfulService;
        this.deliverServiceConfig = deliverServiceConfig;
    }

    /**
     * Process orders. However, method is deprecated
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendDeliverOrders(String transactionId, List<OrderDTO> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }

        ResponseEntity response = deliverRestfulService.executePOSTRequest(deliverServiceConfig.getDeliverOrdersUrl(),
                deliverRestfulService.prepareRequestForDeliverOrders(transactionId, orders));

        if (Objects.isNull(response) || !response.hasBody()) {
            // Keep status of order still is Pending. Cron job will handle later
            LOGGER.error("Sending deliver request to deliver service but not successful");
            return;
        }

        List<String> orderNums = orders.stream().map(OrderDTO::getOrderNum).collect(Collectors.toList());
        orderRepository.updateOrderStatus(OrderStatus.PROCESSED, orderNums);
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
}
