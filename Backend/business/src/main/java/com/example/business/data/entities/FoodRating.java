package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="foodRating")
@IdClass(CompositeKey.class)
public class FoodRating {
	@Id
	@Column(name="user_id")
	private int user_id; 
	
	@Id
	@Column(name="food_id")
	private String food_id; 
	
	@Column(name="rating")
	private int rating;

	public FoodRating() {
		super();
	}

	public FoodRating(int user_id, String food_id, int rating) {
		super();
		this.user_id = user_id;
		this.food_id = food_id;
		this.rating = rating;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFood_id() {
		return food_id;
	}

	public void setFood_id(String food_id) {
		this.food_id = food_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}