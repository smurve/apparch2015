package org.smurve.hsr2014.utils;


import org.smurve.hsr2014.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserFactory {
    User newUser(UserDetails details);

    User newInactiveUser(String username, String password);

    User newUser(String username, String password);

    void encodeUserPassword(User user);
}
