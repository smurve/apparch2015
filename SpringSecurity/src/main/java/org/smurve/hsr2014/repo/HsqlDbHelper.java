package org.smurve.hsr2014.repo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class HsqlDbHelper {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void cleanDb() {
        entityManager.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
    }
}
