package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;
import com.example.business.data.entities.Ticket;

@Repository
public interface FoodRepository extends CrudRepository<Food, Integer>{
}
