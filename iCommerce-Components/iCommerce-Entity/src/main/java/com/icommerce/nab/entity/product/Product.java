package com.icommerce.nab.entity.product;

import com.icommerce.nab.entity.BaseEntity;
import com.icommerce.nab.entity.order.LineItem;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for mapping product object to database
 */
@Entity
@Table(name = "ICOMMERCE_PRODUCT",
        indexes = {
                @Index(name = "INDEX_PROD_NUM", columnList = "PROD_NUM", unique = true)
        })
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5807064597096435260L;

    @Column(name = "PROD_NUM", nullable = false, unique = true, length = 100)
    private String prodNum;

    @Column(name = "PROD_NAME", nullable = false)
    private String prodName;

    @Column(name = "SUPPLIER_NAME", nullable = false)
    private String supplierName;

    @Column(name = "PROD_DESCRIPTION")
    private String prodDescript;

    @Column(name = "PRICE", nullable = false)
    @Audited
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_STATUS", nullable = false)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LineItem> orderItems = new HashSet<>();

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProdDescript() {
        return prodDescript;
    }

    public void setProdDescript(String prodDescript) {
        this.prodDescript = prodDescript;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<LineItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<LineItem> orderItems) {
        this.orderItems = orderItems;
    }
}
