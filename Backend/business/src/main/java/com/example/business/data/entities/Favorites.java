package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="favorites")
public class Favorites {

	@Id
	@Column(name="user_id")
	private int user_id; 
	@Column(name="fid")
	private String fid;
	
	public Favorites() {
		super();
	}
	
	public Favorites(int user_id, String fid) {
		super();
		this.user_id = user_id;
		this.fid = fid;
	}
	
	public String getFid() {
		return fid;
	}
	
	public void setFid(String fid) {
		this.fid = fid;
	} 
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
