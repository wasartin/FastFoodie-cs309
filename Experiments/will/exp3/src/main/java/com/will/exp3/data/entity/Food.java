package com.will.exp3.data.entity;

import java.util.ArrayList;

public class Food {
	
	private long nutrientDBNum;
	private String name;
	private ArrayList<String> ingredients;
	
	public Food() {
		
	}
	
	public Food(long nutrientDBNum) {
		this.nutrientDBNum = nutrientDBNum;
	}
	

	public long getNutrientDBNum() {
		return nutrientDBNum;
	}

	public void setNutrientDBNum(long nutrientDBNum) {
		this.nutrientDBNum = nutrientDBNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
}
