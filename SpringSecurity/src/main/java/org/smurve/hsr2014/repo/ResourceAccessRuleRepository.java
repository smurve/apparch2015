package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.ResourceAccessRule;
import org.smurve.hsr2014.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceAccessRuleRepository extends JpaRepository<ResourceAccessRule, String>, CustomResourceAccessRuleRepository {
    List<ResourceAccessRule> findByRole(Role role);
}
