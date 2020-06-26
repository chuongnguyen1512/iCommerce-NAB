package com.icommerce.nab.dto.dto;

import org.springframework.util.StringUtils;

/**
 * Enum class for product status
 */
public enum ProductStatusDTO {
    NEW("New"),
    EXPIRED("Expired");

    public final String status;

    ProductStatusDTO(String status) {
        this.status = status;
    }

    public static ProductStatusDTO getProductStatusDTO(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        for (ProductStatusDTO productStatusDTO : ProductStatusDTO.values()) {
            if (productStatusDTO.status.equalsIgnoreCase(status)) {
                return productStatusDTO;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
