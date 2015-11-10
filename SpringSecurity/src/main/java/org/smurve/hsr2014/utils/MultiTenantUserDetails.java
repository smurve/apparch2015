package org.smurve.hsr2014.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@SuppressWarnings("serial")
public class MultiTenantUserDetails extends User {

    private final String tenandId;

    public MultiTenantUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                                  String tenantId) {
        super(username, password, true, true, true, true, authorities);

        this.tenandId = tenantId;
    }

    public final String getTenantId() {
        return tenandId;
    }
}
