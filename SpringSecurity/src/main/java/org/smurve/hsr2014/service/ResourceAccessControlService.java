package org.smurve.hsr2014.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.*;
import org.smurve.hsr2014.repo.CustomResourceAccessRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//@Service
public class ResourceAccessControlService implements AccessControlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceAccessControlService.class);

    private final CustomResourceAccessRuleRepository accessRuleRepo;

    @Autowired
    public ResourceAccessControlService(
            @Qualifier("resourceAccessRuleRepositoryImpl") CustomResourceAccessRuleRepository accessRuleRepo) {
        this.accessRuleRepo = accessRuleRepo;
    }

    @Override
    public Collection<String> getRolesFor(Object target, Object accessMethod) {
        if (target instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) target;
            return getRolesForType(entity.getClass(), accessMethod);
        }
        return Collections.emptyList();
    }

    private EntityOperation getOperation(Object accessMethod) {
        return EntityOperation.valueOf(accessMethod.toString().toUpperCase());
    }

    @Override
    public Collection<String> getRolesForType(Class<? extends BaseEntity> targetBaseEntityType, Object accessMethod) {
        LOGGER.info("Getting Roles for " + targetBaseEntityType.getName() + " and method " + accessMethod.toString());

        List<String> authorities = new ArrayList<String>();
        List<ResourceAccessRule> rules = accessRuleRepo.findMatchingRules(targetBaseEntityType,
                getOperation(accessMethod));
        for (ResourceAccessRule accessRule : rules) {
            authorities.add(accessRule.getRole().getRoleName());
        }

        return authorities;
    }

    public boolean hasAccessRule(User user, Class<? extends BaseEntity> targetBaseEntityType, EntityOperation operation) {
        Collection<String> requiredRoles = getRolesForType(targetBaseEntityType, operation);
        return hasUserRequiredRoles(user, requiredRoles);
    }

    private boolean hasUserRequiredRoles(User user, Collection<String> requiredRoles) {
        for (String role : requiredRoles) {
            for (Role userRole : user.getRoles()) {
                if (userRole.getRoleName().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

}
