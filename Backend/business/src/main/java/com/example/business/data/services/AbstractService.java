package com.example.business.data.services;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractService<E, K> {

	private static final String MESSAGE_SUCCESS = "%s has been created";
	private static final String MESSAGE_FAIL_THEIR_FAULT = "%s might already exist, or your fields are incorrect, double check your request";
	private static final String MESSAGE_FAIL_OUR_FAULT = "Server might be down now. Try again";
	
	private CrudRepository<E, K> repo;
	
	public Optional<E> getEntityByID(K id) {
		return repo.findById(id);
	}
	
	public Iterable<E> getAllEntities(){
		return repo.findAll();
	}
	
	//TODO
	public ResponseEntity<?> createEntity(){
		return null;
	}

	@SuppressWarnings("unchecked")
	final JSONObject generateResponse(String keyword, HttpStatus state, HttpStatus input) {
		String responseMessage = "";
		switch(state) {
		case OK:
			responseMessage = String.format(MESSAGE_SUCCESS, keyword);
			break;
		case BAD_REQUEST:
			responseMessage = String.format(MESSAGE_FAIL_THEIR_FAULT, keyword);
			break;
		case INTERNAL_SERVER_ERROR:
		default :
			responseMessage = String.format(MESSAGE_FAIL_OUR_FAULT);
			break;
		}
		JSONObject response = new JSONObject();
		response.put("status", state);
		response.put("HttpStatus", input);
		response.put("message", responseMessage);
		return response;
	}
}
