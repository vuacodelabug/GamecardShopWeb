package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public String getGamecodesList(Model model,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "search_gamecode", required = false) String search_gamecode,
        @RequestParam(value = "search_publisher", required = false) Integer search_publisher,
        @RequestParam(value = "search_gamecard", required = false) Integer search_gamecard,
        @RequestParam(value = "search_isUsed", required = false) Integer search_isUsed) {

    Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable

    // Lọc Gamecode theo các trường tìm kiếm
  Page<GamecodeDTO> gamecodesPage = gamecodeService.findFilteredGamecodes(search_gamecode, search_publisher,
                                                                          search_gamecard, search_isUsed, pageable);

    model.addAttribute("gamecodes", gamecodesPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", gamecodesPage.getTotalPages());
    model.addAttribute("totalItems", gamecodesPage.getTotalElements());

    model.addAttribute("gamecodeDTO", new GamecodeDTO()); // Thêm đối tượng DTO trống vào model
    model.addAttribute("publishers", publisherService.getAllPublishers()); // Tải danh sách các nhà phát hành
    model.addAttribute("gamecards", gamecardService.getFilteredGamecards(search_publisher)); // Tải danh sách các thẻ game

    model.addAttribute("search_gamecode", search_gamecode);
    model.addAttribute("search_publisher", search_publisher);
    model.addAttribute("search_gamecard", search_gamecard);
    model.addAttribute("search_isUsed", search_isUsed);
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
                String duplicateMessage = "Mã thẻ " + String.join(", ", duplicateCodes) + " đã tồn tại";
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
