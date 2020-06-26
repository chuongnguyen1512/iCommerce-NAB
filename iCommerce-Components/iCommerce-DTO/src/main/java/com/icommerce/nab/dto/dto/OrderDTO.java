package com.icommerce.nab.dto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * DTO class for order object
 */
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 5801951190202344274L;

    private String orderNum;

    @NotBlank(message = "Payment type cannot be blank")
    @Pattern(regexp = "^(?i)(Cash|Credit|Internet Banking)$", message = "Payment type should follow ${regexp}")
    private String paymentType;

    @NotBlank(message = "Username cannot be blank}")
    private String userName;

    private Date orderDate;

    @NotBlank(message = "Contact phone cannot be blank")
    private String contactPhone;

    @NotBlank(message = "Shipping Address cannot be blank")
    private String shippingAddress;

    @Min(value = 1, message = "Total Price cannot be less than 1")
    @NotNull(message = "Total Price cannot be null")
    private double totalPrice;

    private List<OrderItemDTO> items;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return orderNum.equals(orderDTO.orderNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNum);
    }
}
