package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.Contract;
import org.smurve.hsr2014.domain.ContractType;
import org.smurve.hsr2014.domain.Customer;

import java.util.List;

/**
 * Created by wgiersche on 16/11/14.
 */
public interface ContractService {

    public Contract createContract (ContractType  type,  Customer customer );

    public Contract findContract ( String contractId );

    List<Contract> findByContractType(ContractType contractType);
}
