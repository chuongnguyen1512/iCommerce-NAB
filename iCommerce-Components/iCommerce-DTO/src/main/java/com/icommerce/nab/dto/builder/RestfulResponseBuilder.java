package com.icommerce.nab.dto.builder;

import com.icommerce.nab.dto.restful.ICommerceResponse;

import java.util.List;
import java.util.UUID;

/**
 * Builder for Restful request
 */
public class RestfulResponseBuilder {

    private RestfulResponseBuilder() {
    }

    /**
     * Build success {@link ICommerceResponse}
     *
     * @param transactionID transaction id
     * @param responses collection of data responses
     * @param <T> response type
     * @return success {@link ICommerceResponse} object
     */
    public static <T> ICommerceResponse buildSuccessResponse(String transactionID, List<T> responses) {
        ICommerceResponse response = new ICommerceResponse();
        response.setUuid(UUID.randomUUID().toString());
        response.setTransactionId(transactionID);
        response.setSuccess(true);
        response.setResponses(responses);
        return response;
    }

    /**
     * Build error {@link ICommerceResponse}
     *
     * @param transactionID transaction id
     * @param responses collection of data responses
     * @param <T> response type
     * @return error {@link ICommerceResponse} object
     */
    public static <T> ICommerceResponse buildErrorResponse(String transactionID, String errorMsg, List<T> responses) {
        ICommerceResponse response = new ICommerceResponse();
        response.setUuid(UUID.randomUUID().toString());
        response.setTransactionId(transactionID);
        response.setSuccess(false);
        response.setErrorMessage(errorMsg);
        response.setResponses(responses);
        return response;
    }
}
