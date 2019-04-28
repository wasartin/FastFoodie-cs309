package com.example.business.page;

import com.example.business.data.entities.Food;
import com.example.business.page;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class FoodPredicate {
	private SearchCriteria criteria;
	
	public BooleanExpression getPredicate() {
        PathBuilder<Food> entityPath = new PathBuilder<>(Food.class, "food");
 
        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (FilterOperation.fromValue(criteria.getOperation())) {
                case EQUAL:
                    return path.eq(value);
                case GREATER_THAN_OR_EQUAL_TO:
                    return path.goe(value);
                case LESS_THAN_OR_EQUAL_TO:
                    return path.loe(value);
            }
        } 
        else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

	private boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
