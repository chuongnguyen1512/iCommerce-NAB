package com.icommerce.nab.product.service.config;

import com.icommerce.nab.common.transform.ProductTransform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Application configuration
 */
@Configuration
public class ApplicationConfig {

    public static final String BEAN_PRODUCT_TRANSFORM_SERVICE = "BEAN_PRODUCT_TRANSFORM_SERVICE";

    @Bean(BEAN_PRODUCT_TRANSFORM_SERVICE)
    @Primary
    public ProductTransform productTransform() {
        return new ProductTransform();
    }
}
