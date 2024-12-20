package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ganecardshop.dto.GamecardDTO;
import com.ganecardshop.service.DiscountService;
import com.ganecardshop.service.GamecardService;
import com.ganecardshop.service.PublisherService;

@Controller
@RequestMapping("/admin/game-card")
public class GamecardController {
    @Autowired
    private GamecardService gamecardService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public String getAllGamecards(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers()); // Chỉ lấy các publisher hợp lệ
        model.addAttribute("discounts", discountService.getAllStatusActive()); // Chỉ lấy các discount hợp lệ
        List<GamecardDTO> gamecards = gamecardService.getFilteredGamecards();
        model.addAttribute("gamecards", gamecards);
        return "admin/page/game-card";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createGameCard(@RequestBody GamecardDTO gamecardDto) {
        try {
            gamecardService.createGamecard(gamecardDto);
            return ResponseEntity.ok("Thêm thẻ game thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm thẻ game: " + e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<GamecardDTO> getGamecardById(@PathVariable Integer id) {
        GamecardDTO gamecard = gamecardService.getGamecardById(id);
        return ResponseEntity.ok(gamecard);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateGamecard(@RequestBody GamecardDTO gamecardDto) {
        try {
            gamecardService.updateGamecard(gamecardDto);
            return ResponseEntity.ok("Cập nhật thẻ game thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật thẻ game: " + e.getMessage());
        }
    }

      @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGameCard(@PathVariable Integer id) {
        gamecardService.deleteGameCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
