package com.ganecardshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.dto.DashboardStats;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GamecodeRepository gamecodeRepository;

    public DashboardStats getDashboardStats() {
        Integer customerCount = userRepository.countActiveUsers();
        Integer totalRevenue = orderRepository.totalRevenue();
        Integer totalOrders = orderRepository.totalOrders();
        Integer totalUnusedGamecodes = gamecodeRepository.totalUnusedGamecodes();

        return new DashboardStats(customerCount, totalRevenue, totalOrders, totalUnusedGamecodes);
    }
}
