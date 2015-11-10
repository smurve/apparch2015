package org.smurve.hsr2014.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.AuditRecord;
import org.smurve.hsr2014.domain.SecureResource;
import org.smurve.hsr2014.repo.AuditRecordRepository;
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
public class AuditLoggerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLoggerAspect.class);

    @Autowired
    private AuditRecordRepository repo;

    // intercept any method in the repo package that has the Audit Aspect
    @Around("execution(@org.smurve.hsr2014.security.Audit * org.smurve.hsr2014.repo.*.*(..))")
    public Object createAuditLog(final ProceedingJoinPoint joinpoint) throws Throwable {

        Object[] args = joinpoint.getArgs();

        String classifier = getClassifier(joinpoint);
        String paramClass = args[0].getClass().getSimpleName();
        String objectId = getEntityId(args[0]);
        String user = findAuthenticatedUser();
        String target = joinpoint.getTarget().getClass().getName();

        AuditRecord auditRecord = new AuditRecord(user, target, classifier, paramClass, objectId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("User: " + user + " - Action: " + classifier
                    + " - On: " + paramClass + " - with ID: " + objectId);
        }

        repo.save(auditRecord);

        return joinpoint.proceed();
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
        if ( SecurityContextHolder.getContext() == null ) return "anonymous";
        if ( SecurityContextHolder.getContext().getAuthentication() == null ) return "anonymous";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MultiTenantUserDetails userDetails = null;
        if (principal instanceof MultiTenantUserDetails) {
            userDetails = (MultiTenantUserDetails) principal;
        }
        return userDetails.getUsername();
    }
}
