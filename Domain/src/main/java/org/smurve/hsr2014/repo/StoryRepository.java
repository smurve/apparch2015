package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, String> {

  public Story findById(String id);

    public List<Story> findByStatus(String status);
}
