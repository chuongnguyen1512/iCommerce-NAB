package com.icommerce.nab.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Retry calling apis configuration
 */
@Configuration
@ConfigurationProperties(prefix = "call.restful.retry")
public class CallRestfulRetryConfig {

    private int maxRetry;
    private int waitingRetry;

    public int getMaxRetry() {
        return this.maxRetry;
    }

    public int getWaitingRetry() {
        return this.waitingRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public void setWaitingRetry(int waitingRetry) {
        this.waitingRetry = waitingRetry;
    }
}