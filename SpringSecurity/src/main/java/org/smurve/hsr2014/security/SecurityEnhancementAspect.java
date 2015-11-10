package org.smurve.hsr2014.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.AuditRecord;
import org.smurve.hsr2014.domain.SecureResource;
import org.smurve.hsr2014.domain.User;
import org.smurve.hsr2014.repo.AuditRecordRepository;
import org.smurve.hsr2014.repo.UserRepository;
import org.smurve.hsr2014.utils.AspectOrder;
import org.smurve.hsr2014.utils.MultiTenantUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

/*
    There is some questionable code here.
    The problem is that the JoinPoint does not provide the method object,
    so there's a chance that we cannot identify the method if a null argument is provided
 */
@Aspect
@Order(AspectOrder.OUTERMOST)
public class SecurityEnhancementAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityEnhancementAspect.class);

    @Autowired
    private UserRepository repo;

    /* intercept save method and enrich the entity, if it is a SecuredObject
       Set tenant and owner from looking at the authentication info of the current Thread
     */
    @Around("execution(* org.smurve.hsr2014.repo.*.save(..))")
    public Object setTenantAndOwner(final ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        if ( args.length != 1 )  return joinPoint.proceed();
        Object object = args [0];
        if ( !(object instanceof SecureResource )) return joinPoint.proceed();

        SecureResource resource = (SecureResource) object;
        String userName = findAuthenticatedUser();

        // nothing to enhance here.
        if ( userName == null ) return joinPoint.proceed();

        User user = repo.findByUsername(userName);

        resource.setOwner(userName);

        // set the tenant if it is null, leave it otherwise
        if ( resource.getTenant() == null ) {
            resource.setTenant(user.getTenant());
        }

        return joinPoint.proceed();
    }

    /*
        find the advised method from the target class and extract the Audit Annotation
     */
    private String getClassifier(ProceedingJoinPoint joinpoint) throws NoSuchMethodException {
        Object[] args = joinpoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        // This is bad. Doesn't check for nulls
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        Signature signature = joinpoint.getSignature();
        String name = signature.getName();
        Object target = joinpoint.getTarget();
        Method method = target.getClass().getMethod(name, classes);
        Audit a = method.getAnnotation(Audit.class);
        return a.classifier();
    }

  private String getEntityId(Object arg) {
    String objectId;
        if (arg instanceof SecureResource) {
            SecureResource secureResource = (SecureResource) arg;
            objectId = secureResource.getId();
        } else {
            throw new IllegalArgumentException("expecting TenantOwned here");
        }
        return objectId;
    }

    private String findAuthenticatedUser() {
        if ( SecurityContextHolder.getContext() == null ) return null;
        if ( SecurityContextHolder.getContext().getAuthentication() == null ) return null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MultiTenantUserDetails userDetails = null;
        if (principal instanceof MultiTenantUserDetails) {
            userDetails = (MultiTenantUserDetails) principal;
        }
        return userDetails.getUsername();
    }
}
