package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodRating")
public class FoodRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rating_id")
	private int rating_id;
	
	@Column(name="user_email")
	private String user_email; 
	
	@Column(name="food_id")
	private int food_id; 
	
	@Column(name="rating")
	private int rating;

	public FoodRating() {
		super();
	}

	public FoodRating(int rating_id, String user_email, int food_id, int rating) {
		super();
		this.rating_id = rating_id;
		this.user_email = user_email;
		this.food_id = food_id;
		this.rating = rating;
	}
	
	public int getRating_id() {
		return rating_id;
	}
	
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
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