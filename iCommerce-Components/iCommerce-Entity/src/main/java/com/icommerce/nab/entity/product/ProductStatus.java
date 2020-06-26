package com.icommerce.nab.entity.product;

import org.springframework.util.StringUtils;

/**
 * Enum class for mapping product status object to database
 */
public enum ProductStatus {

    NEW("New"),
    EXPIRED("Expired");

    public final String status;

    ProductStatus(String status) {
        this.status = status;
    }

    public static ProductStatus getProductStatus(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.status.equalsIgnoreCase(status)) {
                return productStatus;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
