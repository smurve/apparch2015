package org.smurve.hsr2014.configuration;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.utils.AspectOrder;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.DateManipulationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.util.Properties;

/**
 * The Spring configuration that builds the data source the repository layer should use for accessing the persistent
 * store.
 */
@Configuration
@EnableTransactionManagement(order = AspectOrder.INNERMOST)
public class SpringJpaConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringJpaConfiguration.class);

    @Autowired
    private DatabaseConnector dbConnection;

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factory.setPackagesToScan("org.smurve.hsr2014.domain", "org.smurve.hsr2014.domain.spsec");
        factory.setDataSource(dbConnection.buildDataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(buildJpaProperties());

        LOGGER.info("JPA config set up with dbConnector of type " + dbConnection.getClass().getName());
        return factory;
    }

    private Properties buildJpaProperties() {
        Properties retval = new Properties();

        configureDefaultJpaProperties(retval);
        dbConnection.appendVendorJpaConfiguration(retval);

        return retval;
    }

    private void configureDefaultJpaProperties(Properties properties) {
        properties.setProperty("hibernate.connection.charSet", "UTF-8");
        properties.setProperty("hibernate.ejb.naming_strategy", EJB3NamingStrategy.class.getName());
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        // properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.ejb.interceptor", DateManipulationInterceptor.class.getName());
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(AbstractEntityManagerFactoryBean factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(factory.getObject());

        return transactionManager;
    }

    @Bean
    public TransactionAttributeSource annotationTransactionAttributeSource() {
        return new AnnotationTransactionAttributeSource();
    }
}
