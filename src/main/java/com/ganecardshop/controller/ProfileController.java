package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ganecardshop.model.Profile;
import com.ganecardshop.service.ProfileService;

@Controller
@RequestMapping("admin/profile")
public class ProfileController {


    @Autowired
    private ProfileService profileService;

    @GetMapping("")
    public String getList(Model model) {
        List<Profile> profiles = profileService.getAllProfiles();  // Giả sử bạn có phương thức getAllProfiles() trong ProfileService
        model.addAttribute("profiles", profiles);  // Thêm danh sách profile vào model
        return "admin/page/profile";  // Trả về trang profile.html
    }

    // API lấy thông tin profile
    @GetMapping("/api/detail/{id}")
    @ResponseBody
    public Profile getProfile(@PathVariable("id") int id) {
        return profileService.getProfileById(id);  // Trả về thông tin profile theo ID
    }

    // API cập nhật profile
    @PostMapping("/api/edit")
    @ResponseBody
    public Profile updateProfile(@RequestBody Profile request) {
        return profileService.updateProfile(request);  // Cập nhật thông tin profile
    }
}
