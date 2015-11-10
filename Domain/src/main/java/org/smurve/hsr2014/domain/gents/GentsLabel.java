package org.smurve.hsr2014.domain.gents;

import org.smurve.hsr2014.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GENTS_LABELS")
public class GentsLabel extends BaseEntity {

  @Column(name = "COL_SINGULAR")
  private String singular;

  @Column(name = "COL_PLURAL")
  private String plural;

  @Column(name = "COL_LANGUAGE")
  private String language;

  private GentsLabel() {
  } // for JPA

  public GentsLabel(String singular, String plural, String language) {
    this.singular = singular;
    this.plural = plural;
    this.language = language;
  }

  public String getSingular() {
    return singular;
  }

  public void setSingular(String singular) {
    this.singular = singular;
  }

  public String getPlural() {
    return plural;
  }

  public void setPlural(String plural) {
    this.plural = plural;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
