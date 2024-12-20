package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ganecardshop.dto.OrderForm;
import com.ganecardshop.model.Publisher;
import com.ganecardshop.repository.UserRepository;
import com.ganecardshop.service.GamecardService;
import com.ganecardshop.service.OrderService;
import com.ganecardshop.service.PublisherService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@Autowired
	private PublisherService publisherService;

	@Autowired
	GamecardService gamecardService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderService orderService;

	@GetMapping("/home")
	public String getHome(Model model) {
		return "user/page/home";
	}

	// giới thiệu
	@GetMapping("/about")
	public String getAbout(Model model) {
		return "user/page/about";
	}

	// hướng dẫn
	@GetMapping("/guide")
	public String getGuide(Model model) {
		return "user/page/guide";
	}

	// điều khoản
	@GetMapping("/policy")
	public String getTerms(Model model) {
		return "user/page/policy";
	}

	// Lấy danh sách publisher
	@GetMapping("/api/get-listpublisher")
	@ResponseBody
	public List<Publisher> getListPublisher() {
		return publisherService.getAllPublishers();
	}

	// Lấy danh sách thẻ game theo publisher
	@GetMapping("/api/get-gamecardby-publisher/{id}")
	@ResponseBody
	public String getGamecardByPublisher(@PathVariable Integer id) {
		return gamecardService.getGamecardsByPublisher(id).toString();
	}

	// Thực hiện mua hàng
	@PostMapping("/payment")
	public String buyGamecard(@ModelAttribute OrderForm orderForm, RedirectAttributes reA,
			HttpServletRequest request) {
		try {
			Integer userId = (Integer) request.getSession().getAttribute("user_id");
			orderService.processOrder(orderForm, userId, reA);

			// Thông báo thành công
			reA.addFlashAttribute("isSuccess", true);
			reA.addFlashAttribute("messager", "Đã mua thẻ game thành công! Vui lòng kiểm tra email để nhận mã thẻ.");
			return "redirect:/home";
		} catch (IllegalArgumentException e) {
			// Thông báo lỗi cụ thể (ví dụ: hết thẻ game, số lượng không đủ, ... )
			reA.addFlashAttribute("isSuccess", false);
			reA.addFlashAttribute("messager", e.getMessage());
			return "redirect:/home";
		} catch (Exception e) {
			// Thông báo lỗi tổng quát khi có vấn đề ngoài dự tính
			reA.addFlashAttribute("isSuccess", false);
			reA.addFlashAttribute("messager", "Đã xảy ra lỗi trong quá trình thực hiện.");
			return "redirect:/home";
		}
	}

}