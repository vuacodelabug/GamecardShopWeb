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

@Entity
@Table(name = "gamecode")
public class Gamecode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gamecard_id", nullable = false)
    private Gamecard gamecard; // Many-to-one relation with Gamecard

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // nếu có order thì sẽ lưu order_id, nếu không có order thì sẽ lưu 0

    private String code;
    private Integer isUsed = 0; // 0 = not used, 1 = used

    @Column(name = "useddate")
    private LocalDateTime usedDate;

    public Gamecode() {
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gamecard getGamecard() {
        return gamecard;
    }

    public void setGamecard(Gamecard gamecard) {
        this.gamecard = gamecard;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order == null ? null : order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = 0; // Default value is 0
    }

    public LocalDateTime getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDateTime usedDate) {
        this.usedDate = usedDate;
    }

    // Phương thức để cập nhật trạng thái khi gamecode được sử dụng
    public void markAsUsed() {
        this.isUsed = 1;
        this.usedDate = LocalDateTime.now();
    }
}
