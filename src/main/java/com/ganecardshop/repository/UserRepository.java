package com.ganecardshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ganecardshop.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    // Lấy User kèm theo Profile sử dụng JOIN FETCH
    @Query("SELECT u FROM User u JOIN FETCH u.profile WHERE u.id = :id")
    Optional<User> findByIdWithProfile(@Param("id") int id);

    @Query("SELECT u FROM User u WHERE u.status = 1")
    List<User> findAllActiveUsers();

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = 0 WHERE u.id = :id")
    void deactivateUser(@Param("id") int id);

    // Tìm kiếm người dùng theo tên trong profile
    List<User> findByProfileNameContaining(String profileName);

    // Tìm kiếm người dùng theo vai trò
    List<User> findByRole(String role);

    // Tìm kiếm người dùng theo tên trong profile và vai trò
    List<User> findByProfileNameContainingAndRole(String profileName, String role);
}