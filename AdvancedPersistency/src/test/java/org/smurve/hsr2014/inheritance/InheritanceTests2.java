package org.smurve.hsr2014.inheritance;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.domain2.*;
import org.smurve.hsr2014.domain.Action;
import org.smurve.hsr2014.domain2.Role;
import org.smurve.hsr2014.repo2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseTestContext2.class)
public class InheritanceTests2 {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccessRuleRepository accessRuleRepository;

    @Test
    public void testAll() {

        // User Wolfie works for Procter & Gamble
        Tenant tenant = new Tenant("Procter&Gamble");
        tenantRepository.save(tenant);
        User wolfie = new User("wolfie");
        wolfie.setTenant(tenant);
        userRepository.save(wolfie);

        // Wolfie has a project: "Reorg"
        Project project = new Project("Reorg");
        project.setTenant(tenant);
        project.setOwner(wolfie.getUsername());
        projectRepository.save(project);
        List<Project> projects = projectRepository.findByName("Reorg");
        Assert.assertEquals(1, projects.size());

        // There is a role "Administrator" in the systemm
        Role role = new Role("Administrator");
        role.setTenant(tenant);
        roleRepository.save(role);

        // The rule says: Wolfie's project can be changed by administrators
        String resourceType = Project.class.getSimpleName();
        String resourceId = project.getId();
        AccessRule rule = new AccessRule(resourceType, resourceId, Action.UPDATE, role);
        accessRuleRepository.save(rule);

        // Now see the SQL in the console out
        List<AccessRule> forProject = accessRuleRepository.findByResourceTypeAndResourceId(resourceType, resourceId);
        Assert.assertEquals(1, forProject.size());
    }

}
