package org.smurve.hsr2014.service;

import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.mysql.mysql.MySqlConnector;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@Import(JpaTestConfiguration.class)
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repository")
@ComponentScan(basePackages = {
        "org.smurve.hsr2014.repository", "org.smurve.hsr2014.service"})
public class ServicesTestContext {

    @Bean
    public DatabaseConnector databaseConnector() {
        return new MySqlConnector();
    }


}
