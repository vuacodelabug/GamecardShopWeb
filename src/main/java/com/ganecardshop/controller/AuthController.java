package com.ganecardshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ganecardshop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @RequestMapping("/login")
    public String login() {
        return "user/page/home";
    }

    @Autowired
    private UserService userService;

    // Xử lý đăng ký người dùng
    @PostMapping("/register")
    public String registerUserAccount(String user_email,
            String user_password,
            String user_confirmpassword,
            @RequestParam String profile_name,
            @RequestParam(required = false) String profile_phone,
            @RequestParam(required = false) String profile_gender,
            @RequestParam(required = false) String profile_dateOfBirth) {
        if (!user_password.equals(user_confirmpassword)) {
            throw new RuntimeException("Mật khẩu và xác nhận mật khẩu không khớp.");
        }
        java.sql.Date dob = null;
        if (profile_dateOfBirth != null && !profile_dateOfBirth.isEmpty()) {
            dob = java.sql.Date.valueOf(profile_dateOfBirth);
        } else {
            dob = java.sql.Date.valueOf("1900-01-01");
        }
        userService.registerUserAccount(user_email, user_password, profile_name, profile_phone, profile_gender, dob);

        return "redirect:/home?login=false"; // Sau khi đăng ký thành công, chuyển hướng tới trang đăng nhập
    }

    // hiện bảng thông tin cá nhân người dùng
    @RequestMapping("/profile-auth")
    public String getProfile(Model model, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("user_id");
        model.addAttribute("user", userService.findById(userId).get());
        return "user/page/profile-auth";
    }

}
