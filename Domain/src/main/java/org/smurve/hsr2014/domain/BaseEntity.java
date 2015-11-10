package org.smurve.hsr2014.domain;

import org.smurve.hsr2014.utils.RandomId;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @Column(name = "PK_ID")
  private String id = RandomId.nextId();

  private String uniqueName;

  protected BaseEntity() {
  }

  protected BaseEntity(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUniqueName() {
    return uniqueName;
  }

  public void setUniqueName(String uniqueName) {
    this.uniqueName = uniqueName;
  }
}
