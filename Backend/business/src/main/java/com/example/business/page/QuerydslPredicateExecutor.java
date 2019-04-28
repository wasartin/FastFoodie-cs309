package com.example.business.page;

import javax.persistence.criteria.Predicate;

public interface QuerydslPredicateExecutor<T> {
	  Iterable<T> findAll(Predicate predicate);   

	  long count(Predicate predicate);            
}
