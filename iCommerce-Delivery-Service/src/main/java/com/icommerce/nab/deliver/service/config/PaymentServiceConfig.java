package com.icommerce.nab.deliver.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for payment-service restful info
 */
@Configuration
@ConfigurationProperties(prefix = "rest.services.payment")
public class PaymentServiceConfig {

    private String host;
    private String payOrderByCashURL;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPayOrderByCashURL() {
        return payOrderByCashURL;
    }

    public void setPayOrderByCashURL(String payOrderByCashURL) {
        this.payOrderByCashURL = payOrderByCashURL;
    }
}
