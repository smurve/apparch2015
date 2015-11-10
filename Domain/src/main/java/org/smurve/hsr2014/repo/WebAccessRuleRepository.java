package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Role;
import org.smurve.hsr2014.domain.WebAccessRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebAccessRuleRepository extends JpaRepository<WebAccessRule, Long>, CustomWebAccessRuleRepository {
  List<WebAccessRule> findByRole(Role role);
}
