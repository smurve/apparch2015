package org.smurve.hsr2014.service;

import org.smurve.hsr2014.domain.Role;
import org.smurve.hsr2014.domain.User;
import org.smurve.hsr2014.repo.UserRepository;
import org.smurve.hsr2014.utils.MultiTenantUserDetails;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * fetches a User entity from our userService and returns a matching UserDetails object.
 */
//@Service("MultiTenantUserDetailsService")
public class MultiTenantUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public MultiTenantUserDetailsService(UserRepository repo) {
        this.userRepo = repo;
    }

    /**
     * This Method is used by container based authentication (web client)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ((username == null) || username.equals("")) {
            throw new AuthenticationCredentialsNotFoundException("No username provided in auth request");
        }

        try {
            User user = userRepo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("No user with name " + username + " found");
            }
            return new MultiTenantUserDetails(user.getUsername(), user.getPassword(),
                    getAuthorities(user), user.getTenant().getTenantId());
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("No user with name " + username + " found");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(user.getRoles().size());
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

}
