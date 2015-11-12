package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

    public Tenant findByTenantId(String tenantId);
}
