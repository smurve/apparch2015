package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.BaseEntity;

import java.util.Collection;

public interface AccessControlService {
    Collection<String> getRolesFor(Object targetDomainObject, Object accessMethod);

    Collection<String> getRolesForType(Class<? extends BaseEntity> targetBaseEntityType, Object accessMethod);

}
