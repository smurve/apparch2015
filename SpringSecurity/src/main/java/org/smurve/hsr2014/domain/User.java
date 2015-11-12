package org.smurve.hsr2014.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User extends SecureResource {

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    private int wrongPasswordCounter;

    private boolean active;

    private User() {
    } // for JPA

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWrongPasswordCounter() {
        return wrongPasswordCounter;
    }

    public void setWrongPasswordCounter(int wrongPasswordCounter) {
        this.wrongPasswordCounter = wrongPasswordCounter;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
