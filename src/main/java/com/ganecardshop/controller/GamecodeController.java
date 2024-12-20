package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ganecardshop.dto.GamecodeDTO;
import com.ganecardshop.model.Gamecard;
import com.ganecardshop.service.GamecardService;
import com.ganecardshop.service.GamecodeService;
import com.ganecardshop.service.PublisherService;

@Controller
@RequestMapping("admin/game-code") // Mapping URL
public class GamecodeController {

    @Autowired
    private GamecodeService gamecodeService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private GamecardService gamecardService;

    @GetMapping("") // Mapping URL
    public String getGamecodesList(Model model) {
        model.addAttribute("gamecodes", gamecodeService.getFilteredGamecode());
        model.addAttribute("gamecodeDTO", new GamecodeDTO()); // Thêm đối tượng DTO trống vào model
        model.addAttribute("publishers", publisherService.getAllPublishers()); // Tải danh sách các nhà phát hành
        // model.addAttribute("gamecards", gamecardRepository.getAllGameCart()); // Tải
        // danh sách các thẻ game
        return "admin/page/game-code";
    }

    // API để lấy thẻ game theo nhà phát hành
    @GetMapping("/get-publisher/{publisherId}")
    @ResponseBody
    public List<Gamecard> getGamecardsByPublisher(@PathVariable Integer publisherId) {
        return gamecardService.getGamecardsByPublisher(publisherId); // Trả về danh sách thẻ game theo publisher
    }

    @PostMapping("/create")
    public String createGamecode(@ModelAttribute("gamecodeDTO") GamecodeDTO gamecodeDTO,
            RedirectAttributes redirectAttributes) {
        boolean isSuccess = true; // Khởi tạo giá trị mặc định là true
        List<String> duplicateCodes = null; // Biến lưu danh sách các mã thẻ trùng
    
        try {
            // Gọi service để tạo gamecode và kiểm tra mã thẻ trùng
            duplicateCodes = gamecodeService.createGamecode(gamecodeDTO);
    
            // Kiểm tra nếu có mã thẻ trùng
            if (duplicateCodes != null && !duplicateCodes.isEmpty()) {
                String duplicateMessage = "Mã thẻ " + String.join(", ", duplicateCodes ) + " đã tồn tại";
                redirectAttributes.addFlashAttribute("errorMessage", duplicateMessage);
                isSuccess = false; // Đánh dấu là thất bại nếu có mã thẻ trùng
            }
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình tạo mã thẻ game
            isSuccess = false;
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    
        // Thêm thông báo vào RedirectAttributes dựa trên kết quả
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("messager", "Mã thẻ game đã được thêm!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("messager", "Đã xảy ra lỗi! Vui lòng thử lại.");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
    
        return "redirect:/admin/game-code"; // Redirect sau khi xử lý
    }
    

    // lấy thông tin mã thẻ game theo ID
    @GetMapping("/detail/{id}")
    @ResponseBody
    public GamecodeDTO getGamecodeById(@PathVariable Integer id) {
        return gamecodeService.getGamecodeById(id); // Trả về thông tin mã thẻ game theo ID
    }

    @PostMapping("/update")
    public String updateGamecode(@ModelAttribute("gamecodeDTO") GamecodeDTO gamecodeDTO,
                                  RedirectAttributes redirectAttributes) {
        try {
            gamecodeService.updateGamecode(gamecodeDTO);
            redirectAttributes.addFlashAttribute("messager", "Mã thẻ game đã được cập nhật!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());

            redirectAttributes.addFlashAttribute("messager", "Đã xảy ra lỗi! Vui lòng thử lại.");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }
        return "redirect:/admin/game-code"; // Redirect sau khi xử lý
    }
    

    // Xử lý xóa mã thẻ game
    @PostMapping("/delete/{id}")
    @ResponseBody
    public void deleteGamecode(@PathVariable Integer id) {
        gamecodeService.deleteGamecode(id);
    }
}
