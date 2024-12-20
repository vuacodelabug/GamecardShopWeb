package com.ganecardshop.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String gender;
    private java.sql.Date dateOfBirth;
    

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; //1 user có 1 profile


    public void setUser(User user) {
        this.user = user;
    }
    // public User getUser(){
    //     return user;
    // }

    // Constructors
    public Profile() {}

    public Profile(User user) {
        this.user = user;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public java.sql.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.sql.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
