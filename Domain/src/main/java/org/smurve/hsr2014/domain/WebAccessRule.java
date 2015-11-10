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
@Table(name = "AUTH_WEB_ACCESS_RULE")
public class WebAccessRule extends BaseEntity {
  @OneToOne(fetch = FetchType.EAGER)
  private Role role;

  private String applicationPart;

  private String url;

  public WebAccessRule() {
    // empty constructor
  }

  public WebAccessRule(Role role, String applicationPart, String url) {
    this.role = role;
    this.applicationPart = applicationPart;
    this.url = url;
  }

  @NotNull
  public String getApplicationPart() {
    return applicationPart;
  }

  public void setApplicationPart(String applicationPart) {
    this.applicationPart = applicationPart;
  }

  @NotNull
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Transient
  public String getLabel() {
    return getUrl() + " " + getApplicationPart() + " [" + role + "]";
  }

  @NotNull
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
