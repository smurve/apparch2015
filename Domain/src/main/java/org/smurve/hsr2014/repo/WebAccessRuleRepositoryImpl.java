package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.WebAccessRule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class WebAccessRuleRepositoryImpl implements CustomWebAccessRuleRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<WebAccessRule> findMatchingRules(Object applicationPart, Object url) {
    TypedQuery<WebAccessRule> query = entityManager.createQuery("select ac from WebAccessRule"
      + " ac where ac.url = ?1 AND ac.applicationPart = ?2 ", WebAccessRule.class);
    query.setParameter(1, url);
    query.setParameter(2, applicationPart);

    return query.getResultList();
  }
}
