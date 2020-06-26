package com.icommerce.nab.dto.restful;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Http response for iCommerce common data
 * @param <T>
 */
public class ICommerceResponse<T> {

    private String transactionId;

    private String uuid;

    @JsonProperty(value="isSuccess")
    private boolean isSuccess;

    private String errorMessage;

    private List<T> responses;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<T> getResponses() {
        return responses;
    }

    public void setResponses(List<T> responses) {
        this.responses = responses;
    }
}
