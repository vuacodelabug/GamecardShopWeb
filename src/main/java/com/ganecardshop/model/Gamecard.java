package com.ganecardshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gamecard")
public class Gamecard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIgnore // Tránh việc ánh xạ ngược lại từ Publisher về Gamecard
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    private Integer stock = 0; // Mặc định số lượng là 0

    // construc
    public Gamecard() {
    }

    public Gamecard(Publisher publisher, Discount discount, String name, Integer price) {
        this.publisher = publisher;
        this.discount = discount;
        this.name = name;
        this.price = price;
        this.stock = 0; // Đặt số lượng mặc định là 0
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
