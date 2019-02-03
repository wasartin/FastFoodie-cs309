package com.will.exp3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FOOD")
public class Food {
	@Id
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="PROTEIN")
	private String protein;//don't know what these should be yet
	@Column(name="CALORIES")
	private String calories;//"
	
	public Food() {
		super();
	}

	public Food(long id, String name, String protein, String calories) {
		super();
		this.id = id;
		this.name = name;
		this.protein = protein;
		this.calories = calories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(String protein) {
		this.protein = protein;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}
	
	
}
