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
	
//	//TODO
//	public ResponseEntity<?> createEntity(E newEntity, K id){
//		JSONObject response;
//		try {
//			if(repo.existsById(id)) {
//				throw new IllegalArgumentException();
//			}
//			repo.save(newEntity);
//			response = generateResponse(HttpStatus.OK, newEntity.getClass().toString());
//		}catch (IllegalArgumentException e) {
//			response = generateResponse(HttpStatus.BAD_REQUEST, );
//			return new ResponseEntity.badRequest().body(newEntity.getClass().toString());
//		}catch (Exception e) {
//			response = generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, newEntity.getClass().toString());
//		}
//		return new ResponseEntity<>();
//	}

//	@SuppressWarnings("unchecked")
//	final ResponseEntity<?> generateResponse(HttpStatus state, String keyword) {
//		ResponseEntity response = null;
//		String responseMessage = "";
//		switch(state) {
//		case OK:
//			
//			responseMessage = String.format(MESSAGE_SUCCESS, keyword);
//			break;
//		case BAD_REQUEST:
//			responseMessage = String.format(MESSAGE_FAIL_THEIR_FAULT, keyword);
//			break;
//		case INTERNAL_SERVER_ERROR:
//		default :
//			responseMessage = String.format(MESSAGE_FAIL_OUR_FAULT);
//			break;
//		}
//		response = new
//	}
}
