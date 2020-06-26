package com.icommerce.nab.entity.order;

import com.icommerce.nab.entity.BaseEntity;
import com.icommerce.nab.entity.product.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity class for mapping order line item to database
 */
@Entity
@Table(name = "ICOMMERCE_LINEITEM")
public class LineItem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2058832975691082391L;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
