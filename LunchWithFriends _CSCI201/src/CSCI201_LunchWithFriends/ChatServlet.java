package CSCI201_LunchWithFriends;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/chatServlet")
public class ChatServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		//The request parameter is what the Java code in chatBasic.jsp uses to get info
		String getuserID = request.getParameter("userID_");
		String getroom = request.getParameter("room_");
		
		//The session attribute is what the Javascript code in chatBasic.jsp uses to get info
		HttpSession session = request.getSession(true);
		session.setAttribute("userID", getuserID);
		session.setAttribute("room", getroom);

		request.getRequestDispatcher("chatBasic.jsp").forward(request, response);
	}
}