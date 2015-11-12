package org.smurve.hsr2014.security;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.domain.*;
import org.smurve.hsr2014.repo.*;
import org.smurve.hsr2014.utils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * This is a scenario test - not a unit test. It tests not only functions
 * as implemented in methods but also the wiring of the context.
 * This particular scenario test is actually used as a demo for the
 * various aspects that come with implementing advanced security concepts
 * with Spring Security.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MultiTenantSecurityTestContext.class)
public class MultiTenantSecurityTest {

  @Autowired
  @Qualifier ("securedRepo")
  private ContractRepository contractRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TenantRepository tenantRepository;

  @Autowired
  private AuditRecordRepository auditRepo;

  @Autowired
  private HsqlDbHelper dbHelper;


  private User stevie;
  private User wolfie;
  private User harry;
  private Tenant schmitz;
  private Tenant schulz;
  private Customer customer;

  @Before
  public void cleanUp() {
    dbHelper.cleanDb();
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  public void test_access_if_contract_has_adequate_tenant() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();

    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    when_saving_a_contract_for(ContractType.CONSULTING);

    contract_should_end_up_in_db();

    the_database_should_show(1, ContractType.CONSULTING);

  }

  @Test
  public void test_access_denied_when_not_authenticated() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();


    try {
      given_authenticated("wolfie", "wrong password");
    } catch (AuthenticationException e) {
      // ignore
    }
    try {
      given_Customer("John", "Doe");
      when_saving_a_contract_for(ContractType.PURCHASE);
      Assert.fail("access should have been denied");
    } catch (Exception e) {
      if ( !( e instanceof AuthenticationCredentialsNotFoundException )) {
        Assert.fail ("expecting bad authentication");
      }
    }

  }

  @Test
  public void test_access_granted_when_same_tenant() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();
    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    when_saving_a_contract_for(ContractType.RENTAL);

    contract_should_end_up_in_db();
  }

  @Test
  public void test_post_filter_on_tenant() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();
    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    when_saving_a_contract_for(ContractType.RENTAL);

    given_authenticated("harry", "harry");

    when_saving_a_contract_for(ContractType.RENTAL);

    the_database_should_show(1, ContractType.RENTAL);

    given_authenticated("wolfie", "wolfie");

    the_database_should_show(1, ContractType.RENTAL);
  }

  @Test
  public void test_audit_records() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();
    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    when_saving_a_contract_for(ContractType.RENTAL);

    there_shoud_be_an_audit_record_for("wolfie");
  }

  @Test
  public void test_audit_records_with_exceptions() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Harry_Schulz();
    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    // saving for the wrong tenant
    try {
      when_saving_a_contract_for(ContractType.RENTAL, schulz);
      Assert.fail("Expected Exception, but there was none.");
    } catch (AccessDeniedException ade) {
      // we expected that
    }

    // still the access attempt is recorded
    there_shoud_be_an_audit_record_for("wolfie");
  }

  @Test
  public void test_ownership_restrictions() {

    given_Tenants_Schmitz_and_Schulz();
    given_Users_Wolfie_Schmitz_And_Stevie_Schmitz();
    given_authenticated("wolfie", "wolfie");
    given_Customer("John", "Doe");

    String contractId = when_saving_a_contract_for(ContractType.CONSULTING);

    given_authenticated("stevie", "stevie");

    Contract contract = when_accessing_contract(contractId);
    if ( contract != null ) {
      Assert.fail("access should have been denied");
    }
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


  /*
      WHEN
   */
  private String when_saving_a_contract_for(ContractType type, Tenant... tenants) {
    Contract contract = new Contract("newContract", type, customer);
    if ( tenants.length == 1 ) {
      contract.setTenant(tenants[0]);
    }
    contractRepository.save(contract);
    return contract.getContractId();
  }

  private Contract when_accessing_contract(String id) {
    List<Contract> contracts = contractRepository.findByContractId(id);
    if ( contracts.size() > 1 ) {
      throw new IllegalStateException("This is not a realistic case");
    }
    if ( contracts.size() == 1 ) {
      return contracts.get(0);
    } else {
      return null;
    }
  }

  /*
     SHOULD...
   */
  private void there_shoud_be_an_audit_record_for(String user) {
    List<AuditRecord> records = auditRepo.findByUser(user);
    Assert.assertEquals(1, records.size());
  }

  private void access_should_be_denied(Exception e, String... fragments) {
    Assert.assertEquals(AccessDeniedException.class, e.getClass());
    for (String fragment : fragments) {
      Assert.assertTrue("Fragment '" + fragment + "' should be found in message: " + e.getMessage(),
        e.getMessage().contains(fragment));
    }
  }

  private void contract_should_end_up_in_db() {
    List<Contract> contracts = contractRepository.findByContractId("newContract");
    Assert.assertEquals(1, contracts.size());
  }

  private void the_database_should_show(int number, ContractType type) {
    List<Contract> contracts = contractRepository.findByContractType(type);
    Assert.assertEquals(number, contracts.size());
  }


}
