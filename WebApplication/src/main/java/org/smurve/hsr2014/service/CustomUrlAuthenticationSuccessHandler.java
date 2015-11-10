package org.smurve.hsr2014.service;

import org.smurve.hsr2014.api.UserService;
import org.smurve.hsr2014.utils.MultiTenantUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    resetUsersWrongPasswordCounter(authentication);

    if (isAjaxLogin(request)) {
      handleAjaxAuthSuccess(response);
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

  private void handleAjaxAuthSuccess(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().flush();
  }

  private boolean isAjaxLogin(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  private void resetUsersWrongPasswordCounter(Authentication authentication) {
    MultiTenantUserDetails userDetails = (MultiTenantUserDetails) authentication.getPrincipal();
    userService.saveWrongPasswordCountForUser(userDetails.getUsername(), 0);
  }
}
