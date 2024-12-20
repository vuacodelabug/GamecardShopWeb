package com.ganecardshop.controller;

import java.io.IOException;

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

import com.ganecardshop.model.Publisher;
import com.ganecardshop.service.PublisherService;

@Controller
@RequestMapping("/admin/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "admin/page/publisher"; // trả về view danh sách nhà phát hành
    }

    @PostMapping("/create")
    public String createOrUpdatePublisher(
            @RequestParam("publisher_name") String name,
            @RequestParam(value = "publisher_description", required = false) String description,
            @RequestParam("publisher_image") String img, 
            RedirectAttributes redirectAttributes) throws IOException {
        Publisher publisher = new Publisher(name, img, description);
        publisherService.createPublisher(publisher);
         // Thêm thông báo vào RedirectAttributes
         boolean isSuccess = true;
         if (isSuccess) {
            redirectAttributes.addFlashAttribute("messager", "Nhà phát hành đã được thêm!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        }
        return "redirect:/admin/publisher";
    }

    @GetMapping("/api/detail/{id}")
    @ResponseBody
    public Publisher getPublisherById(@PathVariable Integer id) {
        return publisherService.getPublisherById(id); // Trả về đối tượng Publisher
    }

    
    @PostMapping("/api/update/{id}")
    @ResponseBody
    public ResponseEntity<Publisher> updatePublisher(
            @PathVariable Integer id,
            @RequestParam("publisher_name") String name,
            @RequestParam(value = "publisher_description", required = false) String description,
            @RequestParam("publisher_image") String img) throws IOException {
        Publisher existPublisher = publisherService.getPublisherById(id);
        if (existPublisher != null) {
            existPublisher.setName(name);
            existPublisher.setDescription(description);
            existPublisher.setImg(img);
            publisherService.updatePublisher(existPublisher);
            return ResponseEntity.ok(existPublisher); // Trả về đối tượng cập nhật
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String postDelete(@PathVariable Integer id) {
        publisherService.deletePublisher(id); // Đánh dấu nhà phát hành là đã xóa
        return "success";
    }
}