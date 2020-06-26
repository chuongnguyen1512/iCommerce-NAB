package com.icommerce.nab.dto.dto;

import java.io.Serializable;

/**
 * DTO class for order item object
 */
public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 5002157435516336177L;

    private int quantity;
    private String productNumber;
    private double price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
