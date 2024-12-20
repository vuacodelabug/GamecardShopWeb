package com.ganecardshop.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ganecardshop.model.Discount;
import com.ganecardshop.service.DiscountService;

@Controller
@RequestMapping("/admin/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public String getAllDiscounts(Model model) {
        List<Discount> discounts = discountService.getAllStatusActive();

        // Iterate through the list of discounts and format the dates
        for (Discount discount : discounts) {
            discountService.formatDates(discount);
        }

        model.addAttribute("discounts", discounts);
        return "admin/page/discount";
    }

    @PostMapping("/create")
    public String createDiscount(
            @RequestParam("discountName") String discountName,
            @RequestParam(value = "discountDescription", required = false) String discountDescription,
            @RequestParam("discountValue") int discountValue,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("discountType") int discountType,
            RedirectAttributes redirectAttributes) {

        // Chuyển đổi `startDate` và `endDate` từ String sang LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);

        // Tạo đối tượng Discount
        Discount discount = new Discount();
        discount.setName(discountName);
        discount.setDescription(discountDescription);
        discount.setValue(discountValue);
        discount.setStartday(startDateTime);
        discount.setEndday(endDateTime);
        discount.setType(discountType);
        discount.setStatus(1); // Trạng thái mặc định là "Đang áp dụng"

        // Lưu đối tượng Discount vào cơ sở dữ liệu
        discountService.saveDiscount(discount);

        // Thêm thông báo vào RedirectAttributes
        Boolean isSuccess = true;
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("messager", "Mã giảm giá đã được thêm!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("messager", "Đã xảy ra lỗi! Vui lòng thử lại.");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        // Quay lại trang danh sách giảm giá
        return "redirect:/admin/discount";
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public Discount getDiscountById(@PathVariable("id") Integer id) {
        Discount discount = discountService.getDiscountById(id);
        discountService.autoUpdateStatus();
        return discount; // Fetch discount from the service
    }

    @PostMapping("/update")
    @ResponseBody
    public Discount updateDiscount(@RequestParam Integer discountId,
            @RequestParam String discountName,
            @RequestParam String discountDescription,
            @RequestParam int discountValue,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int discountType,
            @RequestParam int discountStatus) {
        return discountService.updateDiscount(discountId, discountName, discountDescription, discountValue, startDate,
                endDate, discountType, discountStatus);
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteDiscount(@PathVariable("id") Integer id) {
        discountService.deleteDiscount(id);
        return new ResponseEntity<>("Discount has been deleted!", HttpStatus.OK);
    }

}
