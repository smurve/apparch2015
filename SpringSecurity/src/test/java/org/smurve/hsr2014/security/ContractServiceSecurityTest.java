package org.smurve.hsr2014.security;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.domain.*;
import org.smurve.hsr2014.repo.CustomerRepository;
import org.smurve.hsr2014.repo.HsqlDbHelper;
import org.smurve.hsr2014.repo.TenantRepository;
import org.smurve.hsr2014.repo.UserRepository;
import org.smurve.hsr2014.service.ContractService;
import org.smurve.hsr2014.utils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MultiTenantSecurityTestContext.class)
public class ContractServiceSecurityTest {

    @Autowired
    private HsqlDbHelper dbHelper;

    @Autowired
    private ContractService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Tenant schmitz, schulz;
    private User wolfie, harry, stevie;
    private Customer customer;

    @Before
    public void cleanUp() {
        dbHelper.cleanDb();
        SecurityContextHolder.getContext().setAuthentication(null);
    }


    @Test
    public void very_cheap_trick_to_make_maven_succeed_without_a_runnable_test() {

    }

    @Test
    public void test_post_filter_on_tenant() {

        given_Tenants_Schmitz_and_Schulz();
        given_Users_Wolfie_Schmitz_And_Harry_Schulz();
        given_authenticated("wolfie", "wolfie");
        given_Customer("John", "Doe");

        Contract wolfiesContract = when_saving_a_contract_for(ContractType.RENTAL);

        given_authenticated("harry", "harry");
        Contract harrysContract = when_saving_a_contract_for(ContractType.RENTAL);
        the_database_should_show(1, ContractType.RENTAL);
        the_database_should_not_show(wolfiesContract);

        given_authenticated("wolfie", "wolfie");
        the_database_should_show(1, ContractType.RENTAL);
        the_database_should_not_show(harrysContract);
    }



    private Contract when_saving_a_contract_for(ContractType contractType) {
        return service.createContract( contractType, customer);
    }


    private void the_database_should_show(int numberOfContracts, ContractType contractType) {
        List<Contract> contracts = service.findByContractType(contractType);
        Assert.assertEquals(numberOfContracts, contracts.size());
    }

    private void the_database_should_not_show(Contract contract ) {
        Contract found = service.findContract(contract.getContractId());
        Assert.assertNull ( "This contract shouldn't be visible here", found );
    }


    /*
    GIVEN...
 */
    private void given_Users_Wolfie_Schmitz_And_Harry_Schulz() {
        wolfie = userFactory.newUser("wolfie", "wolfie");
        wolfie.setTenant(schmitz);
        userRepository.save(wolfie);
        harry = userFactory.newUser("harry", "harry");
        harry.setTenant(schulz);
        userRepository.save(harry);
    }

    private void given_Customer ( String firstName, String lastName ) {
        customer = new Customer("customer1", firstName, lastName);
        customerRepository.save(customer);
    }

    private void given_Users_Wolfie_Schmitz_And_Stevie_Schmitz() {
        wolfie = userFactory.newUser("wolfie", "wolfie");
        wolfie.setTenant(schmitz);
        userRepository.save(wolfie);

        stevie = userFactory.newUser("stevie", "stevie");
        stevie.setTenant(schmitz);
        userRepository.save(stevie);

    }

    /*
        Authenticates the current thread of execution
     */
    private void given_authenticated(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void given_Tenants_Schmitz_and_Schulz() {

        schmitz = new Tenant("Schmitz");
        tenantRepository.save(schmitz);
        schulz = new Tenant("Schulz");
        tenantRepository.save(schulz);
    }


}
