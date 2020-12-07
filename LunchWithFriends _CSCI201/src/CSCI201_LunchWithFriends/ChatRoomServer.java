package CSCI201_LunchWithFriends;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chatroomServer/{userID}/{room}")
public class ChatRoomServer {
	/*
	 * Map of key: room number  value: set of sessions (iow set of users in a chat)
	 * in easier terms- the key is the chatName/roomName, and the value is the set of users in that chat
	 */
	private static Map<String, Set<Session>> chatRoomMap = (Map<String, Set<Session>>) Collections.synchronizedMap(new HashMap<String, Set<Session>>());

	@OnOpen
	public void open(Session session, @PathParam("userID") String userID, @PathParam("room") String room) throws IOException {
		session.getUserProperties().put("userID", userID);
		session.getUserProperties().put("room", room);
		/*
		 * getChat --> Either finds the room this user will chat inside
		 * 		if no such room exists --> Creates chatroom + stores it inside chatRoomMap as <room, this users session>
		 * chatroom will be a set of user sessions who can message each other
		 *  	-->in this case, the set of other users this user session with chat with
		 */
		Set<Session> chatroom = getChat(room);
		/*
		 * Adding current user's session into created chatroom
		 */
		chatroom.add(session);
		session.getBasicRemote().sendText(makeText("Chat System", "you are now connected as "+userID+" in ChatRoom: "+room+"!"));
	}
	
	@OnMessage
	public void recievedMessage(String message, Session session) throws IOException
	{
		String userID = (String) session.getUserProperties().get("userID");
		String room = (String) session.getUserProperties().get("room");
		/*
		 * find the chatroom the user is in */
		Set<Session> chatroom = getChat(room);
		/*
		 * Sends message to all chat users in the same chatroom */
		for(Session rs : chatroom) {
			if(rs.isOpen()) {
				rs.getBasicRemote().sendText(makeText(userID, message));
			}
			/*
			 * I'll implement this after
			//Someone is not online
			else {
				session.getBasicRemote().sendText(makeText("Chat System", ""));
			} */
		}
	}
	
	@OnClose
	public void connectionClosed(Session session) throws IOException {
		/* finds the room name of the chatroom the user session was in */
		String room = (String) session.getUserProperties().get("room");
		/* finds the chatroom (set of sessions) using room name and removes user session */
		Set<Session> chatroom = getChat(room);
		chatroom.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		try {
			throw t;
		}
		catch(Throwable error) {
			error.printStackTrace();
		}
	}
	
	private String makeText(String userID, String msg) {
		JsonObject jObj = Json.createObjectBuilder().add("msg", userID+": "+msg).build();
		StringWriter sWriter = new StringWriter();
		try(JsonWriter jWriter = Json.createWriter(sWriter)){
			jWriter.write(jObj);
		}
		return sWriter.toString();
	}
	
	public Set<Session> getChat(String roomName){
		Set<Session> foundChat = chatRoomMap.get(roomName);
		if(foundChat == null) {
			foundChat = Collections.synchronizedSet(new HashSet<Session>());
			chatRoomMap.put(roomName, foundChat);
		}
		return foundChat;
	}
}