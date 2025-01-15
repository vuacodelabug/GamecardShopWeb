package com.ganecardshop.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        // lấy mã game chưa sử dụng đầu tiên theo gamecard
        Optional<Gamecode> findFirstByGamecardIdAndIsUsedFalse(Integer gamecardId);

        // tìm kiếm mã game
        @Query("SELECT g FROM Gamecode g " +
                        "WHERE (:search_gamecode IS NULL OR g.code LIKE %:search_gamecode%) " +
                        "AND (:search_publisher IS NULL OR g.gamecard.publisher.id = :search_publisher) " +
                        "AND (:search_gamecard IS NULL OR g.gamecard.id = :search_gamecard) " +
                        "AND (:search_isUsed IS NULL OR g.isUsed = :search_isUsed)")
        Page<Gamecode> findFilteredGamecodes(@Param("search_gamecode") String search_gamecode,
                        @Param("search_publisher") Integer search_publisher,
                        @Param("search_gamecard") Integer search_gamecard,
                        @Param("search_isUsed") Integer search_isUsed,
                        Pageable pageable);

        // Đếm số lượng mã thẻ chưa sử dụng
    @Query("SELECT COUNT(g) FROM Gamecode g WHERE g.isUsed = 0")
    Integer totalUnusedGamecodes();
}
