package com.ganecardshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ganecardshop.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    List<Publisher> findByStatus(Integer status); // Find publishers by status
    
}