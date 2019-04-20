package com.example.business.data.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author watis
 *
 * @param <E> The DAO
 * @param <K> The type of id this DAO uses.
 */
@Repository
public abstract class AbstractService<E, K> {
	
	@Autowired
	private CrudRepository<E, K> repo;
	
	private HttpHeaders headers = new HttpHeaders();

	/**
	 * Empty Constructor to init the HttpHeaders content type.
	 */
	public AbstractService() {
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<E> getEntityByID(K id) {
		return repo.findById(id);
	}
	
	/**
	 * 
	 * @return a list of all entities in DB
	 */
	public Iterable<E> getAllEntities(){
		return repo.findAll();
	}
	
	/**
	 * 
	 * @param newEntity
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> createEntity(E newEntity, K id){
		String className = newEntity.getClass().getSimpleName();
		HttpStatus resultingStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			if(repo.existsById(id)) {
				throw new IllegalArgumentException();
			}
			repo.save(newEntity);
			resultingStatus = HttpStatus.OK;
		}catch (IllegalArgumentException e) {
			resultingStatus = HttpStatus.BAD_REQUEST;
		}catch (Exception e) {
		}
		return new ResponseEntity<Object>(className, headers, resultingStatus);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> deleteEntity(K id){
		HttpStatus resultingStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String className = "";
		try {
			if(!repo.existsById(id)) {
				throw new IllegalArgumentException();
			}
			className = repo.findById(id).get().getClass().getSimpleName();
			repo.deleteById(id);
			resultingStatus = HttpStatus.OK;
		}catch (IllegalArgumentException e) {
			resultingStatus = HttpStatus.BAD_REQUEST;
		}catch (Exception e) {
		}
		return new ResponseEntity<Object>(className, headers, resultingStatus);
	}

	/**
	 * Editing an entity can only be done if the entity's id already exists in the DB. 
	 * @param entityToEdit
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> editEntity(E entityToEdit, K id){
		HttpStatus resultingStatus;
		String className = entityToEdit.getClass().getSimpleName();
		try {
			if(repo.existsById(id)) {
				repo.save(entityToEdit);
				resultingStatus = HttpStatus.OK;
			}else {
				resultingStatus = HttpStatus.BAD_REQUEST;
			}
		}catch (Exception e) {
			resultingStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(className, headers, resultingStatus);
	}
}