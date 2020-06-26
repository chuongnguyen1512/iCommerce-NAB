package com.icommerce.nab.order.service.service;

import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;

import java.util.List;

/**
 * Default service for handling orders
 */
public interface DefaultOrderService {

    /**
     * Process orders
     *
     * @param transactionId transaction id
     * @param orders list of orders
     * @return {@link ICommerceResponse} object
     */
    ICommerceResponse processOrders(String transactionId, List<OrderDTO> orders);

    /**
     * Sending orders to deliver-service
     *
     * @param transactionId transaction id
     * @param orders list of orders
     */
    void sendDeliverOrders(String transactionId, List<OrderDTO> orders);

    /**
     * Sending orders to payment service
     *
     * @param transactionId transaction id
     * @param orders list of orders
     */
    void sendPaymentOrders(String transactionId, List<OrderDTO> orders);
}
