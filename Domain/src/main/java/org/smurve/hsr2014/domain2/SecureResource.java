package org.smurve.hsr2014.domain2;

import org.smurve.hsr2014.domain.BaseEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class SecureResource extends BaseEntity {

    // User name of the owner
    private String owner;

    private String TenantId;

    //@Transient
    @ManyToOne
    private Tenant tenant;

    protected SecureResource() {
    }

    protected SecureResource(String id) {
        super(id);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
