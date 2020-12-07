<%@ page import= "CSCI201_LunchWithFriends.User" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@ page import="CSCI201_LunchWithFriends.Business" %>

	
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">

	<link href="style.css" rel="stylesheet" type="text/css">
	<link 
	href="https://fonts.googleapis.com/css2?family=Lato:wght@700&display=swap" rel="stylesheet">
	<link
	href="https://fonts.googleapis.com/css2?family=Commissioner:wght@300&display=swap"
	rel="stylesheet">
	<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">

	<title>Search Results</title>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0WY4DfBXb0unVqaLndM4t1GjzwqwOsyU&callback=initMap&libraries=&v=weekly"
		defer></script>

	<style>
		#header {
			height: 15%;
			background-color: #f7b819;
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			padding: 20px;
			z-index: 2;
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

		#container {
			position: relative;
			top: 100px;
			left: 50%;
		}
		/* Set the size of the div element that contains the map */
		#map {
			height: 550px;
			/* The height is 400 pixels */
			width: 50%;
			/* The width is the width of the web page */
		}

		#main {
			width: 50%;
			position: relative;
			top: 150px;
			left: 150px;
		}
		#main a {
			color: black;
		}
		
		#main button {
			background: none;
			border: none;
			text-align: left;
			cursor: pointer;
		}
		
	</style>
	<script src="display_map.js"></script>
	<script src="display_restaurants.js"></script>
</head>
<body>
<%
		ArrayList<Business> businessList = (ArrayList<Business>)request.getAttribute("businessList");
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
	
	<% User user = (User) session.getAttribute("currUser");
	if(user == null) {
		%> <div id="main">
		<%
		for(Business b : businessList){
			%>
				<p><%= b.getName() %></p>
				<p>Rating: <%= b.getRating() %> / 5.0</p>
				<p><%= b.getAddress() %></p>
				<hr>
			<%
		}
		%>

	</div> <%
	} else {
		%> <div id="main">
		<%
		for(Business b : businessList){
			%>
			<form action="http://localhost:8080/LunchWithFriends/options.jsp">
				<button type="submit" name="currRestaurant" value="<%=b.getName()%>">
				<p><%= b.getName() %></p>
				<p>Rating: <%= b.getRating() %> / 5.0</p>
				<p><%= b.getAddress() %></p>
				</button>
				<hr>
			</form>
			<%
		}
		%>

	</div> <%
	}
	%>

	<div id="container">
		<div id="map">
			<!-- map goes here -->
		</div>
	</div>
</body>
</html>