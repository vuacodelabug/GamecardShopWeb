package com.ganecardshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Profile;
import com.ganecardshop.model.User;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUserId(int userId);
    Profile findByUser(User user); // Tìm profile theo user

}
