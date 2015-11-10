package org.smurve.hsr2014.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.service.AccessControlService;
import org.smurve.hsr2014.service.AccessRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Evaluates permission for the current Authentication object in the securityContext.
 * <p>
 * Our service methods are annotated with @PreAuthorize and @PostFilter.
 * <p>
 * This PermissionEvaluator delegates to particular restrictions and grants access if
 * no restriction applies
 */
@Service
public class RestrictionBasedMethodPermissionEvaluator implements PermissionEvaluator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestrictionBasedMethodPermissionEvaluator.class);

    @Autowired
    private List<AccessRestriction> accessCriteria;

    @Autowired
    public RestrictionBasedMethodPermissionEvaluator() {
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object accessMethod) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("evaluating permissions for " + authentication.getName() + ", " + targetDomainObject + ", "
                    + accessMethod);
        }

        // Only DB Entities and ids are supported in this example
        if (! (targetDomainObject instanceof BaseEntity) ) {
            return true;
        }

        return hasPermission(authentication, accessMethod, (BaseEntity) targetDomainObject);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object accessMethod) {
        throw new IllegalAccessError("we should not use this method at the moment. no multiTenancy implemented!");
    }

    private boolean hasPermission(Authentication authentication, Object accessMethod, BaseEntity target) {

        for (AccessRestriction criterion : accessCriteria) {
            if (criterion.isRestricted(authentication, accessMethod, target)) {
                return false;
            }
        }

        // if no criterion prevents access, grant access.
        return true;
    }

}
