package org.smurve.hsr2014.domain.gents;


import org.smurve.hsr2014.domain.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GENTS_METADATA")
public class GentsMetaData extends BaseEntity {

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private Set<EntityMetaData> entities;

  public Set<EntityMetaData> getEntities() {
    return entities;
  }

  public void setEntities(Set<EntityMetaData> entities) {
    this.entities = entities;
  }
}
