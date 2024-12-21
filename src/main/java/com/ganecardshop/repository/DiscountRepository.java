package com.ganecardshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Discount findByStatus(Integer status); // Find discounts by status
    Discount findById(int id); // Find discounts by ID
    // lấy ra discount status !=0
    List<Discount> findByStatusNot(int status);
    // lấy ra discount findByStatusNotIn
    List<Discount> findByStatusNotIn(List<Integer> status);
}
