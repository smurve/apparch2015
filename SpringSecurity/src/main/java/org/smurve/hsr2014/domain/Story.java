package org.smurve.hsr2014.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STORIES")
public class Story extends BaseEntity {

    private String title;
    private String description;
    private String criteria;
    private String status;
    private String type;
    private String reporter;
    private String assignee;

    public Story() {
    }

  public Story(String title, String description, String criteria, String status, String type, String reporter, String assignee) {
    super.setUniqueName(title);
        this.title = title;
        this.description = description;
        this.criteria = criteria;
        this.status = status;
        this.type = type;
        this.reporter = reporter;
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
