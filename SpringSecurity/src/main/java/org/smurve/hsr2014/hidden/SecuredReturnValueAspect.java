package org.smurve.hsr2014.hidden;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.smurve.hsr2014.domain.SecureResource;
import org.smurve.hsr2014.utils.AspectOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(AspectOrder.OUTERMOST)
public class SecuredReturnValueAspect {

    @Autowired
    private PermissionEvaluator evaluator;

    @Around("execution(* org.smurve.hsr2014.service.*.find*(..))")
    public Object filterReturnValue ( final ProceedingJoinPoint joinPoint) throws Throwable {

        Object returnValue = joinPoint.proceed();

        if ( !(returnValue instanceof SecureResource )) {
            return returnValue;
        }

        SecureResource secureResource = (SecureResource) returnValue;

        Authentication authentication;

        if (SecurityContextHolder.getContext() != null ) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        } else {
            throw new AuthenticationCredentialsNotFoundException("Not auth'd");
        }

        boolean permitted = evaluator.hasPermission( authentication, secureResource, "read");

        if ( permitted ) {
            return secureResource;
        } else {
            return null;
        }
    }
}
