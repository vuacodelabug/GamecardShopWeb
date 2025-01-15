package com.ganecardshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ganecardshop.dto.DashboardStats;
import com.ganecardshop.repository.DashboardService;

@Controller
public class DashboardController {

    @Autowired
	DashboardService dashboardService;

    @GetMapping("admin/dashboard")
    public String getDashBoard(Model model) {
        // Get dashboard stats from the service
        DashboardStats dashboardStats = dashboardService.getDashboardStats();

        // Add the stats to the model
        model.addAttribute("customerCount", dashboardStats.getCustomerCount());
        model.addAttribute("totalRevenue", dashboardStats.getTotalRevenue());
        model.addAttribute("totalOrders", dashboardStats.getTotalOrders());
        model.addAttribute("totalUnusedGamecodes", dashboardStats.getTotalUnusedGamecodes());
        return "admin/page/dashboard";
    }
}

