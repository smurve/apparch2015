package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.WebAccessRule;

import java.util.List;

public interface CustomWebAccessRuleRepository {
  /**
   * returns a list of webaccessRules for the given applicationPart
   */
  List<WebAccessRule> findMatchingRules(Object applicationPart, Object url);
}
