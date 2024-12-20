package com.ganecardshop.service;

import java.util.List;
import java.util.Optional;

import com.ganecardshop.model.Profile;
import com.ganecardshop.model.User;

public interface UserService {

    User findByEmail(String email);
    User saveUser(User user);
    List<User> findAllUsers();
    Optional<User> findById(int id);
    void deleteUser(int id);
    Optional<Profile> findProfileByUserId(int userId);
    void registerUserAccount(String user_email, String user_password, String profile_name, String profile_phone, String profile_gender, java.sql.Date profile_dateOfBirth);
    void addUserAndProfile(String user_email, String user_role, String profile_name, String profile_phone, String profile_gender, java.sql.Date profile_dateOfBirth);
    User getUserWithProfile(int id);
    void updateRole(int id, String role);
    List<User> findUsersBySearchCriteria(String searchCriteria, String searchValue);
}
