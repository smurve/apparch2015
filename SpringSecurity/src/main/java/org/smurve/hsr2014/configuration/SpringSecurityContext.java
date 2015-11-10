package org.smurve.hsr2014.configuration;

import org.smurve.hsr2014.repo.CustomResourceAccessRuleRepository;
import org.smurve.hsr2014.repo.UserRepository;
import org.smurve.hsr2014.security.AuditLoggerAspect;
import org.smurve.hsr2014.security.RestrictionBasedMethodPermissionEvaluator;
import org.smurve.hsr2014.security.SecurityEnhancementAspect;
import org.smurve.hsr2014.service.AccessControlService;
import org.smurve.hsr2014.service.MultiTenantUserDetailsService;
import org.smurve.hsr2014.service.ResourceAccessControlService;
import org.smurve.hsr2014.utils.PasswordEncodingUserFactory;
import org.smurve.hsr2014.utils.UserFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


@Configuration
@EnableAspectJAutoProxy
@Import(SpringJpaConfiguration.class)
@EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repo")
@ComponentScan(basePackages = {
  "org.smurve.hsr2014.security.restrictions",
  "org.smurve.hsr2014.repo"})
public class SpringSecurityContext {

  @Bean
  public AuditLoggerAspect auditLoggerAspect() {
    return new AuditLoggerAspect();
  }

  @Bean
  public SecurityEnhancementAspect securityEnhancementAspect() {
    return new SecurityEnhancementAspect();
  }

  @Bean
  public UserFactory userFactory() {
    return new PasswordEncodingUserFactory(new BCryptPasswordEncoder());
  }

  @Bean
  @Primary
  public AuthenticationManager authenticationManager(AuthenticationProvider provider) {

    return new ProviderManager(Arrays.asList(provider), null);
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository repo) {
    return new MultiTenantUserDetailsService(repo);
  }

  @Bean
  public DefaultMethodSecurityExpressionHandler methodExpressionHandler(PermissionEvaluator permissionEvaluator) {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setPermissionEvaluator(permissionEvaluator);
    return handler;
  }

  @Bean
  public PermissionEvaluator permissionEvaluator() {
    PermissionEvaluator evaluator = new RestrictionBasedMethodPermissionEvaluator();
    return evaluator;
  }

  @Bean
  public AccessControlService accessControlService(
    @Qualifier("resourceAccessRuleRepositoryImpl") CustomResourceAccessRuleRepository repo) {
    return new ResourceAccessControlService(repo);
  }

  @Bean
  public AccessDecisionManager accessDecisionManager() {
    AccessDecisionVoter voter = new PreInvocationAuthorizationAdviceVoter(new ExpressionBasedPreInvocationAdvice());
    return new UnanimousBased(Arrays.asList(voter));
  }











































  /*
  @Bean
  public SecuredReturnValueAspect securedReturnValueAspect() { return new SecuredReturnValueAspect();}
  */




}
