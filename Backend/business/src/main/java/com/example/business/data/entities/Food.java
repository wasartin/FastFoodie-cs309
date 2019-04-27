package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 * An entity (or dao) that maps to the table 'food' in the Database. This represents the model of the object.
 * @author watis
 *
 */
@Entity
@Table(name="food")
public class Food {
	
	/**
	 * Each food is uniquely identified by it's id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="food_id")
	private Integer food_id; 
	
	/**
	 * For displaying an easy to understand name to the user.
	 */
	@Column(name="food_name")
	private String food_name; 
	
	/**
	 * Grams of protein
	 */
	@Digits(fraction = 0, integer = 10)
	@Column(name="protein_total")
	private int protein_total;
	
	/**
	 * Grams of carbs
	 */
	@Column(name="carb_total")
	private int carb_total;
	
	/**
	 * Grams of fat
	 */
	@Column(name="fat_total")
	private int fat_total;
	
	/**
	 * Total number of calories
	 */
	@Column(name="calorie_total")
	private int calorie_total;
	
	/**
	 * Price in dollars
	 */
	@Column(name="price")
	private String price;
	
	/**
	 * A restaurant specific category.
	 */
	@Column(name="category")
	private String category;
	
	/**
	 * The id of the restaurant it is located at.
	 */
	@Column (name="located_at")
	private int located_at;
	
	/**
	 * Average rating of food.
	 */
	@Column(name = "rating")
	private double rating;
	
	/**
	 * The number of times this food has been rated.
	 */
	@Column(name = "rating_count")
	private int rating_count;
	
	public Food() {
		super();
	}

	public Food(int food_id, String food_name, int protein_total, int carb_total, int fat_total, int calorie_total,
			String price, String category, int located_at, double rating, int rating_count) {
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
		this.rating = rating;
		this.rating_count = rating_count;
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
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public int getRating_count() {
		return this.rating_count;
	}
	
	public void setRating_count(int rating_count) {
		this.rating_count = rating_count;
	}

}