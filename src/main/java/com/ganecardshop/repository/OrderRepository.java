package com.ganecardshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
     // tìm kiếm đơn hàng theo trạng thái
     List<Order> findByStatus(Integer status);

     // tìm kiếm số lượng đơn hàng theo ngày
     @Query("SELECT SUM(o.quantity) FROM Order o WHERE o.gameCard.id = :gameCardId AND o.status = 2 AND DATE(o.orderDate) = DATE(:reportDate)")
     Integer findTotalQuantityByGamecardAndDate(@Param("gameCardId") Integer gameCardId, @Param("reportDate") LocalDateTime reportDate);
 
     // tìm kiếm số lượng đơn hàng theo ngày
     @Query("SELECT COALESCE(SUM(o.quantity), 0) FROM Order o WHERE o.gameCard.id = :gameCardId AND o.status = 2 AND o.orderDate BETWEEN :startDate AND :endDate")
     int findTotalQuantityByGamecardAndDateRange(
             @Param("gameCardId") Integer gameCardId,
             @Param("startDate") LocalDateTime startDate,
             @Param("endDate") LocalDateTime endDate);
}