package com.example.business.data.controllers;

import java.util.ArrayList;
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

import com.example.business.data.entities.Favorites;
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
		
		@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/{admin_email}")
		@ResponseBody
		public Ticket getTicket(@PathVariable String user_email, @PathVariable String admin_email){
			
			return null;
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "all/user/{user_email}")
		@ResponseBody
		public List<Ticket> getAllForUser(@PathVariable String user_email){
			return ticketRepo.getAllTicketsForUser(user_email);
		}
		
		private List<Ticket> getAllTicketsInDB(){
			Iterable<Ticket> tIters = ticketRepo.findAll();
			List<Ticket> tList = new ArrayList<Ticket>();
			tIters.forEach(tList::add);
			return tList;
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "all/admin/{user_email}")
		@ResponseBody
		public List<Ticket> getAllForAdmin(@PathVariable String user_email){
			return null;
			//TODO
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
		
		@RequestMapping(method = RequestMethod.POST, path = "/create/{user_email}/{admin_email}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createTicket(@PathVariable String user_email, @PathVariable String admin_email) {
			JSONObject response;
			try {
//				if(ticketRepo.getFoodRatingByUserAndFood() != null) {
//					throw new IllegalArgumentException();
//				}
				//ticketRepo.save(newRating);
				response = generateResponse(204, HttpStatus.OK, "Food has been rated");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		//TODO might need to use text or something.
		
		@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}/{admin_email}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject editTicket(@PathVariable String user_email, @PathVariable String admin_email) {
//			Ticket findOldVersion = ticketRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
//				if(findOldVersion == null) {
//					throw new IllegalArgumentException();
//				}
//				findOldVersion.setRating(rating);
//				ticketRepo.save(findOldVersion);
				response = generateResponse(204, HttpStatus.OK, "Ticket has been edited");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Ticket might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}/{admin_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
		@ResponseBody
		public JSONObject deleteFood(@PathVariable String user_email, @PathVariable int food_id) {
//			Ticket oldVersion = ticketRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
//				if(oldVersion == null) {
//					throw new IllegalArgumentException();
//				}
//				ticketRepo.deleteById(oldVersion.getRating_id());
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