package com.ganecardshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ganecardshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // tìm kiếm đơn hàng theo trạng thái
    List<Order> findByStatus(Integer status);

    // tìm kiếm số lượng đơn hàng theo ngày
    @Query("SELECT SUM(o.quantity) FROM Order o WHERE o.gameCard.id = :gamecardId AND o.status = 2 AND DATE(o.orderDate) = DATE(:reportDate)")
    Integer findTotalQuantityByGamecardAndDate(@Param("gameCardId") Integer gamecardId,
            @Param("reportDate") LocalDateTime reportDate);

    // tìm kiếm số lượng đơn hàng theo ngày
    @Query("SELECT COALESCE(SUM(o.quantity), 0) FROM Order o WHERE o.gameCard.id = :gamecardId AND o.status = 2 AND o.orderDate BETWEEN :startDate AND :endDate")
    int findTotalQuantityByGamecardAndDateRange(
            @Param("gamecardId") Integer gamecardId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}