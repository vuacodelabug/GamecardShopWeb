package com.ganecardshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.model.Publisher;
import com.ganecardshop.repository.PublisherRepository;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findByStatus(1); // Lọc các nhà phát hành còn hoạt động
    }

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher); // Tạo mới nhà phát hành
    }

    public Publisher updatePublisher(Publisher publisher) {
        return publisherRepository.save(publisher); // Cập nhật nhà phát hành
    }

    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found"));
        publisher.setStatus(0); // Đánh dấu là đã xóa
        publisherRepository.save(publisher); // Lưu lại trạng thái xóa
    }
    
    public Publisher getPublisherById(Integer id) {
        return publisherRepository.findById(id).orElse(null); // Lấy thông tin nhà phát hành theo ID
    }
}
