package com.will.exp2.data.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.will.exp2.data.entity.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByDate(Date date);
}