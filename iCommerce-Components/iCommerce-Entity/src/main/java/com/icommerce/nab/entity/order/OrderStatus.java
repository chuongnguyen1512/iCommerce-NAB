package com.icommerce.nab.entity.order;

import org.springframework.util.StringUtils;

/**
 * Enum class for mapping order status to database
 */
public enum OrderStatus {

    PENDING("Pending"),
    PROCESSED("Processed"),
    DELIVERING("Delivering"),
    FINISHED("Finished");

    public final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static OrderStatus getOrderStatus(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.status.equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
