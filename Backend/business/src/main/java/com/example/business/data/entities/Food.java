package com.example.business.data.entities;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="food")
public class Food {
	@Id
	@Column(name="food_id")
	private String food_id; 
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
	@Column(name="weight_oz_total")
	private int weight_oz_total;
	@Column (name="price_total")
	private int price_total;
	
	public Food() {
		super();
	}

	public Food(String food_id, String food_name, int protein_total, int carb_total, int fat_total, int calorie_total,
			int weight_oz_total, int price_total, int located_at) {
		super();
		this.food_id = food_id;
		this.food_name = food_name;
		this.protein_total = protein_total;
		this.carb_total = carb_total;
		this.fat_total = fat_total;
		this.calorie_total = calorie_total;
		this.weight_oz_total = weight_oz_total;
		this.price_total = price_total;
		this.located_at = located_at;
	}

	public int getLocated_at() {
		return located_at;
	}

	public void setLocated_at(int located_at) {
		this.located_at = located_at;
	}

	@Column (name="located_at")
	private int located_at;

	public String getFood_id() {
		return food_id;
	}

	public void setFood_id(String food_id) {
		this.food_id = food_id;
	}

	public int getPrice_total() {
		return price_total;
	}

	public void setPrice_total(int price_total) {
		this.price_total = price_total;
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

	public int getWeight_oz_total() {
		return weight_oz_total;
	}

	public void setWeight_oz_total(int weight_oz_total) {
		this.weight_oz_total = weight_oz_total;
	}
}
