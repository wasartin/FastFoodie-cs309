package com.example.business.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,String>{

}
