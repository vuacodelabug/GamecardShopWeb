package com.ganecardshop.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int value;
    private int type; // 1: phần trăm, 2: Giảm trực tiếp (VND)
    private LocalDateTime startday;
    private LocalDateTime endday;
    private int status; // 1: đang áp dụng, 2: đã hết hạn, 3: đã hủy, 0: đã xóa

    @Transient
    private String formattedStartDay;
    @Transient
    private String formattedEndDay;

    // constructer
    public Discount() {
    }
    public Discount(String name, String description, int value, int type, LocalDateTime startday, LocalDateTime endday, int status) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.type = type;
        this.startday = startday;
        this.endday = endday;
        this.status = status;
    }

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gamecard> gameCards;




    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getStartday() {
        return startday;
    }

    public void setStartday(LocalDateTime startday) {
        this.startday = startday;
    }

    public LocalDateTime getEndday() {
        return endday;
    }

    public void setEndday(LocalDateTime endday) {
        this.endday = endday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

     // Getter and Setter methods for formattedStartDay and formattedEndDay
     public String getFormattedStartDay() {
        return formattedStartDay;
    }

    public void setFormattedStartDay(String formattedStartDay) {
        this.formattedStartDay = formattedStartDay;
    }

    public String getFormattedEndDay() {
        return formattedEndDay;
    }

    public void setFormattedEndDay(String formattedEndDay) {
        this.formattedEndDay = formattedEndDay;
    }

}
