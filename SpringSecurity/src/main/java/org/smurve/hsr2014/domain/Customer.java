package org.smurve.hsr2014.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wgiersche on 28/02/14.
 */
@Entity(name = "Customer")
@Table(name = "CUSTOMERS")
public class Customer extends SecureResource {

    private String customerId;

    private String firstName;
    private String lastName;

    private Customer() {
    } // for JPA

    public Customer(String customerId, String firstName, String lastName ) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
