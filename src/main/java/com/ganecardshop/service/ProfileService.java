package com.ganecardshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.model.Profile;
import com.ganecardshop.model.User;
import com.ganecardshop.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // Lấy thông tin profile theo user
    public Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user);
    }

    // Lấy thông tin profile theo ID
    public Profile getProfileById(int id) {
        return profileRepository.findById(id).orElse(null);
    }

    // Cập nhật thông tin profile
    public Profile updateProfile(Profile profile) {
        return profileRepository.save(profile);
    }
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();  // Lấy tất cả các profile
    }

}
