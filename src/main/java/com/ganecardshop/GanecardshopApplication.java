package com.ganecardshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.ganecardshop.service.DiscountService;

@SpringBootApplication
@EntityScan(basePackages = "com.ganecardshop.model") // Chỉ định package chứa các Entity

public class GanecardshopApplication implements ApplicationRunner{
	@Autowired
	private DiscountService discountService;

	public static void main(String[] args) {
		SpringApplication.run(GanecardshopApplication.class, args);
	}

	@Override
    public void run(ApplicationArguments args) throws Exception {
        // Gọi phương thức sau khi ứng dụng khởi động
		// Kiểm tra và cập nhật status theo thời gian
        discountService.autoUpdateStatus();
    }
}
