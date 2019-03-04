package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id //specifies that this is a primary key
	@Column(name="user_email")
	private String user_email;
	@Column(name="user_type")
	private String user_type;
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public User() {//No arg constructor required by JPA for building properly
		super();
	}
	public User(String user_email, String user_type) {
		super();
		this.user_email = user_email;
		this.user_type = user_type;
	}
	
}