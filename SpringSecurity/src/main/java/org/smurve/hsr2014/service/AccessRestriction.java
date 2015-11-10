package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.BaseEntity;
import org.springframework.security.core.Authentication;


public interface AccessRestriction {
    public boolean isRestricted(Authentication authentication, Object accessMethod, BaseEntity target);
}
