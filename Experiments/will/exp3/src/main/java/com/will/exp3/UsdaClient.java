package com.will.exp3;

import org.json.simple.JSONObject;

public class UsdaClient {

	private final String API_KEY = "pd4EQsX2gq4jS3GAkUYcaqM6u8w5ylbB56MFlTQ4";
	private final String CONTENT_TYPE = "application/json";
	private final String JSON_REQUEST = "json";
	private final String BASE_URL = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=%s&type=b&format=%s&api_key=%s	";
	
	public UsdaClient() {
		
	}
	
	//BigMac ID = 21237
	public JSONObject getResponse(final long NDB_NO) {
		
		String inputURL = String.format(BASE_URL, NDB_NO, JSON_REQUEST, API_KEY);
		
		
		return null;
	}
}
