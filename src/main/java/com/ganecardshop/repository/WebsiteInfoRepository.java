package com.ganecardshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.WebsiteInfo;

@Repository
public interface WebsiteInfoRepository extends JpaRepository<WebsiteInfo, Integer> {
    // Có thể định nghĩa các phương thức tìm kiếm tùy chỉnh nếu cần
}
