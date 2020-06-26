package com.icommerce.nab.dto.builder;

import com.icommerce.nab.dto.restful.ICommerceRequest;

import java.util.List;
import java.util.UUID;

/**
 * Builder for Restful request
 */
public class RestfulRequestBuilder {

    private RestfulRequestBuilder() {
    }

    /**
     * Building {@link ICommerceRequest} object
     *
     * @param transactionID transaction id
     * @param data collection of data
     * @param <T> type of data
     * @return {@link ICommerceRequest} object
     */
    public static <T> ICommerceRequest buildICommerceRequest(String transactionID, List<T> data) {
        ICommerceRequest request = new ICommerceRequest();
        request.setUuid(UUID.randomUUID().toString());
        request.setTransactionId(transactionID);
        request.setData(data);
        return request;
    }
}
