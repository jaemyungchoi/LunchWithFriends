package CSCI201_LunchWithFriends;

import java.util.*;

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

/**
 * Servlet implementation class SearchRestaurantDisplay
 */
@WebServlet("/SearchRestaurantDisplay")
public class SearchRestaurantDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/lunchwithfriends?cloudSqlInstance=csci201-lunchwithfriends:us-west2:lunchwithfriends&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=lunchwithfriendsTest&password=lunchwithfriends";
	static Connection connection = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRestaurantDisplay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchname = request.getParameter("searchname");
		List<Business> restaurantList =  new ArrayList<Business>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM serialized_java_restaurants");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			businessDBAccess busDB = new businessDBAccess();
			
			while (resultSet.next()) {
				long serializedID = resultSet.getLong("serialized_id");
				Business addBusiness = (Business) busDB.deSerializeJavaObjectFromDB(connection, serializedID);

				String rName = addBusiness.getName();
				if(rName.toLowerCase().contains(searchname.toLowerCase())) {
					restaurantList.add(addBusiness);
				}
			}
			
			/*
			testing
			for(Business b : restaurantList) {
				response.getWriter().append("Found: " + b.getName()).append("\n");
			}
			*/
			
			HttpSession session = request.getSession(true);
			session.setAttribute("businessList", restaurantList);
			
			request.setAttribute("businessList", restaurantList);
			request.getRequestDispatcher("restaurants.jsp").forward(request, response);

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
