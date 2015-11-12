package org.smurve.hsr2014.domain.gents;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GENTS_ATTRIBUTE_METADATA")
public class AttributeMetaData extends Labelled implements Comparable {

  private int orderByIdx;

  private AttributeMetaData() {
  } // for JPA

  public AttributeMetaData(String uniqueName, GentsLabel label, int orderByIdx) {
    super(label);
    setUniqueName(uniqueName);
    this.orderByIdx = orderByIdx;
  }

  public int getOrderByIdx() {
    return orderByIdx;
  }

  public void setOrderByIdx(int orderByIdx) {
    this.orderByIdx = orderByIdx;
  }

  @Override
  public int compareTo(Object o) {
    return ((AttributeMetaData) o).orderByIdx - orderByIdx;
  }
}
