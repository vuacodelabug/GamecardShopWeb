package com.ganecardshop.dto;

public class DashboardStats {

    private Integer customerCount;
    private Integer totalRevenue;
    private Integer totalOrders;
    private Integer totalUnusedGamecodes;

    // Constructor
    public DashboardStats(Integer customerCount, Integer totalRevenue, Integer totalOrders, Integer totalUnusedGamecodes) {
        this.customerCount = customerCount;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalUnusedGamecodes = totalUnusedGamecodes;
    }

    // Getters and Setters
    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public Integer getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Integer totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getTotalUnusedGamecodes() {
        return totalUnusedGamecodes;
    }

    public void setTotalUnusedGamecodes(Integer totalUnusedGamecodes) {
        this.totalUnusedGamecodes = totalUnusedGamecodes;
    }
}
