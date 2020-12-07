package CSCI201_LunchWithFriends;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JDBC_Access
 */
@WebServlet("/JDBC_Access")
public class JDBC_Access extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/lunchwithfriends?cloudSqlInstance=csci201-lunchwithfriends:us-west2:lunchwithfriends&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=lunchwithfriendsTest&password=lunchwithfriends";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDBC_Access() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usersTest");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Test test = new Test();
			userDBAccess userAccess = new userDBAccess();
			Location loc = new Location(27.2046, 77.4977);
			//List<Business> bTemp = YelpAPIParser.getBusiness(" ", loc);
			tempR t = new tempR();
			
			List<Business> bTemp = t.businesses;
			businessDBAccess busDB = new businessDBAccess();
			
			
			for(Business i : bTemp) {
				
				long serialized_id = busDB.serializeJavaObjectToDB(connection, i);	
				Business objFromDB = (Business) busDB.deSerializeJavaObjectFromDB(connection, serialized_id);
				response.getWriter().append(objFromDB.getName() + "\n");
				
			}
			
			while (resultSet.next()) {
				String nameString = resultSet.getString("guestName");
				response.getWriter().append(nameString);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
