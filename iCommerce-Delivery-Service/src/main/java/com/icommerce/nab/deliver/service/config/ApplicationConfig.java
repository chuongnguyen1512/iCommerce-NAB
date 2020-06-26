package com.icommerce.nab.deliver.service.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommerce.nab.common.config.CallRestfulRetryConfig;
import com.icommerce.nab.common.restful.PaymentCallRestfulService;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

import static com.icommerce.nab.common.constant.ICommerceConstants.DATE_FORMAT;

/**
 * Application configuration
 */
@Configuration
public class ApplicationConfig {

    public static final String BEAN_PAYMENT_CALL_RESTFUL_SERVICE = "BEAN_PAYMENT_CALL_RESTFUL_SERVICE";
    public static final String BEAN_GSON = "BEAN_GSON";
    public static final String BEAN_REST_TEMPLATE = "BEAN_REST_TEMPLATE";

    @Bean(BEAN_PAYMENT_CALL_RESTFUL_SERVICE)
    @Primary
    public PaymentCallRestfulService paymentCallRestfulService(@Qualifier(BEAN_REST_TEMPLATE) RestTemplate restTemplate,
                                                               @Qualifier(BEAN_GSON) Gson gson,
                                                               @Autowired CallRestfulRetryConfig retryConfig,
                                                               @Value("${rest.services.payment.host}") String host) {
        return new PaymentCallRestfulService(restTemplate, gson, retryConfig, host);
    }

    @Bean(BEAN_GSON)
    public Gson gson() {
        return new GsonBuilder().disableHtmlEscaping().setDateFormat(DATE_FORMAT).create();
    }

    @Bean(BEAN_REST_TEMPLATE)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
