package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Contract;
import org.smurve.hsr2014.domain.ContractType;
import org.smurve.hsr2014.security.Audit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Qualifier("securedRepo")
public class JpaContractRepository implements ContractRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Audit(classifier = "create")
    @PreAuthorize("hasPermission(#newContract, 'createOrUpdate')")
    @Transactional
    public void save(Contract newContract) {

        entityManager.persist(newContract);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<Contract> findByContractId(String contractId) {
        TypedQuery<Contract> query = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.contractId = :contractId", Contract.class);
        query.setParameter("contractId", contractId);
        return query.getResultList();
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<Contract> findByContractType(ContractType contractType) {
        TypedQuery<Contract> query = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.contractType = :contractType", Contract.class);
        query.setParameter("contractType", contractType);
        return query.getResultList();
    }
}
