package com.icommerce.nab.entity.payment;

import com.icommerce.nab.entity.BaseEntity;
import com.icommerce.nab.entity.order.Order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity class for mapping payment object to database
 */
@Entity
@Table(name = "ICOMMERCE_PAYMENT",
        indexes = {
                @Index(name = "INDEX_PAYMENT_NUM", columnList = "PAYMENT_NUM", unique = true),
        })
public class Payment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5580068308940998797L;

    @Column(name = "PAYMENT_NUM", nullable = false, unique = true, length = 100)
    private String paymentNum;

    @Column(name = "ISSUED_DATE", nullable = false)
    private Date issuedDate;

    @Column(name = "PAYMENT_DETAILS")
    private String details;

    @Column(name = "PAYMENT_PRICE", nullable = false)
    private double payment;

    @OneToOne(mappedBy = "payment")
    private Order order;

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
