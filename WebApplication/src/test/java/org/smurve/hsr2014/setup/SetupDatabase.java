package org.smurve.hsr2014.setup;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.api.StoryService;
import org.smurve.hsr2014.domain.Story;
import org.smurve.hsr2014.domain.gents.AttributeMetaData;
import org.smurve.hsr2014.domain.gents.EntityMetaData;
import org.smurve.hsr2014.domain.gents.GentsLabel;
import org.smurve.hsr2014.domain.gents.GentsMetaData;
import org.smurve.hsr2014.repo.gents.AttributeMetaDateRepository;
import org.smurve.hsr2014.repo.gents.GentLabelRepository;
import org.smurve.hsr2014.repo.gents.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SetupContext.class)
public class SetupDatabase {

  private static final Logger logger = LoggerFactory.getLogger(SetupDatabase.class);

    @Autowired
    private StoryService storyService;

  @Autowired
  private MetaDataRepository metaDataRepository;

  @Autowired
  private AttributeMetaDateRepository attributeMetaDataRepository;

  @Autowired
  private GentLabelRepository gentsLabelRepository;

    @Test
    public void setup() {
        List<Story> allStories = Arrays.asList(
          new Story("Initial Version", "description", "criterion", "To Do", "Feature", "Wolfie", "Harry"),
          new Story("Scaffolding", "description", "criterion", "Back Log", "Bug", "Wolfie", "Harry"),
          new Story("Database Layer", "description", "criterion", "Verified", "Enhancement", "Wolfie", "Harry"),
          new Story("Database tests", "description", "criterion", "In Progress", "Feature", "Wolfie", "Harry"),
          new Story("Service layer", "description", "criterion", "In Progress", "Bug", "Wolfie", "Harry"),
          new Story("Service tests", "description", "criterion", "To Do", "Feature", "Wolfie", "Harry"),
          new Story("Initial Web page", "description 2", "criterion", "Review", "Spike", "Wolfie", "Harry"),
          new Story("Restful Services", "description 3", "criterion", "Done", "Enhancement", "Wolfie", "Harry"),
          new Story("Autom. Tests", "description 4", "criterion", "Verified", "Feature", "Wolfie", "Harry"),
          new Story("Refactoring", "description 5", "criterion", "Back Log", "Bug", "Wolfie", "Harry"));

        for ( Story story : allStories ) {
            storyService.createNewStory(story);
        }

        Assert.assertEquals(10, allStories.size());

      setupMetaData();
    }

  public void setupMetaData() {
    GentsMetaData metaData = new GentsMetaData();
    metaData.setId("1");

    GentsLabel storyLabel = new GentsLabel("Story", "Stories", "en");
    //gentsLabelRepository.save(storyLabel);
    EntityMetaData storyMetaData = new EntityMetaData("Story", storyLabel);

    GentsLabel entityMdLabel = new GentsLabel("Entity Meta Data", "Entity Meta Data", "en");
    //gentsLabelRepository.save(entityMdLabel);
    EntityMetaData entityMetaData = new EntityMetaData("EntityMetaData", entityMdLabel);

    Set<EntityMetaData> entities = new HashSet<>();
    entities.addAll(Arrays.asList(storyMetaData, entityMetaData));
    metaData.setEntities(entities);


    GentsLabel titleLabel = new GentsLabel("Title", "Titles", "en");
    AttributeMetaData title = new AttributeMetaData("title", titleLabel, 1);

    GentsLabel descriptionLabel = new GentsLabel("Description", "Descriptions", "en");
    AttributeMetaData description = new AttributeMetaData("description", descriptionLabel, 2);

    GentsLabel criteriaLabel = new GentsLabel("Criterion", "Criteria", "en");
    AttributeMetaData criteria = new AttributeMetaData("criteria", criteriaLabel, 3);

    GentsLabel statusLabel = new GentsLabel("Status", "Statuses", "en");
    AttributeMetaData status = new AttributeMetaData("status", statusLabel, 4);

    GentsLabel typeLabel = new GentsLabel("Type", "Types", "en");
    AttributeMetaData type = new AttributeMetaData("type", typeLabel, 5);

    GentsLabel reporterLabel = new GentsLabel("Reporter", "Reporters", "en");
    AttributeMetaData reporter = new AttributeMetaData("reporter", reporterLabel, 6);

    GentsLabel assigneeLabel = new GentsLabel("Assignee", "Assignees", "en");
    AttributeMetaData assignee = new AttributeMetaData("assignee", assigneeLabel, 7);

    TreeSet<AttributeMetaData> attributes = new TreeSet<>();
    attributes.addAll(Arrays.asList(title, description, criteria, status, type, reporter, assignee));
    storyMetaData.setAttributes(attributes);


    metaData = metaDataRepository.findOne("1");
    Assert.assertNotNull(metaData);

  }


}
