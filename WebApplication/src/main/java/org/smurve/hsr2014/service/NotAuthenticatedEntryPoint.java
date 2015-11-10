package org.smurve.hsr2014.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This EntryPoint gets called if there is an incoming request for a REST webservice without SecurityContext.
 * Sends a http 401 back to the client.
 */
@Service("notAuthenticatedEntryPoint")
public class NotAuthenticatedEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    // send back http 401 -> user is not logged in.
    // do not send 403, client needs to differentiate between "missing permissions" and "not logged in"
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
