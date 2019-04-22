package com.example.business.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.business.data.entities.Ticket;
import com.example.business.data.repositories.TicketRepository;
import com.example.business.data.services.TicketService;

public class TicketServiceTest {

	@InjectMocks
	TicketService ticketService;
	
	@Mock
	TicketRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getTicketByIdTest() {
		Ticket newTicket = new Ticket();
		Optional<Ticket> ticketOp= Optional.of(newTicket);
		when(repo.findById(1)).thenReturn(ticketOp);
		
		ticketService.getEntityByID(1);
		verify(repo, times(1)).findById(1);
	}
	
	@Test
	public void getTicketByIdTest_Fail() {
		Ticket newTicket = new Ticket();
		Optional<Ticket> ticketOp= Optional.of(newTicket);
		when(repo.findById(1)).thenReturn(ticketOp);
		
		ticketService.getEntityByID(1);
		verify(repo, never()).existsById(1);
	}
	
	@Test
	public void getAllTickets() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		when(repo.findAll()).thenReturn(tickets);
		
		ticketService.getAllEntities();
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getAllTicketsByUser() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		when(repo.getAllTicketsForUser("Tom")).thenReturn(tickets);
		
		ticketService.getAllticketsByUser("Tom");
		verify(repo, times(1)).getAllTicketsForUser("Tom");
		assertThat(repo.getAllTicketsForUser("Tom"), is(notNullValue()));
	}
	
	@Test
	public void getAllTicketsForAdmin() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		when(repo.getAllTicketsForAdmin("Tom")).thenReturn(tickets);
		
		ticketService.getAllTicketsForAdmin("Tom");
		verify(repo, times(1)).getAllTicketsForAdmin("Tom");
		assertThat(repo.getAllTicketsForAdmin("Tom"), is(notNullValue()));
	}

	@Test
	public void createTicketTest() {
		Ticket toAdd = new Ticket(3, "jongreaz@gmail.com", "Shawn", new Timestamp(System.currentTimeMillis()), "nothing", "none");
		when(repo.existsById(toAdd.getTicket_id())).thenReturn(false);
		when(repo.save(toAdd)).thenReturn(new Ticket());
		
		ResponseEntity<?> response = ticketService.createEntity(toAdd, toAdd.getTicket_id());
		assertThat(repo.save(toAdd), is(notNullValue()));
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toAdd.getClass().getSimpleName());
	}
	
	@Test
	public void createTicketTest_Fail() {
		Ticket alreadyInDB = new Ticket(3, "jongreaz@gmail.com", "Shawn", new Timestamp(System.currentTimeMillis()), "nothing", "none");
		when(repo.existsById(alreadyInDB.getTicket_id())).thenReturn(true);

		ResponseEntity<?> response = ticketService.createEntity(alreadyInDB, alreadyInDB.getTicket_id());
		
		verify(repo, never()).save(alreadyInDB);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), alreadyInDB.getClass().getSimpleName());
	}

	@Test 
	public void deleteTicketTest() {
		Ticket alreadyInDB = new Ticket(3, "jongreaz@gmail.com", "Shawn", new Timestamp(System.currentTimeMillis()), "nothing", "none");
		when(repo.existsById(alreadyInDB.getTicket_id())).thenReturn(true);
		when(repo.findById(alreadyInDB.getTicket_id())).thenReturn(Optional.of(alreadyInDB));
		
		ResponseEntity<?> response = ticketService.deleteEntityById(alreadyInDB.getTicket_id());
		verify(repo, times(1)).deleteById(alreadyInDB.getTicket_id());
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}

	@Test 
	public void deleteFavoriteTest_Fail() {
		Ticket notInDB = new Ticket(3, "jongreaz@gmail.com", "Shawn", new Timestamp(System.currentTimeMillis()), "nothing", "none");
		when(repo.existsById(notInDB.getTicket_id())).thenReturn(false);
		
		ResponseEntity<?> response = ticketService.deleteEntityById(notInDB.getTicket_id());
		verify(repo, never()).deleteById(notInDB.getTicket_id());
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
}