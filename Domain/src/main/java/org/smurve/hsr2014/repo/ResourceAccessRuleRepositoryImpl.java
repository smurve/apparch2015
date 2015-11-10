package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.BaseEntity;
import org.smurve.hsr2014.domain.EntityOperation;
import org.smurve.hsr2014.domain.ResourceAccessRule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ResourceAccessRuleRepositoryImpl implements CustomResourceAccessRuleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ResourceAccessRuleRepositoryImpl() {
    }

    public ResourceAccessRuleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<ResourceAccessRule> findMatchingRules(Class<? extends BaseEntity> resourceType, EntityOperation operation) {
        TypedQuery<ResourceAccessRule> query = entityManager.createQuery("select ac from ResourceAccessRule"
                + " ac where ac.operation = ?1 AND ac.resourceType = ?2 ", ResourceAccessRule.class);
        query.setParameter(1, operation);
        query.setParameter(2, resourceType);

        return query.getResultList();
    }
}