package org.smurve.hsr2014.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "SECURE_RESOURCES")
public abstract class SecureResource extends BaseEntity {

    // User name of the owner
    private String owner;

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
