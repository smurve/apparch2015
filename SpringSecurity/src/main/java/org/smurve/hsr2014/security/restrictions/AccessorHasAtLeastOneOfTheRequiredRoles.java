package org.smurve.hsr2014.security.restrictions;

import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.service.AccessControlService;
import org.smurve.hsr2014.service.AccessRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AccessorHasAtLeastOneOfTheRequiredRoles implements AccessRestriction {

    @Autowired
    private AccessControlService accessControlService;

    @Override
    public boolean isRestricted(Authentication authentication, Object accessMethod, BaseEntity target) {
        Collection<String> requiredRoles = accessControlService.getRolesFor(target, accessMethod);
        if (requiredRoles.size() == 0) {
            return false;
        }

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
