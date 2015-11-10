package org.smurve.hsr2014.inheritance;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smurve.hsr2014.domain.*;
import org.smurve.hsr2014.domain.AccessRule;
import org.smurve.hsr2014.domain.Project;
import org.smurve.hsr2014.domain.Role;
import org.smurve.hsr2014.domain.Tenant;
import org.smurve.hsr2014.domain.User;
import org.smurve.hsr2014.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseTestContext.class)
public class InheritanceTests {


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
        AccessRule rule = new AccessRule(projects.get(0), Action.UPDATE, role);
        accessRuleRepository.save(rule);

        // Now see the SQL in the console out
        List<AccessRule> forProject = accessRuleRepository.findByResource(project);
        Assert.assertEquals(1, forProject.size());
    }

}
