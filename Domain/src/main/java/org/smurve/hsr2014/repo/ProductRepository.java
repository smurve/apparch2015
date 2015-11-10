package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
@Transactional
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save ( Product product ) {
        entityManager.persist( product );
    }

    public Product findById(String id) {

        TypedQuery<Product> query = entityManager.createQuery("from Product where id = :id", Product.class);

        query.setParameter("id", id );

        return query.getSingleResult();
    }
}
