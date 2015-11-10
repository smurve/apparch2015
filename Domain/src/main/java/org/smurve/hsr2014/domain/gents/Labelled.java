package org.smurve.hsr2014.domain.gents;


import org.smurve.hsr2014.domain.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Labelled extends BaseEntity {

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private GentsLabel label;

  protected Labelled() {
  } // for JPA

  protected Labelled(GentsLabel label) {
    this.label = label;
  }

  public GentsLabel getLabel() {
    return label;
  }

  public void setLabel(GentsLabel label) {
    this.label = label;
  }
}
