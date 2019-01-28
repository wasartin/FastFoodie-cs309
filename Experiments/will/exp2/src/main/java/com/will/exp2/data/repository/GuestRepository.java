package com.will.exp2.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.will.exp2.data.entity.Guest;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}
