package org.smurve.hsr2014.inheritance;

import configuration.SpringJpaConfiguration;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.domain.Epic;
import org.smurve.hsr2014.repo.EpicRepository;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.mysql.mysql.MySqlConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EpicRepoTests {


  @Autowired
  private EpicRepository repository;


  @Test
  public void testPersistAndRetrieve() {

    Epic theEpic = new Epic("aName");

    theEpic.setId("someID");

    repository.save(theEpic);

    String id = theEpic.getId();

    theEpic = repository.findByName("aName");

    Assert.assertNotNull(theEpic);
  }

  @Configuration
  @Import(SpringJpaConfiguration.class)
  @EnableAspectJAutoProxy
  @EnableJpaRepositories(basePackages = "org.smurve.hsr2014.repo")
  @ComponentScan(basePackages = {"org.smurve.hsr2014.repo"})
  public static class InheritanceTestContext {

    @Bean
    public DatabaseConnector databaseConnector() {
      return new MySqlConnector();
    }

  }
}
