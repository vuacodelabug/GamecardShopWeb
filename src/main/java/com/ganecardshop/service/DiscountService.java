package com.ganecardshop.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.model.Discount;
import com.ganecardshop.repository.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public List<Discount> getAllStatusActive() {
        return discountRepository.findByStatusNot(0);
    }

    public Discount getDiscountById(int id) {
        return discountRepository.findById(id);
    }

    public Discount saveDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public void formatDates(Discount discount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Iterate through the list of discounts and format the dates
        if (discount.getStartday() == null || discount.getEndday() == null) {
            return;
        }
        String formattedStartDay = discount.getStartday().format(formatter);
        String formattedEndDay = discount.getEndday().format(formatter);

        // Add formatted dates to the discount object
        discount.setFormattedStartDay(formattedStartDay);
        discount.setFormattedEndDay(formattedEndDay);
    }

    @Transactional
    public Discount updateDiscount(Integer id, String name, String description, Integer value, String startDate,
            String endDate, Integer type, Integer status) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isPresent()) {
            Discount discount = discountOptional.get();

            // Update discount information
            discount.setName(name);
            discount.setDescription(description);
            discount.setValue(value);
            discount.setStartday(LocalDateTime.parse(startDate)); // Adjust the date-time format accordingly
            discount.setEndday(LocalDateTime.parse(endDate)); // Adjust the date-time format accordingly
            discount.setType(type);
            discount.setStatus(status);
            discountRepository.save(discount);
            this.formatDates(discount); // Format the dates
            // Save the updated discount
            return discount;
        }
        return null;
    }

    // set status = 0
    public void deleteDiscount(int id) {
        Discount discount = discountRepository.findById(id);
        discount.setStatus(0);
        discountRepository.save(discount);
    }

    public void autoUpdateStatus() {
        List<Discount> discounts = discountRepository.findByStatusNotIn(List.of(3, 0));

        LocalDateTime now = LocalDateTime.now();

        for (Discount discount : discounts) {
            // Kiểm tra và cập nhật status theo thời gian
            if (discount.getStartday().isBefore(now) || discount.getStartday().isEqual(now)) {
                discount.setStatus(1); // Nếu startDate <= thời gian hiện tại, status = 1 (Đang áp dụng)
            }
            if (discount.getEndday().isBefore(now) || discount.getEndday().isEqual(now)) {
                discount.setStatus(2); // Nếu endDate <= thời gian hiện tại, status = 2 (Đã hết hạn)
            }
            discountRepository.save(discount); // Lưu bản ghi đã cập nhật
        }
    }
}
