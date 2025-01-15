package com.ganecardshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "website_info")  // Tên bảng trong cơ sở dữ liệu
public class WebsiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // ID tự động tăng, làm khóa chính

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;  // Tên công ty

    @Column(name = "banner_image_url", length = 255)
    private String bannerImageUrl;  // URL của banner

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // Mô tả

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;  // Số điện thoại

    @Column(name = "email", length = 255)
    private String email;  // Email

    @Column(name = "address", length = 255)
    private String address;  // Địa chỉ

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Thời gian tạo

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Thời gian cập nhật

    // Constructor không tham số (cần thiết cho JPA)
    public WebsiteInfo() {}

    // Constructor với các tham số để dễ dàng tạo đối tượng
    public WebsiteInfo(String companyName, String bannerImageUrl, String description, String phoneNumber, String email, String address) {
        this.companyName = companyName;
        this.bannerImageUrl = bannerImageUrl;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    // Getters và setters cho các trường
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
