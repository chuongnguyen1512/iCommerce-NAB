package com.icommerce.nab.dto.restful;

import java.util.List;

/**
 * Http request for iCommerce common data
 * @param <T>
 */
public class ICommerceRequest<T> {

    private String transactionId;
    private String uuid;
    private List<T> data;

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
