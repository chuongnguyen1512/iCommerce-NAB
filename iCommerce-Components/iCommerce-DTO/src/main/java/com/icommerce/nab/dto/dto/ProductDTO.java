package com.icommerce.nab.dto.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * DTO class for product object
 */
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -4125776530807928856L;

    private String prodNum;

    @NotBlank(message = "Product name cannot be blank")
    private String prodName;

    @NotBlank(message = "Supplier name cannot be blank")
    private String supplierName;

    private String prodDescript;

    @Min(value = 1, message = "Product price cannot be less than 1")
    @NotNull(message = "Product price cannot be null")
    private double price;

    @NotBlank(message = "Product status cannot be blank")
    @Pattern(regexp = "^(?i)(New|Expired)$", message = "Product status should follow ${regexp}")
    private String status;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return prodNum.equals(that.prodNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodNum);
    }
}
