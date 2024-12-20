package com.ganecardshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Assuming there is a User entity

    // email nhận thẻ
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "gamecard_id", nullable = false)
    private Gamecard gameCard;  // Assuming there is a Gamecard entity

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "status", nullable = false)
    private Integer status;  // 1 = pending, 2 = completed, 3 = cancelled

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    // cobnstructor
    public Order() {
    }

    public Order(User user, Gamecard gameCard, Integer quantity, Integer amount, String paymentMethod, Integer status, LocalDateTime orderDate) {
        this.user = user;
        this.gameCard = gameCard;
        this.quantity = quantity;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderDate = orderDate;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gamecard getGamecard() {
        return gameCard;
    }

    public void setGamecard(Gamecard gameCard) {
        this.gameCard = gameCard;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
