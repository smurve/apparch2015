package org.smurve.hsr2014.repo.gents;


import org.smurve.hsr2014.repo.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GentDataRepositoryImpl implements GentDataRepository {


  private Map<String, JpaRepository> repos = new HashMap<>();

  @Autowired
  private MetaDataRepository metaDataRepo;

  @Autowired
  private StoryRepository storyRepo;

  @PostConstruct
  private void initialize() {
    repos.put("EntityMetaData", metaDataRepo);
    repos.put("Story", storyRepo);
  }

  @Override
  public List findAll(String gentClass) {
    JpaRepository repo = repos.get(gentClass);
    if (repo == null) {
      throw new UnsupportedOperationException("Entity not supported: '" + gentClass + "'");
    }
    return repo.findAll();
  }
}
