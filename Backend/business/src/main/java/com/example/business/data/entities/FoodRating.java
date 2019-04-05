package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodRating")
public class FoodRating {
	
	@Id
	@Column(name="rating_id")
	private int rating_id;
	
	@Column(name="user_id")
	private String user_id; 
	
	@Column(name="food_id")
	private int food_id; 
	
	@Column(name="rating")
	private int rating;

	public FoodRating() {
		super();
	}

	public FoodRating(int rating_id, String user_id, int food_id, int rating) {
		super();
		this.rating_id = rating_id;
		this.user_id = user_id;
		this.food_id = food_id;
		this.rating = rating;
	}
	
	public int getRating_id() {
		return rating_id;
	}
	
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_email) {
		this.user_id = user_email;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}