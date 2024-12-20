package com.ganecardshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("admin/dashboard")
	public String getDashBoard() {
		return "admin/page/dashboard";
	}
	
}
