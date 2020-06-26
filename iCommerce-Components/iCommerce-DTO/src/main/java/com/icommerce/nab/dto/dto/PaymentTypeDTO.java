package com.icommerce.nab.dto.dto;

import org.springframework.util.StringUtils;

/**
 * Enum class for payment type
 */
public enum PaymentTypeDTO {

    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    INTERNET_BANKING("Internet Banking");

    public final String paymentType;

    PaymentTypeDTO(String paymentType) {
        this.paymentType = paymentType;
    }

    public static PaymentTypeDTO getPaymentTypeDTO(String paymentType) {
        if (StringUtils.isEmpty(paymentType)) {
            return null;
        }
        for (PaymentTypeDTO paymentTypeDTO : PaymentTypeDTO.values()) {
            if (paymentTypeDTO.paymentType.equalsIgnoreCase(paymentType)) {
                return paymentTypeDTO;
            }
        }
        return null;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
