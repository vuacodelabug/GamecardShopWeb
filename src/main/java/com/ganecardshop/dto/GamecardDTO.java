package com.ganecardshop.dto;

import java.text.NumberFormat;
import java.util.Locale;

public class GamecardDTO {
    private Integer id;
    private String publisherName;
    private String cardName;
    private Integer price;
    private String formattedPrice;
    private String discountName;
    private Integer stock;
    private Integer publisherId;
    private Integer discountId; // Có thể null

    // Constructor
    public GamecardDTO(Integer id, String publisherName, Integer publisherId, String cardName, Integer price,
            String discountName, Integer discountId,
            Integer stock) {
        this.id = id;
        this.publisherName = publisherName;
        this.cardName = cardName;
        this.price = price;
        this.formattedPrice = formatCurrency(price);
        this.discountName = discountName != null ? discountName : "Chưa có khuyến mãi";
        this.stock = stock;
        this.publisherId = publisherId;
        this.discountId = discountId != null ? discountId : 0;
    }

   private String formatCurrency(Integer value) {
        // Use Locale.Builder to create a locale for Vietnam
        Locale locale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat formatter = NumberFormat.getInstance(locale);
        return formatter.format(value) + " đ";
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getCardName() {
        return cardName;
    }

    public Integer getPrice() {
        return price;
    }
    public String getFormattedPrice() {
        return formattedPrice;
    }

    public String getDiscountName() {
        return discountName;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getStock() {
        return stock;
    }

    

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setPrice(Integer price) {
        this.formattedPrice = formatCurrency(price);
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
