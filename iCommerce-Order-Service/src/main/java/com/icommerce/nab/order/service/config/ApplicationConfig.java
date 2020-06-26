package com.icommerce.nab.order.service.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommerce.nab.common.config.CallRestfulRetryConfig;
import com.icommerce.nab.common.restful.DeliverCallRestfulService;
import com.icommerce.nab.common.restful.PaymentCallRestfulService;
import com.icommerce.nab.common.transform.BaseTransform;
import com.icommerce.nab.common.transform.OrderTransform;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.entity.order.Order;
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

    public static final String BEAN_ORDER_TRANSFORM_SERVICE = "BEAN_ORDER_TRANSFORM_SERVICE";
    public static final String BEAN_DELIVER_CALL_RESTFUL_SERVICE = "BEAN_DELIVER_CALL_RESTFUL_SERVICE";
    public static final String BEAN_PAYMENT_CALL_RESTFUL_SERVICE = "BEAN_PAYMENT_CALL_RESTFUL_SERVICE";
    public static final String BEAN_GSON = "BEAN_GSON";
    public static final String BEAN_REST_TEMPLATE = "BEAN_REST_TEMPLATE";

    @Bean(BEAN_GSON)
    public Gson gson() {
        return new GsonBuilder().disableHtmlEscaping().setDateFormat(DATE_FORMAT).create();
    }

    @Bean(BEAN_REST_TEMPLATE)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(BEAN_ORDER_TRANSFORM_SERVICE)
    public BaseTransform<OrderDTO, Order> orderTransform() {
        return new OrderTransform();
    }

    @Bean(BEAN_DELIVER_CALL_RESTFUL_SERVICE)
    @Primary
    public DeliverCallRestfulService deliverCallRestfulService(@Qualifier(BEAN_REST_TEMPLATE) RestTemplate restTemplate,
                                                               @Qualifier(BEAN_GSON) Gson gson,
                                                               @Autowired CallRestfulRetryConfig retryConfig,
                                                               @Value("${rest.services.deliver.host}") String host) {
        return new DeliverCallRestfulService(restTemplate, gson, retryConfig, host);
    }

    @Bean(BEAN_PAYMENT_CALL_RESTFUL_SERVICE)
    @Primary
    public PaymentCallRestfulService paymentCallRestfulService(@Qualifier(BEAN_REST_TEMPLATE) RestTemplate restTemplate,
                                                               @Qualifier(BEAN_GSON) Gson gson,
                                                               @Autowired CallRestfulRetryConfig retryConfig,
                                                               @Value("${rest.services.payment.host}") String host) {
        return new PaymentCallRestfulService(restTemplate, gson, retryConfig, host);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer(@Value("${h2.database.externalPort}") String dbExternalPort) throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", dbExternalPort);
    }
}
