package CSCI201_LunchWithFriends;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

import java.util.*;

/**
 * Servlet implementation class DisplayInterestedUsers
 */
@WebServlet("/DisplayInterestedUsers")
public class DisplayInterestedUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/lunchwithfriends?cloudSqlInstance=csci201-lunchwithfriends:us-west2:lunchwithfriends&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=lunchwithfriendsTest&password=lunchwithfriends";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayInterestedUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String searchname = request.getParameter("currRestaurant");
		ArrayList<User> list = new ArrayList<User>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM serialized_java_restaurants");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			businessDBAccess busDB = new businessDBAccess();
			
			
			while (resultSet.next()) {
				long serializedID = resultSet.getLong("serialized_id");
				Business addBusiness = (Business) busDB.deSerializeJavaObjectFromDB(connection, serializedID);
				String name = addBusiness.getName();
				System.out.println(name + "  /  " + searchname + "\n");
				if(name.equalsIgnoreCase(searchname)) {
					list = addBusiness.getInterestedUsers();
					//WHEN WE PRESS SINGLE OPTION BUTTON --> get list + INSERT USER INTO INTERESTED USERS
					HttpSession session = request.getSession(true);
					User u = (User) session.getAttribute("currUser");
					System.out.println("\n" + u.getName() + ": " +u.getUniqueID() + "\n");
					addBusiness.likesRestaurant((User) session.getAttribute("currUser"), true);
					if(addBusiness.getInterestedUsers() == null) System.out.println("interestedusers is null");
					else System.out.println("its not null");
					//update database for restaurant
					
					//DELETE FROM DATABASE
					String query = "DELETE from serialized_java_restaurants where serialized_id = ?";
				    PreparedStatement ps = connection.prepareStatement(query);
				      ps.setLong(1, serializedID);
				      ps.execute();
				     //RE INSERT INTO DATABASE WITH UPDATE
					businessDBAccess bdb = new businessDBAccess();
					long sid = busDB.serializeJavaObjectToDB(connection, addBusiness);
					break;
				}	
			}
			
		} catch(Exception e) {
			
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("possibleUsersList", list);
		
		request.setAttribute("possibleUsersList", list);
		request.getRequestDispatcher("match.jsp").forward(request, response);

	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
