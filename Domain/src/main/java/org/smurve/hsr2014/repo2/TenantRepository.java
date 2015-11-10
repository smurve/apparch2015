package org.smurve.hsr2014.repo2;

import org.smurve.hsr2014.domain2.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

    public Tenant findByTenantId(String tenantId);
}
