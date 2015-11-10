package org.smurve.hsr2014.service;

import org.apache.commons.lang3.StringUtils;
import org.smurve.hsr2014.api.UserService;
import org.smurve.hsr2014.domain.User;
import org.smurve.hsr2014.repo.UserRepository;
import org.smurve.hsr2014.utils.PasswordEncodingUserFactory;
import org.smurve.hsr2014.utils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repo;

  @Autowired
  public UserServiceImpl(UserRepository repo) {
    this.repo = repo;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  @PreAuthorize("hasPermission(#user, 'createOrUpdate')")
  public User save(User user) {
    encodePasswordIfChangedOrNew(user);
    usePreviousPasswordIfUserDidNotChangeIt(user);
    return repo.save(user);
  }

  private void encodePasswordIfChangedOrNew(User user) {
    if (passwordChangedOrNew(user)) {
      UserFactory userFactory = new PasswordEncodingUserFactory(new BCryptPasswordEncoder());
      userFactory.encodeUserPassword(user);
    }
  }

  private void usePreviousPasswordIfUserDidNotChangeIt(User user) {
    if (StringUtils.isEmpty(user.getPassword()) && user.getId() != null) {
      user.setPassword((repo.findOne(user.getId())).getPassword());
    }
  }

  private boolean passwordChangedOrNew(User user) {
    if (user.getId() == null) {
      // it's a new user to be inserted
      return true;
    }

    if (StringUtils.isEmpty(user.getPassword())) {
      return false;
    }

    return true;
  }

  @Override
  public User getUserById(String id) {
    User user = repo.findOne(id);
    clearPassword(user);
    return user;
  }

  private void clearPassword(User user) {
    user.setPassword(null);
  }

  @Override
  @PreAuthorize("hasPermission('com.zuehlke.hatch.demo.domain.auth.User', 'read')")
  public User getUserByName(String username) {
    User user = repo.findByUsername(username);
    clearPassword(user);
    return user;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  @PreAuthorize("hasPermission(#id, 'com.zuehlke.hatch.demo.domain.auth.User', 'delete')")
  public void deleteUser(String id) {
    repo.delete(id);
  }

  @Override
  @PostFilter("hasPermission(filterObject, 'readAll')")
  public List<User> getAllUsers() {
    List<User> users = repo.findAll();
    for (User user : users) {
      clearPassword(user);
    }
    return users;
  }

  @Override
  public int getWrongPasswordCountForUser(String username) {
    User user = repo.findByUsername(username);
    if (user == null) {
      throw new NoResultException();
    }
    return user.getWrongPasswordCounter();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void saveWrongPasswordCountForUser(String username, int count) {
    User user = repo.findByUsername(username);
    user.setWrongPasswordCounter(count);
  }

  @Override
  public String getPasswordByUserId(String id) {
    User user = repo.findOne(id);
    if (user == null) {
      throw new NoResultException();
    }
    return user.getPassword();
  }

  @Override
  public List<User> getUsersByRole(String role) {
    return repo.findUsersByRole(role);
  }

  @Override
  public boolean hasUserRole(String userId, String roleName) {
    if (repo.hasUserRole(userId, roleName) != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean checkIfUserIsActive(String username) {
    User user = repo.findByUsername(username);
    if (user == null) {
      throw new NoResultException();
    }
    return user.isActive();
  }

  @Override
  public boolean userExists(String username) {
    return (repo.findByUsername(username) != null);
  }

}
