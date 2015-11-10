package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Contract;
import org.smurve.hsr2014.domain.ContractType;

import java.util.List;

public interface ContractRepository {

    public void save(Contract newContract);

    public List<Contract> findByContractId(String contractId);

    public List<Contract> findByContractType(ContractType contractType);
}
