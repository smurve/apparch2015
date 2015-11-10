package org.smurve.hsr2014.domain2;


import org.smurve.hsr2014.domain.Action;

import javax.persistence.*;

@Entity (name = "AccessRule2")
@Table(name = "ACCESS_RULES2")
public class AccessRule extends SecureResource {

    private String resourceType;
    private String resourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private Action action;

    @ManyToOne
    private Role role;

    public AccessRule(String resourceType, String resourceId, Action action, Role role) {
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.action = action;
        this.role = role;
    }

    private AccessRule() {
    } // for JPA

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
