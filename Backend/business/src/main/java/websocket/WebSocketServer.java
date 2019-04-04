package websocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class WebSocketServer {

	private static Map<Session, String> sessionUserNameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();
	
}
