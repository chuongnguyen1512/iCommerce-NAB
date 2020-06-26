package com.icommerce.nab.order.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for deliver-service Restful APIs
 */
@Configuration
@ConfigurationProperties(prefix = "rest.services.deliver")
public class DeliverServiceConfig {

    private String host;
    private String deliverOrdersUrl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDeliverOrdersUrl() {
        return deliverOrdersUrl;
    }

    public void setDeliverOrdersUrl(String deliverOrdersUrl) {
        this.deliverOrdersUrl = deliverOrdersUrl;
    }
}
