package org.smurve.hsr2014.exercise;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.ProductType;
import org.smurve.hsr2014.domain.Product;
import org.smurve.hsr2014.inheritance.DatabaseTestContext;
import org.smurve.hsr2014.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseTestContext.class)
public class YourUnitTest {

    @Autowired
    private ProductRepository productRepo;

    @Test
    public void testAll () {

        Product toy = new Product("T001", "Barbie", ProductType.HOUSE_HOLD);

        productRepo.save(toy);

        toy = productRepo.findById ( "T001");

        Assert.assertEquals("Barbie", toy.getName());
    }
}
