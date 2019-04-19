package com.example.business.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private int ticket_id; 
	
	private String user_id;
	private String admin_id;
	private Timestamp date_assigned;
	private String text;
	private String category;
	
	public Ticket() {
		super();
	}

	public Ticket(int ticket_id, String user_id, String admin_id, Timestamp date, String text, String category) {
		super();
		this.ticket_id = ticket_id;
		this.user_id = user_id;
		this.admin_id = admin_id;
		this.date_assigned = date;
		this.text = text;
		this.category = category;
	}
	
	public int getTicket_id() {
		return ticket_id;
	}
	
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public Timestamp getDate() {
		return date_assigned;
	}

	public void setDate(Timestamp date) {
		this.date_assigned = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}