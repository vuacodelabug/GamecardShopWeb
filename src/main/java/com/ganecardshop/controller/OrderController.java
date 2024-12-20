package com.ganecardshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ganecardshop.dto.OrderDTO;
import com.ganecardshop.model.Order;
import com.ganecardshop.service.OrderService;

@Controller
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders(); // Lấy tất cả các Order từ cơ sở dữ liệu
        List<OrderDTO> orderDtos = orderService.convertToDtoList(orders);

        model.addAttribute("orders", orderDtos); // Đưa danh sách vào Model để truyền sang view
        return "admin/page/order";
    }
     // Endpoint trả về chi tiết đơn hàng theo ID
    @GetMapping("/detail/{id}")
    @ResponseBody
    public OrderDTO getOrderDetails(@PathVariable Integer id) {
        Optional<Order> order = orderService.findOrderById(id);
        
        if (order.isPresent()) {
            return orderService.convertToDto(order.get());
        } else {
            throw new RuntimeException("Order not found with id " + id);
        }
    }
    
    @GetMapping("/success")
    public String listOrderss(Model model) {
        List<Order> orders = orderService.getSuccessOrders(); // Lấy tất cả các Order từ cơ sở dữ liệu
        List<OrderDTO> orderDtos = orderService.convertToDtoList(orders);

        model.addAttribute("transactions", orderDtos); // Đưa danh sách vào Model để truyền sang view
        return "admin/page/transaction";
    }
}