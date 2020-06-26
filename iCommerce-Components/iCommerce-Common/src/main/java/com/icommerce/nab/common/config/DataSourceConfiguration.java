package com.icommerce.nab.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.icommerce.nab.common.config.DataSourceConfiguration.BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER;
import static com.icommerce.nab.common.config.DataSourceConfiguration.BEAN_MYSQL_ICOMMERCE_TRANSACTION_MANAGER;

/**
 * Configuration class for database
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {"com.icommerce.nab.entity", "com.icommerce.nab.repository"},
        entityManagerFactoryRef = BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER,
        transactionManagerRef = BEAN_MYSQL_ICOMMERCE_TRANSACTION_MANAGER
)
public class DataSourceConfiguration {

    public static final String BEAN_MYSQL_ICOMMERCE_DATASOURCE = "BEAN_MYSQL_ICOMMERCE_DATASOURCE";
    public static final String BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER = "BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER";
    public static final String BEAN_MYSQL_ICOMMERCE_TRANSACTION_MANAGER = "BEAN_MYSQL_ICOMMERCE_TRANSACTION_MANAGER";

    @Primary
    @Bean(BEAN_MYSQL_ICOMMERCE_DATASOURCE)
    @ConfigurationProperties(prefix = "mysql.icommerce.datasource")
    public DataSource iCommerceDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean entityManager(@Qualifier(BEAN_MYSQL_ICOMMERCE_DATASOURCE) DataSource datasource,
                                                                         @Autowired Environment env) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPackagesToScan("com.icommerce.nab.entity", "com.icommerce.nab.repository");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean(BEAN_MYSQL_ICOMMERCE_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(@Qualifier(BEAN_MYSQL_ICOMMERCE_ENTITY_MANAGER) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }
}