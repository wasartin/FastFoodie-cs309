package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="favorites")
public class Favorites {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="favorites_id")
	private int favorites_id;
	
	@Column(name="user_id")
	private String user_id; 
	
	@Column(name="fid")
	private int fid;
	
	public Favorites() {
		super();
	}
	
	public Favorites(int favorites_id, String user_id, int fid) {
		super();
		this.favorites_id = favorites_id;
		this.user_id = user_id;
		this.fid = fid;
	}

	public Favorites(String user_id, int fid) {
		super();
		this.user_id = user_id;
		this.fid = fid;
	}

	public int getFavorites_id() {
		return favorites_id;
	}

	public void setFavorites_id(int favorites_id) {
		this.favorites_id = favorites_id;
	}

	public int getFid() {
		return fid;
	}
	
	public void setFid(int fid) {
		this.fid = fid;
	} 
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
