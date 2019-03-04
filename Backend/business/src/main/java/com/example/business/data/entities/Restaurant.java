package com.example.business.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant")
public class Restaurant {

	//TODO this should be an int
	@Id
	@Column(name="restaurant_id")
	private int restaurant_id;
	@Column(name="restaurant_name")
	private String restaurant_name;
	@Column(name="last_updated")
	private Timestamp last_updated;
	
	public Restaurant(){
		super();
	}
	
	public Restaurant(int id, String name, Timestamp last_updated) {
		this.restaurant_id = id;
		this.restaurant_name = name;
		this.last_updated = last_updated;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public Timestamp getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(Timestamp last_updated) {
		this.last_updated = last_updated;
	}
}