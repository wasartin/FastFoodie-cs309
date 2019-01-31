package com.will.exp2.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.will.exp2.business.domain.RoomReservation;
import com.will.exp2.business.service.ReservationService;

@RestController
@RequestMapping(value="/api")
public class ReservationServiceController {

	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(method=RequestMethod.GET, value="/reservation/{date}")
	public List<RoomReservation> getAllReservationsForDate(@PathVariable(value="date")String dateString){
		return this.reservationService.getRoomReservationsForDate(dateString);
	}
}
