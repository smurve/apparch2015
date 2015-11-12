package org.smurve.hsr2014.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An {@code AccessRule} represents a permission granted to a role for performing an operation on a resource.
 * <p>
 * An {@code AccessRule} provides the following information:
 * <ul>
 * <li>A {@code role} that specifies who is affected by the rule</li>
 * <li>A {@code resourceType} that specifies where the rule applies</li>
 * <li>An {@code operation} that specifies what permission the rule grants</li>
 * </ul>
 */
@Entity
@Table(name = "RESOURCE_ACCESS_RULES")
public class ResourceAccessRule extends BaseEntity {
    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    private Class<? extends BaseEntity> resourceType;

    @Enumerated(EnumType.STRING)
    private EntityOperation operation;

    public ResourceAccessRule() {
        // empty constructor
    }

    public ResourceAccessRule(Role role, Class<? extends BaseEntity> resourceType, EntityOperation operation) {
        this.role = role;
        this.resourceType = resourceType;
        this.operation = operation;
    }

    @NotNull
    public Class<? extends BaseEntity> getResourceType() {
        return resourceType;
    }

    public void setResourceType(Class<? extends BaseEntity> resourceType) {
        this.resourceType = resourceType;
    }

    @NotNull
    public EntityOperation getOperation() {
        return operation;
    }

    public void setOperation(EntityOperation operation) {
        this.operation = operation;
    }

    @NotNull
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}