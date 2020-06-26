package com.icommerce.nab.entity.order;

import com.icommerce.nab.entity.BaseEntity;
import com.icommerce.nab.entity.payment.Payment;
import com.icommerce.nab.entity.payment.PaymentType;
import com.icommerce.nab.entity.user.Account;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for mapping order object to database
 */
@Entity
@Table(name = "ICOMMERCE_ORDER",
        indexes = {
                @Index(name = "INDEX_ORDER_NUM", columnList = "ORDER_NUM", unique = true),
        })
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3450731148249277564L;

    @Column(name = "ORDER_NUM", nullable = false, unique = true, length = 100)
    private String orderNum;

    @Column(name = "ORDER_DATE", nullable = false)
    private Date orderDate;

    @Column(name = "CONTACT_PHONE", nullable = false)
    private String contactPhone;

    @Column(name = "SHIPPING_ADDRESS", nullable = false)
    private String shippingAddress;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private double totalPrice;

    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus status;

    @Column(name = "PAYMENT_TYPE", nullable = false)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> items = new HashSet<>();

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public Set<LineItem> getItems() {
        return items;
    }

    public void setItems(Set<LineItem> items) {
        this.items = items;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
