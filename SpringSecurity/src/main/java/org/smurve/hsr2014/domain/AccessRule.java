package org.smurve.hsr2014.domain;


import javax.persistence.*;

@Entity
@Table(name = "ACCESS_RULES")
public class AccessRule extends SecureResource {

    @ManyToOne
    private SecureResource resource;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private Action action;

    @ManyToOne
    private Role role;

    public AccessRule(SecureResource resource, Action action, Role role) {
        this.resource = resource;
        this.action = action;
        this.role = role;
    }

    @SuppressWarnings("unused")
	private AccessRule() {
    } // for JPA

    public SecureResource getResource() {
        return resource;
    }

    public void setResource(SecureResource resource) {
        this.resource = resource;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
