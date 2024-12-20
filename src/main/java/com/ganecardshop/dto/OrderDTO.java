package com.ganecardshop.dto;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OrderDTO {

    private Integer id;
    private String customerName;
    private String gameCard;
    private Integer totalPrice;
    private String formattedTotalPrice;
    private String orderDate;
    private String status;
    private List<String> gameCodes;

    // Các trường OrderDetailDto di chuyển vào OrderDTO
    private String publisher;
    private String gameCardName;
    private int quantity;
    private String discount;
    private String paymentMethod;

    // Constructor
    public OrderDTO() {
    }

    public OrderDTO(Integer id, String customerName, String gameCard, Integer totalPrice, LocalDateTime orderDate,
                    int status, List<String> gameCodes, String publisher, String gameCardName, int quantity,
                    String discount, String paymentMethod) {
        this.id = id;
        this.customerName = customerName;
        this.gameCard = gameCard;
        this.totalPrice = totalPrice;
        this.formattedTotalPrice = formatCurrency(totalPrice);
        this.orderDate = formatDate(orderDate);
        this.status = formatStatus(status);
        this.gameCodes = gameCodes;
        this.publisher = publisher;
        this.gameCardName = gameCardName;
        this.quantity = quantity;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
    }

    private String formatCurrency(Integer value) {
        // Sử dụng Locale cho Việt Nam
        Locale locale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat formatter = NumberFormat.getInstance(locale);
        return formatter.format(value) + "đ";
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        return date.format(formatter);
    }

    private String formatStatus(int status) {
        return switch (status) {
            case 1 -> "Đang thực hiện";
            case 2 -> "Thành công";
            case 3 -> "Đã hủy";
            default -> "Không xác định";
        };
    }

    // Getters và setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGameCard() {
        return gameCard;
    }

    public void setGameCard(String gameCard) {
        this.gameCard = gameCard;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        this.formattedTotalPrice = formatCurrency(totalPrice);
    }

    public String getFormattedTotalPrice() {
        return formattedTotalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = formatDate(orderDate);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = formatStatus(status);
    }

    public List<String> getGameCodes() {
        return gameCodes;
    }

    public void setGameCodes(List<String> gameCodes) {
        this.gameCodes = gameCodes;
    }

    // Getter và setter cho các trường OrderDetailDto
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGameCardName() {
        return gameCardName;
    }

    public void setGameCardName(String gameCardName) {
        this.gameCardName = gameCardName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
