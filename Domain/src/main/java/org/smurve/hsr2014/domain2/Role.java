package org.smurve.hsr2014.domain2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="Role2")
@Table(name = "ROLES2")
public class Role extends SecureResource {

    @Column(name = "ROLE_NAME")
    private String roleName;

    public Role() {
    } // for JPA

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
