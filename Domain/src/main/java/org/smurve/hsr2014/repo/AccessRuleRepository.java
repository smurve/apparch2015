package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.AccessRule;
import org.smurve.hsr2014.domain.SecureResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRuleRepository extends JpaRepository<AccessRule, String> {

    public List<AccessRule> findByResource(SecureResource resource);
}
