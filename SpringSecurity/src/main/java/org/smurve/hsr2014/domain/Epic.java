package org.smurve.hsr2014.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Epic")
public class Epic extends BaseEntity {

  private Epic() {
  } // for JPA

  private String name;

  public Epic(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
