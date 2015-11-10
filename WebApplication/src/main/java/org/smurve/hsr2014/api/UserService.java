package org.smurve.hsr2014.api;

import org.smurve.hsr2014.domain.User;

import java.util.List;

public interface UserService {

  User save(User user);

  int getWrongPasswordCountForUser(String username);

  void saveWrongPasswordCountForUser(String username, int count);

  User getUserById(String id);

  User getUserByName(String username);

  void deleteUser(String id);

  List<User> getAllUsers();

  String getPasswordByUserId(String id);

  List<User> getUsersByRole(String role);

  boolean hasUserRole(String userId, String roleName);

  boolean checkIfUserIsActive(String username);

  boolean userExists(String username);

}
