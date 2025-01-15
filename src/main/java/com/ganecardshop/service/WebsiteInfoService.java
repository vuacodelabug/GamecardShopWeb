package com.ganecardshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.model.WebsiteInfo;
import com.ganecardshop.repository.WebsiteInfoRepository;

@Service
public class WebsiteInfoService {

    @Autowired
    private WebsiteInfoRepository websiteInfoRepository;

    public WebsiteInfo getWebsiteInfo() {
        return websiteInfoRepository.findById(1).orElse(null);
    }
}

