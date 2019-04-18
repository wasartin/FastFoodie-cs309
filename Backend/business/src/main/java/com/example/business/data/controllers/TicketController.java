package com.example.business.data.controllers;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Ticket;
import com.example.business.data.repositories.TicketRepository;

@RestController
@RequestMapping(value="/tickets")
public class TicketController{

		@Autowired
		TicketRepository ticketRepo;
		
		@RequestMapping(method = RequestMethod.GET, path = "/all")
		@ResponseBody
		public List<Ticket> getAll(){
			return (List<Ticket>) ticketRepo.findAll();
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "all/user/{user_email}")
		@ResponseBody
		public List<Ticket> getAllForUser(@PathVariable String user_email){
			return ticketRepo.getAllTicketsForUser(user_email);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "all/admin/{admin_email}")
		@ResponseBody
		public List<Ticket> getAllForAdmin(@PathVariable String admin_email){
			return ticketRepo.getAllTicketsForAdmin(admin_email);
		}
		
		@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createTicketByJSON(@RequestBody Ticket newTicket) {
			JSONObject response;
			try {
				if(ticketRepo.existsById(newTicket.getTicket_id())) {
					throw new IllegalArgumentException();
				}
				ticketRepo.save(newTicket);
				response = generateResponse(204, HttpStatus.OK, "Ticket has been made");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Ticket might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{ticket_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
		@ResponseBody
		public JSONObject deleteTicket(@PathVariable int ticket_id) {
			JSONObject response;
			try {
				if(!ticketRepo.existsById(ticket_id)) {
					throw new IllegalArgumentException();
				}
				ticketRepo.deleteById(ticket_id);
				response = generateResponse(204, HttpStatus.OK, "Ticket has been deleted");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that ticket in the database, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@SuppressWarnings("unchecked")
		private JSONObject generateResponse(int status, HttpStatus input, String message) {
			JSONObject response = new JSONObject();
			response.put("status", status);
			response.put("HttpStatus", input);
			response.put("message", message);
			return response;
		}
}