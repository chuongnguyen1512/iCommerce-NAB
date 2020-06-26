package com.icommerce.nab.order.service.controller;

import com.icommerce.nab.common.controller.BaseController;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.order.service.service.DefaultOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Restful Controller for order-service
 */
@RestController
public class OrderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private DefaultOrderService orderService;

    @Autowired
    public OrderController(@Autowired DefaultOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Processing orders
     *
     * @param transactionId transaction id
     * @param orders list of orders
     * @return response entity object
     */
    @RequestMapping(value = "/processOrders", method = RequestMethod.POST)
    public ResponseEntity processOrders(@RequestHeader(name = "transaction_id") String transactionId,
                                        @RequestBody List<OrderDTO> orders) {

        return processRestfulRequest(transactionId, new ProcessRequestConsumer() {
            @Override
            public boolean isInvalidRequest() {
                return CollectionUtils.isEmpty(orders);
            }

            @Override
            public Object executeRequest() {
                LOGGER.info("Receiving process orders request with orders size: {}", orders.size());
                return orderService.processOrders(transactionId, orders);
            }
        });
    }

}
