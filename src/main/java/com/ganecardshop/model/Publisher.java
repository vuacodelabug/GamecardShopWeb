package com.ganecardshop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "publisher")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String img;
	private String description;
	private Integer status = 1;

	public Publisher() {
	}

	public Publisher(String name, String img, String description) {
		this.name = name;
		this.img = img;
		this.description = description;
		this.status = 1;
	}

	@OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore // Tránh việc ánh xạ ngược lại từ Gamecard về Publisher
	private List<Gamecard> gameCards;

	public List<Gamecard> getGamecards() {
		return gameCards;
	}

	public void setGamecards(List<Gamecard> gameCards) {
		this.gameCards = gameCards;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// Getters and Setters
}
