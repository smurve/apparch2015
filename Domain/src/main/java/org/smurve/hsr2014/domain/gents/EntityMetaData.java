package org.smurve.hsr2014.domain.gents;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GENTS_ENTITY_METADATA")
public class EntityMetaData extends Labelled {

  private String entityName;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<AttributeMetaData> attributes;

  private EntityMetaData() {
  } // for JPA

  public EntityMetaData(String entityName, GentsLabel label) {
    super(label);
    this.entityName = entityName;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public Set<AttributeMetaData> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<AttributeMetaData> attributes) {
    this.attributes = attributes;
  }

}
