package com.example.business.page;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FilterOperation {
	EQUAL						("eq"),
	NOT_EQUAL					("neq"),
	GREATER_THAN				("gt"),
	GREATER_THAN_OR_EQUAL_TO	("gte"),
	LESS_THAN					("lt"),
	LESS_THAN_OR_EQUAL_TO		("lte"),
	IN							("in"),
	NOT_IN						("nin"),
	BETWEEN						("btn"),
	ASCENDING					("asc"),
	DESCENDING					("desc"),
	CONTAINS					("like"),
	UNKNOWN						("");
	
	private String value;

	FilterOperation(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	public final static FilterOperation fromValue(String value){
		for(FilterOperation op : FilterOperation.values()){
			if(op.toString().equalsIgnoreCase(value)){
				return op;
			}
		}
		return UNKNOWN;
	}

}
