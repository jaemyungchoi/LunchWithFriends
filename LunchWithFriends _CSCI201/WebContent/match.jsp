<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
<%@ page import="CSCI201_LunchWithFriends.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="style.css" rel="stylesheet" type="text/css">
	<link 
	href="https://fonts.googleapis.com/css2?family=Lato:wght@700&display=swap" 
	rel="stylesheet">
	<link
	href="https://fonts.googleapis.com/css2?family=Commissioner:wght@300&display=swap"
	rel="stylesheet">
	<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
	<title>Matches</title>
	
	<style>
		#header {
			height: 150px;
			background-color: #f7b819;
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			padding: 20px;
		}

		h1 {
			color: white;
			float: left;
		}

		.searchbar {
			position: fixed;
			top: 40px;
			left: 30%;
			width: 40%;
		}

		.searchbar button {
			background: white;
			color: #f7b819;
			width: 20%;
		}

		.searchbar button:hover {
			background: #f7b819;
			color: white;
		}

		#icons button {
			color: white;
			font-size: 2.5em;
			padding: 20px;
			float: right;
			background: none;
			border: none;
		}
		#main {
			width: 70%;
			position: relative;
			top: 150px;
			left: 200px;
		}
		.match {
			width: 100%;
			border: 2px solid #d4d4d4;
			border-radius: 15px;
			display: inline-block;
		}
		.match-icons button {
			font-size: 2.5em;
			padding: 20px;
			float: right;
			background: none;
			border: none;
		}
		#yes {
			color: green;
		}
		#no {
			color: red;
		}
		h2 {
			font-size: 2em;
			font-family: 'Lato', sans-serif;
		}
		p {
			display: inline-block;
		}
		
	</style>
</head>
<body>
	<%
		ArrayList<User> userList = (ArrayList<User>)request.getAttribute("possibleUsersList");
	%>
	<div id="header">
		<h1><a href="http://localhost:8080/LunchWithFriends/index.jsp"> Lunch With Friends</a></h1>
		<form class="searchbar" action="http://localhost:8080/LunchWithFriends/SearchRestaurantDisplay">
			<input type="text" name="searchname" 
				placeholder="Search for your favorite restaurants...">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
		</form>
		<div id="icons">
			<button type="button">
				<i class="far fa-user-circle"></i>
			</button>
			<button type="button">
				<i class="far fa-bell"></i>
			</button>
			<button type="button">
				<i class="far fa-comment-dots"></i>
			</button>
		</div>
	</div>
	<div id="main">
		<h2>Matches</h2>
		<%
		if(userList == null){ %>
			<p>No users interested in this restaurant currently.</p>
		<%
		}
		else{
		for(User u : userList){
			%>
			<div class="match">
			<p><%=u.name %> also wants to go to this restaurant!</p>
			<div class="match-icons">
				<button id="no" type="button">
					<i class="fas fa-times"></i>
				</button>
				<form action="http://localhost:8080/LunchWithFriends/ChatStart.jsp">
				<button id="yes" type="button">
					<i class="fas fa-check"></i>
				</button>
				</form>
			</div>
		</div>
			<%
			}
		}
		%>
		
	</div>
</body>
</html>