package org.smurve.hsr2014.domain2;

import org.smurve.hsr2014.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="Tenant2")
@Table(name = "TENANTS2")
public class Tenant extends BaseEntity {

    @Column(name = "TENANT_NAME")
    private String tenantId;

    private Tenant() {
    }

    public Tenant(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
