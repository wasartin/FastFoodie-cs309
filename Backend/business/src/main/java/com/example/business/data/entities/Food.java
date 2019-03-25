package com.example.business.data.entities;

import java.math.BigDecimal;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="food")
public class Food {
	
	@Id
	@Column(name="food_id")
	private int food_id; 
	
	@Column(name="food_name")
	private String food_name; 
	
	@Column(name="protein_total")
	private int protein_total;
	
	@Column(name="carb_total")
	private int carb_total;
	
	@Column(name="fat_total")
	private int fat_total;
	
	@Column(name="calorie_total")
	private int calorie_total;
	
	@Column(name="price")
	private String price;
	
	@Column(name="category")
	private String category;
	
	@Column (name="located_at")
	private int located_at;
	
	public Food() {
		super();
	}

	public Food(int food_id, String food_name, int protein_total, int carb_total, int fat_total, int calorie_total,
			String price, String category, int located_at) {
		super();
		this.food_id = food_id;
		this.food_name = food_name;
		this.protein_total = protein_total;
		this.carb_total = carb_total;
		this.fat_total = fat_total;
		this.calorie_total = calorie_total;
		this.price = price;
		this.category = category;
		this.located_at = located_at;
	}
	
	public int getLocated_at() {
		return located_at;
	}

	public void setLocated_at(int located_at) {
		this.located_at = located_at;
	}
	
	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}
	
	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public int getProtein_total() {
		return protein_total;
	}

	public void setProtein_total(int protein_total) {
		this.protein_total = protein_total;
	}

	public int getCarb_total() {
		return carb_total;
	}

	public void setCarb_total(int carb_total) {
		this.carb_total = carb_total;
	}

	public int getFat_total() {
		return fat_total;
	}

	public void setFat_total(int fat_total) {
		this.fat_total = fat_total;
	}

	public int getCalorie_total() {
		return calorie_total;
	}

	public void setCalorie_total(int calorie_total) {
		this.calorie_total = calorie_total;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
