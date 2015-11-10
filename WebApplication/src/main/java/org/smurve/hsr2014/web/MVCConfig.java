package org.smurve.hsr2014.web;

import org.smurve.hsr2014.config.SpringJpaConfiguration;
import org.smurve.hsr2014.configuration.SpringSecurityContext;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.mysql.mysql.MySqlConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repo")
@ComponentScan(basePackages = {"org.smurve.hsr2014.ctrl", "org.smurve.hsr2014.service", "org.smurve.hsr2014.repo"})
@Import({SpringJpaConfiguration.class, SpringSecurityContext.class})
@ImportResource("classpath:SecurityConfiguration.xml")
public class MVCConfig {

  @Bean
  public DefaultWebSecurityExpressionHandler webExpressionHandler(
    @Qualifier("webPermissionEvaluator") PermissionEvaluator permissionEvaluator) {
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setPermissionEvaluator(permissionEvaluator);
    return handler;
  }


    @Bean
    public DatabaseConnector databaseConnector() {
        return new MySqlConnector();
    }

}
