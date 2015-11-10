package org.smurve.hsr2014.security.restrictions;

import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.service.AccessRestriction;
import org.smurve.hsr2014.utils.MultiTenantUserDetails;
import org.springframework.security.core.Authentication;

public class AuthenticationBasedAccessRestriction implements AccessRestriction {

    @Override
    public boolean isRestricted(Authentication authentication, Object accessMethod, BaseEntity target) {

        if (authentication == null) {
            return true;
        }

        if (!authentication.isAuthenticated()) {
            return true;
        }

        if ((authentication.getPrincipal() == null)
                || !(authentication.getPrincipal().getClass().equals(MultiTenantUserDetails.class))) {
            return true;
        }
        return false;
    }
}
