package org.smurve.hsr2014.repo2;

import org.smurve.hsr2014.domain2.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wgiersche on 22/03/14.
 */
public interface RoleRepository extends JpaRepository<Role, String> {

    public Role findByRoleName(String roleName);
}
