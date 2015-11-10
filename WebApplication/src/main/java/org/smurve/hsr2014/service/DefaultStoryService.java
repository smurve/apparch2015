package org.smurve.hsr2014.service;

import org.smurve.hsr2014.api.StoryService;
import org.smurve.hsr2014.domain.Story;
import org.smurve.hsr2014.repo.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultStoryService implements StoryService {

    @Autowired
    private StoryRepository repo;

  public Story findById(String id) {
        return repo.findById(id);
    }

    @Override
    public List<Story> findByStatus(String status) {
        return null;
    }

    @Override
    public String createNewStory(Story story) {
        repo.save(story);
        return story.getId();
    }

    @Override
    public List<Story> findAllStories() {
        return repo.findAll();
    }

    @Override
    public void updateStory(Story story) {
        repo.save(story);
    }

    @Override
    public void removeStory(String id) {
        repo.delete(id);
    }
}
