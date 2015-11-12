package org.smurve.hsr2014.domain;

import javax.persistence.*;

/**
 * Created by wgiersche on 28/02/14.
 */
@Entity(name = "Contract")
@Table(name = "CONTRACTS")
public class Contract extends SecureResource {

    private String contractId;

    @ManyToOne (fetch = FetchType.EAGER)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    private Contract() {
    } // for JPA

    public Contract(String contractId, ContractType contractType, Customer customer ) {
        this.contractId = contractId;
        this.contractType = contractType;
        this.customer = customer;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }
}
