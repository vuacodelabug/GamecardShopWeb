package com.ganecardshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Gamecard;

@Repository
public interface GamecardRepository extends JpaRepository<Gamecard, Integer> {
    // Tìm kiếm theo nhà phát hành
    @Query("SELECT g FROM Gamecard g WHERE g.publisher.id = :publisherId ORDER BY g.price ASC")
    List<Gamecard> findByPublisherId(@Param("publisherId") Integer publisherId);

    // Lấy tất cả thẻ game
    @Query("SELECT g FROM Gamecard g " +
            "JOIN FETCH g.publisher p " +
            "WHERE p.status = 1" +
            "ORDER BY p.name ASC, g.name ASC")
    List<Gamecard> getAllGameCart();
}
