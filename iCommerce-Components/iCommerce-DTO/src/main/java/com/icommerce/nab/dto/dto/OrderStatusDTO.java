package com.icommerce.nab.dto.dto;

import org.springframework.util.StringUtils;

/**
 * Enum type for order status
 */
public enum OrderStatusDTO {

    PENDING("Pending"),
    PROCESSED("Processed"),
    DELIVERING("Delivering"),
    FINISHED("Finished");

    public final String status;

    OrderStatusDTO(String status) {
        this.status = status;
    }

    public static OrderStatusDTO getOrderStatusDTO(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        for (OrderStatusDTO orderStatusDTO : OrderStatusDTO.values()) {
            if (orderStatusDTO.status.equalsIgnoreCase(status)) {
                return orderStatusDTO;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
