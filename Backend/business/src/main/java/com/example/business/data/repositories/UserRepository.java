package com.example.business.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,String>{
	
	@Query(value = "select * from user where email = ?1", nativeQuery = true)
	User findByID(String email);

	void delete(User user);
}
