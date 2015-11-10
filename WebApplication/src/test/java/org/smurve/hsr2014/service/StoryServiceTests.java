package org.smurve.hsr2014.service;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.api.StoryService;
import org.smurve.hsr2014.domain.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServicesTestContext.class)

public class StoryServiceTests {

    @Autowired
    private StoryService storyService;

    private Story aStory;

    @Test
    public void testCrud() {

        Assert.assertNotNull(storyService);

        given_a_story();

        when_saving_a_new_story(aStory);

        it_should_be_visible_in_the_database();

        it_should_be_possible_see_all_stories(1);
    }

    private void it_should_be_possible_see_all_stories(int number) {
        int actual = storyService.findAllStories().size();
        Assert.assertEquals(actual, number);
    }

    private void it_should_be_visible_in_the_database() {
      String id = aStory.getId();
        Assert.assertNotNull(storyService.findById(id));
    }

    private void when_saving_a_new_story(Story story) {
        storyService.createNewStory(story);
    }

    private void given_a_story() {
      aStory = new Story("Title", "description", "criteria", "Back Log", "Bug", "Wolfie", "Harry");
    }
}
