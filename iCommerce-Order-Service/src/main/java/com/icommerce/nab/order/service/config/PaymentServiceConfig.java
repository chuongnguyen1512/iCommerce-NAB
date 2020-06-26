package com.icommerce.nab.order.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for payment-service Restful APIs
 */
@Configuration
@ConfigurationProperties(prefix = "rest.services.payment")
public class PaymentServiceConfig {

    private String host;
    private String payCashOrdersUrl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPayCashOrdersUrl() {
        return payCashOrdersUrl;
    }

    public void setPayCashOrdersUrl(String payCashOrdersUrl) {
        this.payCashOrdersUrl = payCashOrdersUrl;
    }
}
