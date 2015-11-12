package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.Contract;
import org.smurve.hsr2014.domain.ContractType;
import org.smurve.hsr2014.domain.Customer;
import org.smurve.hsr2014.repo.ContractRepository;
import org.smurve.hsr2014.utils.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    @Qualifier("unsecuredRepo")
    private ContractRepository repo;

    @Override
    @Transactional
    public Contract createContract(ContractType type, Customer customer) {

        Contract contract = new Contract(RandomId.nextId(), type, customer);
        repo.save( contract );
        return contract;
    }

    @Override
    @Transactional
    public Contract findContract(String contractId) {
        List<Contract> contracts =  repo.findByContractId(contractId);
        if ( contracts.size() > 1 ) {
            throw new IllegalStateException("Database inconsistent: Found more than one contract for id " + contractId);
        }
        if (contracts.size() == 0 ) {
            return null;
        } else {
            return contracts.get(0);
        }

    }

    @Override
    @Transactional
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<Contract> findByContractType(ContractType contractType) {
        return repo.findByContractType(contractType);
    }
}
