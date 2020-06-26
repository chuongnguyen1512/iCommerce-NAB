package com.icommerce.nab.deliver.service.service;

import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;

import java.util.List;

/**
 * Interface for handling deliver request
 */
public interface DefaultDeliverService {

    /**
     * Processing deliver order requests
     *
     * @param transactionId transaction id
     * @param orders list of deliver orders
     * @return {@link ICommerceResponse} object
     */
    ICommerceResponse deliverOrders(String transactionId, List<OrderDTO> orders);
}
