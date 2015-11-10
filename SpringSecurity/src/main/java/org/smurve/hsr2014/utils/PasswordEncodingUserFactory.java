package org.smurve.hsr2014.utils;

import org.smurve.hsr2014.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodingUserFactory implements UserFactory {

    private final BCryptPasswordEncoder encoder;

    public PasswordEncodingUserFactory(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User newUser(UserDetails details) {
        User newUser = new User(details.getUsername());
        String encodedPassword = encoder.encode(details.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setWrongPasswordCounter(0);
        return newUser;
    }

    @Override
    public void encodeUserPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    @Override
    public User newUser(String username, String password) {
        User newUser = new User(username.toLowerCase());
        String encodedPassword = encoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setWrongPasswordCounter(0);
        newUser.setActive(true);
        return newUser;
    }

    @Override
    public User newInactiveUser(String username, String password) {
        User newUser = newUser(username, password);
        newUser.setActive(false);
        return newUser;
    }
}
