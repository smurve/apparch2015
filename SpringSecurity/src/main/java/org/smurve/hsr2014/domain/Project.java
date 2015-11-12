package org.smurve.hsr2014.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "PROJECTS")
public class Project extends SecureResource {

    @Column(name = "PROJECT_NAME")
    private
    @NotNull
    String name;

    @Column(name = "START_DATE")
    private Date startDate;

    private Project() {
    }// for JPA

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
