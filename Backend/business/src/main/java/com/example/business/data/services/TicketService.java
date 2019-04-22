package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Ticket;
import com.example.business.data.repositories.TicketRepository;

@Service
public class TicketService extends AbstractService<Ticket, Integer>{
	@Autowired
	TicketRepository repo;

	public List<Ticket> getAllticketsByUser(String user_email){
		return repo.getAllTicketsForUser(user_email);
	}
	
	public List<Ticket> getAllTicketsForAdmin(String admin_email){
		return repo.getAllTicketsForAdmin(admin_email);
	}
}
