package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

	@Query(value ="SELECT * FROM ticket t WHERE t.user_id =?1", nativeQuery = true)
	List<Ticket> getAllTicketsForUser(String user_id);
	
	@Query(value ="SELECT * FROM ticket t WHERE t.admin_id =?1", nativeQuery = true)
	List<Ticket> getAllTicketsForAdmin(String admin_id);
	
}
