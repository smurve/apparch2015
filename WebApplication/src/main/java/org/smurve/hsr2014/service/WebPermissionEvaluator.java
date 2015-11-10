package org.smurve.hsr2014.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class is used in the SecurityContext.xml to check the users permission to access certain URLs or partials of the
 * web application. Examples:
 * <p>
 * This would be the permission for the root application: <intercept-url pattern="/**"
 * access="hasPermission('application','any')" />
 * <p>
 * We can restrict access to the security partial: <intercept-url pattern="/security/**"
 * access="hasPermission('security','any')" />
 * <p>
 * We can restrict access to a certain part of the security partial: <intercept-url pattern="/security/#/userRoles"
 * access="hasPermission('security','/#/userRoles')" />
 */
@Service
public class WebPermissionEvaluator implements PermissionEvaluator {

  private static final Logger logger = LoggerFactory.getLogger(WebPermissionEvaluator.class);

  private final AccessControlService accessControlService;

  @Autowired
  public WebPermissionEvaluator(WebAccessControlService acs) {
    accessControlService = acs;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Object applicationPart, Object url) {
    logger
      .debug("evaluationg permissions for " + authentication.getName() + ", application part: " + applicationPart + ", url: " + url);

    if (!authentication.isAuthenticated()) {
      return false;
    }

    return hasAccessorRequiredRoles(authentication, applicationPart, url);
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object accessMethod) {
    throw new RuntimeException("WebPermissionEvaluator is not fully implemented yet. This is a TODO!");
  }

  private boolean hasAccessorRequiredRoles(Authentication authentication, Object applicationPart, Object url) {
    Collection<String> requiredRoles = accessControlService.getRolesFor(applicationPart, url);

		/*
     * if no role is required, grant access
		 */
    if (requiredRoles.size() == 0) {
      return true;
    }
    // if at least one required role is occupied by the accessor, grant
    // access
    for (String role : requiredRoles) {
      for (GrantedAuthority auth : authentication.getAuthorities()) {
        if (auth.getAuthority().equals(role)) {
          return true;
        }
      }
    }
    return false;
  }

}
