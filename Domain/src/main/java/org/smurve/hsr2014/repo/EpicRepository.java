package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, String> {

  public Epic findByName(String name);
}
