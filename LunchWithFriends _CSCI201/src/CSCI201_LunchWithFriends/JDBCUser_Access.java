package CSCI201_LunchWithFriends;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class JDBCUser_Access
 */
@WebServlet("/JDBCUser_Access")
public class JDBCUser_Access extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/lunchwithfriends?cloudSqlInstance=csci201-lunchwithfriends:us-west2:lunchwithfriends&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=lunchwithfriendsTest&password=lunchwithfriends";
	static Connection connection = null;
	boolean loggedIn = false;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			
			
			userDBAccess userAccess = new userDBAccess();
			String name = " ", email = " ", id = " ";
			String temp = request.getParameter("USERname");
			String[] arr = temp.split(",");
				name = arr[0];
				email = arr[1];
				id = arr[2];
			
			User u = new User(name,email,id);
			
			//send info to index.jsp
			
			
			
			User tempUser = null;

				//try {
					String sql = "(SELECT serID FROM serIDs WHERE email = '" + email + "')";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						Long serID = rs.getLong("serID");
						u = (User) userAccess.deSerializeJavaObjectFromDB(connection, serID);
					} else {
						CurrUser user = new CurrUser();
						user.currUser = u;
						System.out.println("cmbjhvjhfkon");
						
						//if(tempUser == null) {
							long serialized_id = userAccess.serializeJavaObjectToDB(connection, u);
							
							String sql1 = "INSERT INTO serIDs VALUES (" + "\'" + serialized_id + "\', " + "\'" + email + " \');";
									PreparedStatement ps1 = connection.prepareStatement(sql1);
									ps1.execute(sql1);
						//}
					}

			
					HttpSession session = request.getSession(true);
					session.setAttribute("currUser", u);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDBCUser_Access() {
        super();
        // TODO Auto-generated constructor stub
    }

}
