package org.smurve.hsr2014.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * https://gist.github.com/kdonald/2232095
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);

    private final String clientHost;

    @Autowired
    public CorsFilter() {
      clientHost = "127.0.0.1:9000";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "http://" + clientHost);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if (isCorsPreflightRequest(request)) {
            LOGGER.debug("setting access control headers for preflight request");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers",
                    "authorization, accept, content-type, origin, x-requested-with");
            response.addHeader("Access-Control-Max-Age", "1728000");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCorsPreflightRequest(HttpServletRequest request) {
        return (request.getHeader("Access-Control-Request-Method") != null) && "OPTIONS".equals(request.getMethod());
    }

}
