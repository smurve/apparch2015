package org.smurve.hsr2014.service;

import org.smurve.hsr2014.config.SpringJpaConfiguration;
import org.smurve.hsr2014.utils.AspectOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by wgiersche on 25/03/14.
 */
@Configuration
@EnableTransactionManagement(order = AspectOrder.INNERMOST)
public class JpaTestConfiguration extends SpringJpaConfiguration {

    @Override
    protected void configureDefaultJpaProperties(Properties properties) {
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
      properties.setProperty("hibernate.show_sql", "true");
        // properties.setProperty("hibernate.hbm2ddl.auto", "validate");
    }

}
