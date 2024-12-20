package com.ganecardshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ganecardshop.model.Gamecard;

public interface GamecardRepository extends JpaRepository<Gamecard, Integer> {
    public List<Gamecard> findByPublisherId(Integer publisherId);

    @Query("SELECT g FROM Gamecard g " +
            "JOIN FETCH g.publisher p " +
            "WHERE p.status = 1" +
            "ORDER BY p.name ASC, g.name ASC")
    List<Gamecard> getAllGameCart();
}
