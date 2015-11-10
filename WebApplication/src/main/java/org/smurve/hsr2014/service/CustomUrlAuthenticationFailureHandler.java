package org.smurve.hsr2014.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomUrlAuthenticationFailureHandler.class);

  private static final String AUTH_FAILURE_URL = "/login.html#/invalid";
  private static final String INVALID_PW_URL = "/login.html#/invalidPW";
  private static final String ACCOUNT_LOCKED_URL = "/login.html#/accountLocked";
  private static final String USER_PARAM_NAME = "j_username";
  private static final String LDAP_UNREACHABLE_URL = "/login.html#/ldapUnreachable";
  private static final String AUTH_CONFIG_MISSING = "/login.html#/ldapMissingAuthConfig";
  private static final String INVALID_CREDENTIALS = "/login.html#/invalidCredentials";
  private static final String ACCOUNT_NOT_ACTIVE = "/login.html#/accountNotActive";

  private final UserService userService;

  @Autowired
  public CustomUrlAuthenticationFailureHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {

    if (isAjaxLogin(request)) {
      handleAjaxAuthFailure(request, response, exception);
    } else {
      handleFormAuthFailure(request, response, exception);
    }

  }

  private void handleFormAuthFailure(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException exception) throws IOException, ServletException {

    LOGGER.warn("login failure (form auth): " + exception);

    if (exception.getClass() == AuthenticationServiceException.class && exception.getCause() != null
      && exception.getCause().getClass().getSimpleName() == "CommunicationException") {
      this.setDefaultFailureUrl(LDAP_UNREACHABLE_URL);
    } else if (exception.getClass() == AuthenticationServiceException.class) {
      this.setDefaultFailureUrl(INVALID_CREDENTIALS);
    } else if (exception.getClass() == ProviderNotFoundException.class) {
      this.setDefaultFailureUrl(AUTH_CONFIG_MISSING);
    } else if (exception.getClass() == BadCredentialsException.class) {
      incrementBadPasswordCounter(getUsernameFromRequest(request));
      this.setDefaultFailureUrl(INVALID_PW_URL);
    } else if (exception.getClass() == AccountLockedException.class) {
      this.setDefaultFailureUrl(ACCOUNT_LOCKED_URL);
    } else if (exception.getClass() == AccountNotActiveException.class) {
      this.setDefaultFailureUrl(ACCOUNT_NOT_ACTIVE);
    } else {
      this.setDefaultFailureUrl(AUTH_FAILURE_URL);
    }
    super.onAuthenticationFailure(request, response, exception);
  }

  private void handleAjaxAuthFailure(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException exception) throws IOException {

    LOGGER.warn("login failure (ajax auth): " + exception);

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    if (exception.getClass() == AuthenticationServiceException.class && exception.getCause() != null
      && exception.getCause().getClass().getSimpleName() == "CommunicationException") {
      response.getWriter().print("{\"reason\":\"ldap_Unreachable\"}");
    } else if (exception.getClass() == AuthenticationServiceException.class) {
      this.setDefaultFailureUrl(INVALID_CREDENTIALS);
      response.getWriter().print("{\"reason\":\"bad_credentials\"}");
    } else if (exception.getClass() == ProviderNotFoundException.class) {
      this.setDefaultFailureUrl(AUTH_CONFIG_MISSING);
      response.getWriter().print("{\"reason\":\"missing_Auth_Config\"}");
    } else if (exception.getClass() == BadCredentialsException.class) {
      incrementBadPasswordCounter(getUsernameFromRequest(request));
      response.getWriter().print("{\"reason\":\"bad_credentials\"}");
    } else if (exception.getClass() == AccountLockedException.class) {
      response.getWriter().print("{\"reason\":\"account_locked\"}");
    } else if (exception.getClass() == AccountNotActiveException.class) {
      response.getWriter().print("{\"reason\":\"account_not_active\"}");
    } else {
      response.getWriter().print("{\"reason\":\"invalid\"}");
    }
    response.getWriter().flush();
  }

  private boolean isAjaxLogin(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  private String getUsernameFromRequest(HttpServletRequest request) {
    String user = "";
    for (String param : request.getParameterMap().keySet()) {
      if (param.equals(USER_PARAM_NAME)) {
        user = request.getParameterMap().get(param)[0];
        break;
      }
    }
    return user;
  }

  private void incrementBadPasswordCounter(String username) {
    try {
      int count = userService.getWrongPasswordCountForUser(username);
      userService.saveWrongPasswordCountForUser(username, count + 1);
    } catch (NoResultException nre) {
      // user not found, do not increment counter
    }
  }

}
