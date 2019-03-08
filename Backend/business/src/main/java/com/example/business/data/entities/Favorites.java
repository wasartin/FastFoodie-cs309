package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="favorites")
public class Favorites {

	@Id
	@Column(name="favorites_id")
	private int favorites_id;
	
	@Column(name="user_id")
	private String user_id; 
	
	@Column(name="fid")
	private int fid;
	
	public Favorites() {
		super();
	}
	
	public Favorites(String user_id, int fid, int favorites_id) {
		super();
		this.favorites_id = favorites_id;
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
