package CSCI201_LunchWithFriends;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uniqueID; 
	public String name;
	private String email;
	private HashMap<Business, ArrayList<InterestedUser>> potentialMatches;
	private HashMap<Business, Boolean> groupOrSingle; //true if interested in group, false if only single user
	
	/*chat implementation*/
	private HashMap<Business, HashMap<String, User>> chatMap;
	
	
	
    public User() {
        //super();
        // TODO Auto-generated constructor stub
    }

	public User(String name, String email, String uniqueID) {
		this.name = name;
		this.email = email;
		this.uniqueID = uniqueID;
	}
	
	public void setList() {
		
		potentialMatches = new HashMap<Business, ArrayList<InterestedUser>>();
		groupOrSingle = new HashMap<Business, Boolean>();
		
	}
	
	public void addChat(Business business, String uniqueRoomID, User otherChatUser)
	{
		HashMap<String,User> chatinsert = new HashMap<String, User>();
		chatinsert.put(uniqueRoomID, otherChatUser);
		chatMap.put(business, chatinsert);
	}
	
	/**
	 * Adds Business user is in.
	 * 
	 * If group is true, only adds Business to group 
	 * NEED: group chat addition if group is true
	 * 
	 * If group is false, add to list of Businesss and potential matches maps
	 */
	public void addBusiness(Business business, boolean group) {
		
		System.out.print("hey");
		if(group) {
			groupOrSingle.put(business, true);
		} else {
			groupOrSingle.put(business, false);
			ArrayList<InterestedUser> usersInterestedIn = new ArrayList<InterestedUser>();
			potentialMatches.put(business, usersInterestedIn);
			
		}
	}
	
	public boolean isInterestedInBusiness(Business business) {
		if(groupOrSingle.get(business) != null) return true;
		return false;
	}
	
	public boolean wantsGroupOrSingle(Business business) {
		
		if(isInterestedInBusiness(business)) {
			return groupOrSingle.get(business);
		} else {
			throw new RuntimeException("User is not interested in this Business");
		}
	}
	
	//When thisUser is interested in a user -> send a notif to user
	public void addIntrestedUserAtBusiness(Business business, User user, boolean isInterested) {
		if(potentialMatches.get(business) != null) {
			ArrayList<InterestedUser> listOfUsers = potentialMatches.get(business);
			listOfUsers.add(new InterestedUser(user, isInterested));
		}
		
		
		
		//send a notif to user
	}
	
	public boolean isInterestedInUserAtBusiness(Business business, User user) {
		ArrayList<InterestedUser> listOfUsers = potentialMatches.get(business);
		
		if(listOfUsers == null) throw new RuntimeException("Business has not been added yet");
		
		
		if(hasDecidedOn(business, user)) {
			InterestedUser intrUsr = getInterestedUser(business, user);
			if(intrUsr.isInterested()) return true;
			return false;
		}
		
		return false;
	}
	
	public boolean hasDecidedOn(Business business, User user) {
		ArrayList<InterestedUser> listOfUsers = potentialMatches.get(business);
		
		if(listOfUsers == null) throw new RuntimeException("Business has not been added yet");
		
		for(int i = 0; i < listOfUsers.size(); i++) {
			InterestedUser intrUsr = listOfUsers.get(i);
			if(intrUsr.getUser() == user) {
				return true;
			}
		}
		return false;
	}
	
	public InterestedUser getInterestedUser(Business business, User user) {
		ArrayList<InterestedUser> listOfUsers = potentialMatches.get(business);
		
		if(listOfUsers == null) throw new RuntimeException("Business has not been added yet");
		
		for(int i = 0; i < listOfUsers.size(); i++) {
			InterestedUser intrUsr = listOfUsers.get(i);
			if(intrUsr.getUser() == user) {
				return intrUsr;
			}
		}
		return null;
	}
	
	
	public ArrayList<InterestedUser> getChoicesForBusiness(Business business) {
		
		return potentialMatches.get(business);
	}
	
	
	public boolean isMatch(Business business, User user) {
		return (isInterestedInUserAtBusiness(business, user) && user.isInterestedInUserAtBusiness(business, this));
	}
	
	
	public String getEmail() {
		return email;
	}


	public String getName() {
		return name;
	}


	public String getUniqueID() {
		return uniqueID;
	}


	private class InterestedUser {
		
		User user;
		boolean isInterested;
		
		public InterestedUser(User user, boolean isInterested) {
			this.user = user;
			this.isInterested = isInterested;
		}
		
		public User getUser() {
			return this.user;
		}
		
		public boolean isInterested() {
			return this.isInterested;
		}
	}
	
	
	
	
}

