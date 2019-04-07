package com.example.business.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

@ServerEndpoint("/websocket/{user_email}")
public class WebSocketServer {
	@Autowired
	FoodRatingRepository foodRatingRepo;

	private static Map<Session, String> sessionUserEmailMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();
	
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("user_email") String user_email) throws IOException {
		sessionUserEmailMap.put(session, user_email);
		usernameSessionMap.put(user_email, session);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		updateDataBase(user_email, message);
		update(message);
	}
	
	private boolean updateDataBase(String user_email, String message) {
		//"299, 5"
		String[] parsedMessage = message.split(",");
		int food_id = Integer.valueOf(parsedMessage[0]);
		int rating = Integer.valueOf(parsedMessage[1]);
		FoodRating newRating = new FoodRating();
		newRating.setFood_id(food_id);
		newRating.setRating(rating);
		newRating.setUser_email(user_email);
		try {
			foodRatingRepo.save(newRating);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	@OnClose
	public void onClose(Session session) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		sessionUserEmailMap.remove(session);
    	usernameSessionMap.remove(user_email);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable){
		logger.info("Entered into Error");
	}
	
	private static void update(String message) throws IOException{
		sessionUserEmailMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
}