package com.ganecardshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ganecardshop.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to) {
        System.out.println("EmailController");

        emailService.sendSimpleEmail(to, "Chào bạn!!!", "This is a test email.");
        return "Email sent successfully!";
    }
}
