package org.smurve.hsr2014.hidden;

import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.domain.SecureResource;
import org.smurve.hsr2014.service.AccessRestriction;
import org.smurve.hsr2014.utils.MultiTenantUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Restriction only if target is tenantOwned and authentication does not belong to the same tenant
 */
@Component
public class AccessorOwnsTarget implements AccessRestriction {

    @Override
    public boolean isRestricted(Authentication authentication, Object accessMethod, BaseEntity target) {

        if (!(target instanceof SecureResource)) {
            return false;
        }

        SecureResource secureResource = (SecureResource) target;

        if (secureResource.getOwner() == null) {
            return false;
        }

        if (authentication.getPrincipal() instanceof MultiTenantUserDetails) {
            MultiTenantUserDetails details = (MultiTenantUserDetails) authentication.getPrincipal();

            if (details.getUsername().equals(secureResource.getOwner())) {
                return false;
            } else {
                return true;
            }
        } else {
            // access only if MultiTenantAuthentication is provided
            return true;
        }

    }
}
