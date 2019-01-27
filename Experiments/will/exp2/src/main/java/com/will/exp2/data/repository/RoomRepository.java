package com.will.exp2.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.will.exp2.data.entity.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>{
	Room findByNumber(String number); //https://docs.spring.io/spring-data/jpa/docs/1.10.6.RELEASE/reference/html/#repositories.query-methods.query-creation
}
