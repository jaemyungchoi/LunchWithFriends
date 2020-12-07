<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
	String userID = request.getParameter("userID_");
	String room = request.getParameter("room_");
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
<title>Chat Client</title>
<script>
	var getp1 = "${userID}";
	var p1 = String(getp1);
	var getp2 = "${room}";
	var p2 = String(getp2);

	var socket = new WebSocket(
			"ws://localhost:8080/LunchWithFriends/chatroomServer" + "/" + p1
					+ "/" + p2);

	socket.onmessage = function processMessage(recievedMessage) {
		var mdata = JSON.parse(recievedMessage.data);
		if (mdata.msg != null)
			msgTextArea.value += mdata.msg + "\n";
	}

	function send() {
		socket.send(msgText.value);
		msgText.value = "";
	}
</script>
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
	width: 40%;
	position: relative;
	top: 180px;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
	border: 2px solid #d4d4d4;
	border-radius: 15px;
	padding-bottom: 30px;
}

h2 {
	font-size: 2em;
	font-family: 'Lato', sans-serif;
	text-align: center;
}

#main input[type=text] {
	border: 2px solid #d4d4d4;
	border-radius: 15px;
	margin: 10px;
	height: 20px;
}

p {
	margin: 0px;
}

#main button {
	color: #f7b819;
	font-size: 1.5em;
	background: none;
	border: none;
	cursor: pointer;
}

#msgTextArea {
	border: 2px solid #d4d4d4;
	border-radius: 15px;
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
		<h2>Messaging</h2>
		<p>UserID: <%=userID%></p>
		<p>Chat Room: <%=room%></p>
		<br /> 
		<textarea id="msgTextArea" readonly="readonly" rows="10" cols="45"></textarea>
		<br /> 
		<input type="text" id="msgText" size="50" />
		<button type="button" value="Send" onclick="send();">
			<i class="fas fa-arrow-circle-right"></i>
		</button>
	</div>
</body>
</html>