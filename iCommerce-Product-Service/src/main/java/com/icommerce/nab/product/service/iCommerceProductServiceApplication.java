package com.icommerce.nab.product.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {
        "com.icommerce.nab",
        "com.icommerce.nab.entity",
        "com.icommerce.nab.repository",
        "com.icommerce.nab.product.service",
        "com.icommerce.nab.common.config",
        "com.icommerce.nab.product.service"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@SpringBootApplication
public class iCommerceProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(iCommerceProductServiceApplication.class, args);
    }
}
