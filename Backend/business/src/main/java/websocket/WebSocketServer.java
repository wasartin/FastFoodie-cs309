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
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

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
		update(message);
	}
	
	//TODO update DB here
	private boolean updateDataBase(String username, String message) {
		
		return false;
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
	
	private String parseMessage(String message) {
		return null;
	}
	
}