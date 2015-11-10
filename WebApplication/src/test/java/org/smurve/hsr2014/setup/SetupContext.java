package org.smurve.hsr2014.setup;

import org.smurve.hsr2014.service.JpaTestConfiguration;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.hsql.HSqlConnector;
import org.smurve.hsr2014.utils.db.mysql.mysql.MySqlConnector;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@Import(JpaTestConfiguration.class)
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repo")
@ComponentScan(basePackages = {
        "org.smurve.hsr2014.repository", "org.smurve.hsr2014.service"})
public class SetupContext {

    @Bean
    public DatabaseConnector databaseConnector() {
      if (true) {
        return new MySqlConnector();
      } else {
        return new HSqlConnector();
      }
    }


}
