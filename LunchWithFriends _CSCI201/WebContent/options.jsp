<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
	String restaurantName = request.getParameter("currRestaurant");
%>
<link href="style.css" rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css2?family=Lato:wght@700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Commissioner:wght@300&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<title>Choose an Option</title>

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
	width: 80%;
	position: relative;
	margin: 0 auto;
	top: 150px;
	z-index: -1;
}

h2 {
	font-size: 2em;
	font-family: 'Lato', sans-serif;
	text-align: center;
}

.img-option {
	height: 400px;
	width: 400px;
	border-radius: 50%;
	margin: 50px;
	margin-top: 20px;
	margin-bottom: 0px;
}

.container {
	width: 50%;
	float: left;
}

.container button {
	background: white;
	border: none;
	cursor: pointer;
}

</style>
</head>
<body>
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
		<form action="http://localhost:8080/LunchWithFriends/chatStart.jsp">
			<div class="container">
				<button type="submit" name="currRestaurant" value=<%=restaurantName%>>
					<img class="img-option" src="https://i.imgur.com/vnyXkBl.png"
						alt="Group Illustration">
					<h2>Group</h2>
				</button>
			</div>
		</form>
			<div class="container">
			<form action="http://localhost:8080/LunchWithFriends/DisplayInterestedUsers">
				<button type="submit" name="currRestaurant" value="<%=restaurantName%>">
					<img class="img-option" src="https://i.imgur.com/9Mdnoz3.png"
						alt="Single Illustration">
					<h2>Single</h2>
					<p> <%=restaurantName%> </p>
				</button>
			</form>
			</div>
	</div>
</body>
</html>