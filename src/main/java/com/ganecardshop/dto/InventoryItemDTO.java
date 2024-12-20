package com.ganecardshop.dto;

// Báo cáo tồn kho theo ngày
public class InventoryItemDTO {
    private String gameCardName;
    private String publisher;
    private int value; // Giá trị thẻ game
    private int quantity; // Số lượng còn lại

    // Constructor mặc định
    public InventoryItemDTO() {
    }

    // Constructor đầy đủ
    public InventoryItemDTO(String gameCardName, String publisher, int value, int quantity) {
        this.gameCardName = gameCardName;
        this.publisher = publisher;
        this.value = value;
        this.quantity = quantity;
    }

    // Getters và Setters
    public String getGameCardName() {
        return gameCardName;
    }

    public void setGameCardName(String gameCardName) {
        this.gameCardName = gameCardName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InventoryItemDTO{" +
                "gameCardName='" + gameCardName + '\'' +
                ", publisher='" + publisher + '\'' +
                ", value=" + value +
                ", quantity=" + quantity +
                '}';
    }
}
