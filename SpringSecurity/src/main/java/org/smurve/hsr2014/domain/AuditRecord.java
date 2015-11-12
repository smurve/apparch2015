package org.smurve.hsr2014.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_RECORDS")
public class AuditRecord extends BaseEntity {
    private String user;
    private String repo;
    private String classifier;
    private String arg;
  private String objectId;

    private AuditRecord() {
    } // for JPA

  public AuditRecord(String user, String repo, String classifier, String paramClass, String objectId) {
        this.user = user;
        this.repo = repo;
        this.classifier = classifier;
        this.arg = paramClass;
        this.objectId = objectId;
    }

    public String getRepo() {
        return repo;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getArg() {
        return arg;
    }

  public String getObjectId() {
        return objectId;
    }

    public String getUser() {
        return user;
    }
}
