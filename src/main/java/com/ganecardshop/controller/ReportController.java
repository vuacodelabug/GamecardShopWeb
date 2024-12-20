package com.ganecardshop.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ganecardshop.dto.InventoryItemDTO;
import com.ganecardshop.service.OrderService;
import com.ganecardshop.service.ReportService;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReportService inventoryService;

    @GetMapping("/inventorys")
    @ResponseBody
    public ResponseEntity<List<InventoryItemDTO>> getInventoryReport(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reportDate) {

        // Nếu không có ngày trong request, dùng ngày hiện tại
        if (reportDate == null) {
            reportDate = LocalDateTime.now();
        }

        // Xác định phạm vi ngày trong tháng
        LocalDate startOfMonth = reportDate.toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = reportDate.toLocalDate().with(TemporalAdjusters.lastDayOfMonth());

        // Lấy báo cáo
        List<InventoryItemDTO> report = inventoryService.getInventoryReportByMonth(startOfMonth, endOfMonth);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/inventory")
    public String getInventoryReport(Model model,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reportDate) {

        // Nếu không có ngày trong request, dùng ngày hiện tại
        if (reportDate == null) {
            reportDate = LocalDateTime.now();
        }

        // Xác định phạm vi ngày trong tháng
        LocalDate startOfMonth = reportDate.toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = reportDate.toLocalDate().with(TemporalAdjusters.lastDayOfMonth());

        // Lấy báo cáo kho hàng trong tháng
        List<InventoryItemDTO> report = inventoryService.getInventoryReportByMonth(startOfMonth, endOfMonth);

        // Truyền báo cáo và ngày vào mô hình để hiển thị trong view
        model.addAttribute("inventorys", report); // Truyền báo cáo kho
        String formattedDate = reportDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        model.addAttribute("reportDate", formattedDate); // Truyền ngày đã được format vào model
        System.out.println("reportDate: " + reportDate);

        return "admin/page/report-inventory"; // Trả về view báo cáo kho hàng
    }

    // Báo cáo doanh thu
    @GetMapping("/sale")
    public String getSaleReport(Model model) {
        return "admin/page/report-sale"; // Trả về view báo cáo doanh thu
    }


}
