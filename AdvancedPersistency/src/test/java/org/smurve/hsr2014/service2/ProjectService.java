package org.smurve.hsr2014.service2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.domain2.Project;
import org.smurve.hsr2014.repo2.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository repo;


    @CacheEvict(value = "projects", key = "#project.name")
    public void removeProject ( Project project ) {
        repo.delete( project );
    }

    @Transactional
    @CacheEvict(value = "projects", key = "#project.name")
    public void createProject ( Project project ) {
        repo.save ( project );
    }

    @Cacheable( value = "projects", key = "#name")
    @Transactional
    public Project findProject ( String name ) {

        LOGGER.info("accessing DB...");

        List<Project> projects = repo.findByName( name );
        if ( projects.size() > 1 ) {
            throw new IllegalStateException("There should be only one project of name " + name);
        }
        if ( projects.size() == 0 ) {
            return null;
        } else {
            return projects.get(0);
        }
    }
}
