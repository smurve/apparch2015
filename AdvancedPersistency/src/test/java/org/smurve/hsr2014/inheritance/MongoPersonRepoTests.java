package org.smurve.hsr2014.inheritance;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.mongo.Address;
import org.smurve.hsr2014.domain.mongo.Person;
import org.smurve.hsr2014.domain.mongo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongoPersonRepoTests {

    static final Logger logger = LoggerFactory.getLogger(MongoPersonRepoTests.class);

    @Autowired
    private MongoTemplate template;

    @Autowired
    private PersonRepo repo;

    @Test
    public void testSaveAndRetrieveSingleWithTemplate() throws UnknownHostException {

        Person joe = new Person("Joe", 34, new Address("Neunbrunnenstrasse", "47", "8050", "Zürich"));

        template.insert(joe);

        Person found = template.findOne(new Query(where("name").is("Joe")), Person.class);

        Assert.assertEquals("Joe", found.getName());

        Assert.assertEquals("47", found.getAddress().getNo());

        template.dropCollection("person");

    }

    @Test
    public void testSaveAndRetrieveSingleWithRepository() {

        Person joe = new Person("Joe", 34, new Address("Neunbrunnenstrasse", "47", "8050", "Zürich"));

        repo.save(joe);

        List<Person> personsFound = repo.findByName("Joe");

        Assert.assertEquals(1, personsFound.size());

        joe = personsFound.get(0);

        Assert.assertEquals("47", joe.getAddress().getNo());

        repo.deleteAll();

    }




    public static class CustomMongoTypeMapper extends DefaultMongoTypeMapper {
    }

    @Configuration
    @EnableMongoRepositories(basePackages = "org.smurve.hsr2014.domain.mongo")
    public static class MongoPersonRepoTestContext extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "test";
        }

        @Override
        public @Bean
        Mongo mongo() throws Exception {
            return new MongoClient();
        }

        @Bean
        @Override
        public MappingMongoConverter mappingMongoConverter() throws Exception {
            MappingMongoConverter mmc = super.mappingMongoConverter();
            mmc.setTypeMapper(customTypeMapper());
            return mmc;
        }

        @Bean
        public MongoTypeMapper customTypeMapper() {
            return new CustomMongoTypeMapper();
        }

        @Override
        protected String getMappingBasePackage() {
            return "org.smurve.hsr2014.domain.mongo";
        }
    }

}
