package com.ganecardshop.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Chỉ định bảng tương ứng trong cơ sở dữ liệu
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String role;
    private int status = 1; // 1 = Active, 0 = Inactive



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setUser(this); // Đảm bảo liên kết 2 chiều
    }
    
    // Constructor mặc định
    public User() {}

    public User(String email, String password, boolean isPassword) {
        this.email = email;
        this.password = password;
        this.role = "user"; // Mặc định là user
    }

    // Constructor khi không có mật khẩu (mật khẩu mặc định)
    public User(String email, String role) {
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode("123!@#"); // Mã hóa mật khẩu mặc định
        this.role = role;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Triển khai phương thức getUsername() từ UserDetails
    @Override
    public String getUsername() {
        return email;
    }

    // Triển khai phương thức getAuthorities() từ UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    // Triển khai các phương thức còn lại từ UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true; // Giả sử tài khoản không hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Giả sử tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Giả sử thông tin đăng nhập không hết hạn
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1; // Chỉ cho phép đăng nhập nếu status = 1
    }
    
}
