package com.ganecardshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ganecardshop.dto.OrderDTO;
import com.ganecardshop.dto.OrderForm;
import com.ganecardshop.model.Discount;
import com.ganecardshop.model.Gamecard;
import com.ganecardshop.model.Gamecode;
import com.ganecardshop.model.Order;
import com.ganecardshop.model.Publisher;
import com.ganecardshop.model.User;
import com.ganecardshop.repository.GamecardRepository;
import com.ganecardshop.repository.GamecodeRepository;
import com.ganecardshop.repository.OrderRepository;
import com.ganecardshop.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GamecodeRepository gamecodeRepository; // Inject Gamecode repository

    @Autowired
    private GamecardRepository gamecardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // giao dịch thành công
    public List<Order> getSuccessOrders() {
        return orderRepository.findByStatus(1);
    }

    public OrderDTO convertToDto(Order order) {
        OrderDTO dto = new OrderDTO();

        // Set thông tin đơn hàng
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());

        // Set thông tin người dùng
        User user = order.getUser();
        if (user != null && user.getProfile() != null) {
            dto.setCustomerName(user.getProfile().getName());
        }

        // Lấy danh sách mã game (Gamecodes) và thêm vào DTO
        List<String> gameCodes = gamecodeRepository.findByOrderId(order.getId()).stream()
                .map(Gamecode::getCode)
                .collect(Collectors.toList());
        dto.setGameCodes(gameCodes);

        // Set thông tin thẻ game
        Gamecard gamecard = order.getGamecard();
        if (gamecard != null) {
            Publisher publisher = gamecard.getPublisher();
            dto.setGameCard(String.format("%s - %s",
                    publisher != null ? publisher.getName() : "Unknown Publisher",
                    gamecard.getName()));

            Discount discount = gamecard.getDiscount();
            String discountStr = "Không có giảm giá";
            int discountValue = 0;
            if (discount != null) {
                discountStr = discount.getName();
                if (discount.getType() == 1) { // Giảm giá theo phần trăm
                    discountValue = order.getGamecard().getPrice() * discount.getValue() / 100;
                } else if (discount.getType() == 2) { // Giảm giá trực tiếp (VND)
                    discountValue = discount.getValue();
                }
            }

            // Set các thuộc tính OrderDTO
            dto.setPublisher(publisher != null ? publisher.getName() : "Unknown Publisher");
            dto.setGameCardName(gamecard.getName());
            dto.setQuantity(order.getQuantity());
            dto.setDiscount(discountStr);
            dto.setPaymentMethod(order.getPaymentMethod());

            // Tính toán giá trị tổng cộng
            int finalPrice = gamecard.getPrice() - discountValue;
            dto.setTotalPrice(finalPrice * order.getQuantity());
        }

        // Set trạng thái
        dto.setStatus(order.getStatus());

        return dto;
    }

    public List<OrderDTO> convertToDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Phương thức tìm kiếm đơn hàng theo ID
    public Optional<Order> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public String processOrder(OrderForm orderForm, Integer userId, RedirectAttributes redirectAttributes)
            throws IllegalArgumentException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại."));

        Gamecard gamecard = gamecardRepository.findById(orderForm.getGamecardId())
                .orElseThrow(() -> new IllegalArgumentException("Thẻ game không tồn tại."));

        // Kiểm tra số lượng thẻ game trong kho
        int newStock = gamecard.getStock() - orderForm.getQuantity();

        if (newStock < 0) {
            // Nếu hết thẻ game, tạo đơn hàng với trạng thái "Spending" và mô tả "Hết thẻ
            // game"
            Order order = new Order();
            order.setUser(user); // Set user ID
            order.setEmail(orderForm.getEmail());
            order.setGamecard(gamecard);
            order.setQuantity(orderForm.getQuantity());
            order.setAmount(orderForm.getAmount());
            order.setPaymentMethod(orderForm.getPaymentMethod());
            order.setStatus(1); // Spending
            order.setDescription("Hết thẻ game"); // Mô tả "Hết thẻ game"
            order.setOrderDate(LocalDateTime.now());

            orderRepository.save(order);

            // Không cập nhật kho thẻ game, vì hết thẻ
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("messager", "Xảy ra lỗi mã game!");
            return "redirect:/home";
        }

        // Nếu đủ số lượng thẻ game
        Order order = new Order();
        order.setUser(user); // Set user ID
        order.setEmail(orderForm.getEmail());
        order.setGamecard(gamecard);
        order.setQuantity(orderForm.getQuantity());
        order.setAmount(orderForm.getAmount());
        order.setPaymentMethod(orderForm.getPaymentMethod());
        order.setStatus(1); // Pending
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        // Trong trường hợp đủ thẻ game, tìm và lấy ra số lượng thẻ game cần mua
        for (int i = 0; i < orderForm.getQuantity(); i++) {
            // Fetch an unused game code based on the gamecardId
            Gamecode gamecode = gamecodeRepository.findFirstByGamecardIdAndIsUsedFalse(gamecard.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Lỗi mã thẻ game!"));
            gamecode.markAsUsed();
            gamecodeRepository.save(gamecode);
        }

        // Cập nhật số lượng thẻ game trong kho
        System.out.println("New stock: " + newStock);
        gamecard.setStock(newStock);
        gamecardRepository.save(gamecard);
        // đơn hàng thành công
        order.setStatus(2);
        orderRepository.save(order);
        

        return "redirect:/home";
    }

}
