package org.smurve.hsr2014.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.api.StoryService;
import org.smurve.hsr2014.domain.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StoryController {

    static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "/stories")
    @ResponseBody
    public List<Story> stories() {

        return storyService.findAllStories();
    }

    @RequestMapping(value = "/story", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveStory(@RequestBody final Story story) {

      story.setUniqueName(story.getTitle());
      String id = storyService.createNewStory(story);
        String response = "{\"id\": " + id + ", \"msg\": \"Saved Story with id " + id + "\"}";
        logger.info("Saved story with id " + id);
        return response;
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeStory(@PathVariable final String id) {
        String response = null;
        try {
            storyService.removeStory(id);
            response = "Removed Story with id " + id;
        } catch (Exception e) {
            response = "Exception: " + e.getMessage();
        } finally {
            logger.info(response);
            return response;
        }
    }
}
