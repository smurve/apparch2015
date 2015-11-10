package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.domain.WebAccessRule;
import org.smurve.hsr2014.repo.CustomWebAccessRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WebAccessControlService implements AccessControlService {
  private final CustomWebAccessRuleRepository accessRuleRepo;

  @Autowired
  public WebAccessControlService(@Qualifier("webAccessRuleRepositoryImpl") CustomWebAccessRuleRepository accessRuleRepo) {
    this.accessRuleRepo = accessRuleRepo;
  }

  @Override
  public Collection<String> getRolesFor(Object applicationPart, Object url) {
    List<String> authorities = new ArrayList<String>();

    List<WebAccessRule> rules = accessRuleRepo.findMatchingRules(applicationPart, url);
    for (WebAccessRule accessRule : rules) {
      authorities.add(accessRule.getRole().getRoleName());
    }

    return authorities;
  }

  @Override
  public Collection<String> getRolesForType(Class<? extends BaseEntity> targetBaseEntityType, Object accessMethod) {
    throw new RuntimeException("WebAccessControlService does not impement getRolesForType(...)");
  }

}
