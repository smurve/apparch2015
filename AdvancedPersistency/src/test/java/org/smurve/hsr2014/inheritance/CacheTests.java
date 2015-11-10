package org.smurve.hsr2014.inheritance;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain.Project;
import org.smurve.hsr2014.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration (classes = {DatabaseTestContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(CacheTests.class);

    @Autowired
    private ProjectService projectService;

    @Test
    public void testAll () {

        projectService.createProject(new Project("Area 51"));
        Project apollo13 = new Project("Apollo 13");
        projectService.createProject( apollo13);
        projectService.createProject(new Project("WW II"));
        projectService.createProject(new Project("Osama"));
        projectService.createProject(new Project("SaveTheWhales"));

        Project found;

        LOGGER.info("first time from db");

        found = projectService.findProject("Apollo 13");
        System.out.println("found " + found.getName());
        System.out.println();

        LOGGER.info("now from cache");

        found = projectService.findProject("Apollo 13");
        System.out.println(found == null ? "not found" : "found " + found.getName());
        System.out.println();

        LOGGER.info("others still from DB");

        found = projectService.findProject("Area 51");
        System.out.println(found == null ? "not found" : "found " + found.getName());
        System.out.println();


        LOGGER.info (" now it's gone");

        projectService.removeProject(apollo13);
        found = projectService.findProject("Apollo 13");
        System.out.println(found == null ? "not found" : "found " + found.getName());
        System.out.println();

        LOGGER.info (" and back again");

        projectService.createProject(apollo13);
        found = projectService.findProject("Apollo 13");
        System.out.println(found == null ? "not found" : "found " + found.getName());
        System.out.println();

        LOGGER.info (" and again from cache");

        found = projectService.findProject("Apollo 13");
        System.out.println(found == null ? "not found" : "found " + found.getName());
        System.out.println();
    }

}
