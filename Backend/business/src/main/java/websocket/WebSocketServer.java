package websocket;

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
import org.springframework.stereotype.Component;

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
	
	@Autowired
	FoodRatingRepository foodRatingRepo;

	private static Map<Session, String> sessionUserNameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();
	
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		sessionUserNameMap.put(session, username);
		usernameSessionMap.put(username, session);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException{
		String username = sessionUserNameMap.get(session);
		updateDataBase(username, message);
		update(message);
	}
	
	private boolean updateDataBase(String username, String message) {
		//"299, 5"
		String[] parsedMessage = message.split(",");
		int food_id = Integer.valueOf(parsedMessage[0]);
		int rating = Integer.valueOf(parsedMessage[1]);
		FoodRating newRating = new FoodRating();
		newRating.setFood_id(food_id);
		newRating.setRating(rating);
		newRating.setUser_email(username);
		try {
			foodRatingRepo.save(newRating);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	@OnClose
	public void onClose(Session session) throws IOException{
		String username = sessionUserNameMap.get(session);
		sessionUserNameMap.remove(session);
    	usernameSessionMap.remove(username);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable){
		logger.info("Entered into Error");
	}
	
	private static void update(String message) throws IOException{
		sessionUserNameMap.forEach((session, username) -> {
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