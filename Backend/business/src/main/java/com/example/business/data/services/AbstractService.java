package com.example.business.data.services;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractService<E, K> {
	
	private CrudRepository<E, K> repo;
	
	public Optional<E> getEntityByID(K id) {
		return repo.findById(id);
	}
	
	public Iterable<E> getAllEntities(){
		return repo.findAll();
	}
	
	public ResponseEntity<?> createEntity(E newEntity, K id){
		ResponseEntity<?> response;
		String className = newEntity.getClass().getSimpleName();
		try {
			if(repo.existsById(id)) {
				throw new IllegalArgumentException();
			}
			repo.save(newEntity);
			response =  ResponseEntity.status(HttpStatus.OK).body(className);
		}catch (IllegalArgumentException e) {
			response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(className);
		}catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(className);
		}
		return response;
	}

}