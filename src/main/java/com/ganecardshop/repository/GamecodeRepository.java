package com.ganecardshop.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Gamecode;

@Repository
public interface GamecodeRepository extends JpaRepository<Gamecode, Integer> {
        List<Gamecode> findByIsUsed(Integer isUsed);

        Optional<Gamecode> findByCode(String code);

        // tìm kiếm mã game theo order
        List<Gamecode> findByOrderId(Integer orderId);

        // tìm kiếm mã đã được sử dụng theo gamecard và ngày
        @Query("SELECT COUNT(gc) FROM Gamecode gc WHERE gc.gamecard.id = :gamecardId AND gc.isUsed = 1 AND DATE(gc.usedDate) = DATE(:reportDate)")
        int findUsedCountByGamecardAndDate(@Param("gamecardId") Integer gamecardId,
                        @Param("reportDate") LocalDateTime reportDate);

        // tìm kiếm mã đã được sử dụng theo gamecard và khoảng thời gian
        @Query("SELECT COALESCE(COUNT(gc), 0) FROM Gamecode gc WHERE gc.gamecard.id = :gamecardId AND gc.isUsed = 1 AND gc.usedDate BETWEEN :startDate AND :endDate")
        int findUsedCountByGamecardAndDateRange(
                        @Param("gamecardId") Integer gamecardId,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        List<Gamecode> findByGamecardId(Integer gamecardId);
        // findFirstByGamecardIdAndIsUsedFalse
        Optional<Gamecode> findFirstByGamecardIdAndIsUsedFalse(Integer gamecardId);
        
}
