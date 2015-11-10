package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, String> {

    public List<Project> findByName(String name);
}
