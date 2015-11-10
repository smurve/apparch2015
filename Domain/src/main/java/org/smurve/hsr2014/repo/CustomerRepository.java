package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wgiersche on 16/11/14.
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
