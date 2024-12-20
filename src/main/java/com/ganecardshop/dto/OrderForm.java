package com.ganecardshop.dto;

public class OrderForm {
    private Integer gamecardId;
    private String email;
    private Integer quantity;
    private Integer amount;
    private String paymentMethod;


    public OrderForm() {
    }

    public OrderForm(Integer gamecardId, String email, Integer quantity, Integer amount, String paymentMethod) {
        this.gamecardId = gamecardId;
        this.email = email;
        this.quantity = quantity;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and setters

    public Integer getGamecardId() {
        return gamecardId;
    }

    public void setGamecardId(Integer gamecardId) {
        this.gamecardId = gamecardId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderForm [amount=" + amount + ", email=" + email + ", gamecardId=" + gamecardId + ", paymentMethod="
                + paymentMethod + ", quantity=" + quantity + "]";
    }
}
