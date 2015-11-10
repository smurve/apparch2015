package org.smurve.hsr2014.repo2;


import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.domain.EntityOperation;
import org.smurve.hsr2014.domain.ResourceAccessRule;

import java.util.List;

public interface CustomResourceAccessRuleRepository {
    List<ResourceAccessRule> findMatchingRules(Class<? extends BaseEntity> resourceType, EntityOperation operation);
}
