package com.ganecardshop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.dto.InventoryItemDTO;
import com.ganecardshop.model.Gamecard;
import com.ganecardshop.repository.GamecardRepository;
import com.ganecardshop.repository.GamecodeRepository;
import com.ganecardshop.repository.OrderRepository;

@Service
public class ReportService {

    @Autowired
    private GamecardRepository gamecardRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GamecodeRepository gamecodeRepository;

    public List<InventoryItemDTO> getInventoryReportByMonth(LocalDate startOfMonth, LocalDate endOfMonth) {
        List<Gamecard> gamecards = gamecardRepository.findAll();
    
        return gamecards.stream().map(gamecard -> {
            InventoryItemDTO dto = new InventoryItemDTO();
            dto.setGameCardName(gamecard.getName());
            dto.setPublisher(gamecard.getPublisher().getName());
            dto.setValue(gamecard.getPrice());
    
            int initialStock = gamecard.getStock();
            
            // Tổng số lượng đã bán trong tháng
            int soldQuantity = orderRepository.findTotalQuantityByGamecardAndDateRange(
                gamecard.getId(), 
                startOfMonth.atStartOfDay(), 
                endOfMonth.atTime(23, 59, 59)
            );
    
            // Tổng số mã đã sử dụng trong tháng
            int usedQuantity = gamecodeRepository.findUsedCountByGamecardAndDateRange(
                gamecard.getId(), 
                startOfMonth.atStartOfDay(), 
                endOfMonth.atTime(23, 59, 59)
            );
    
            dto.setQuantity(initialStock - soldQuantity - usedQuantity);
            return dto;
        }).collect(Collectors.toList());
    }

}
