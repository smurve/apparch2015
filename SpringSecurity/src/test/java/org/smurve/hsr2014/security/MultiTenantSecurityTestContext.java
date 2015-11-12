package org.smurve.hsr2014.security;

import org.smurve.hsr2014.configuration.SpringJpaConfiguration;
import org.smurve.hsr2014.configuration.SpringSecurityContext;
import org.smurve.hsr2014.hidden.SecuredReturnValueAspect;
import org.smurve.hsr2014.repo.HsqlDbHelper;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.hsql.HSqlConnector;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@Configuration
@EnableAspectJAutoProxy
@Import(value = {SpringJpaConfiguration.class, SpringSecurityContext.class})
@EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repo")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {
        "org.smurve.hsr2014.security.restrictions",
        "org.smurve.hsr2014.service",
        "org.smurve.hsr2014.repo"})
public class MultiTenantSecurityTestContext {

    @Bean
    public DatabaseConnector databaseConnector() {
        return new HSqlConnector();
    }

    @Bean
    public HsqlDbHelper hsqlDbHelper() {
        return new HsqlDbHelper();
    }

}
