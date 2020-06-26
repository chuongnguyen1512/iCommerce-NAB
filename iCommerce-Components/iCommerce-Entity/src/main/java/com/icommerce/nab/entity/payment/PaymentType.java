package com.icommerce.nab.entity.payment;

import org.springframework.util.StringUtils;

/**
 * Enum class for mapping payment type object to database
 */
public enum PaymentType {

    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    INTERNET_BANKING("Internet Banking");

    public final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public static PaymentType getPaymentType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.paymentType.equalsIgnoreCase(type)) {
                return paymentType;
            }
        }
        return null;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
