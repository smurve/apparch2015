package org.smurve.hsr2014.api;

import org.smurve.hsr2014.domain.Story;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StoryService {

  public Story findById(String id);

    public List<Story> findByStatus(String status);

  String createNewStory(Story story);

    List<Story> findAllStories();

    void updateStory(Story story);

  void removeStory(String id);
}
