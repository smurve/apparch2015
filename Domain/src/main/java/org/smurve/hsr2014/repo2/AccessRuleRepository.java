package org.smurve.hsr2014.repo2;

import org.smurve.hsr2014.domain2.AccessRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRuleRepository extends JpaRepository<AccessRule, String> {

    public List<AccessRule> findByResourceTypeAndResourceId(String resourceType, String resourceId);
}
