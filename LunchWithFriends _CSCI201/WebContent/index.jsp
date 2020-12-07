<%@ page import= "CSCI201_LunchWithFriends.User" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<meta name="google-signin-client_id" content="1086689429442-1v6o19vn962eclqnv5oirtrp5r5g7d33.apps.googleusercontent.com">
	<link href="style.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css2?family=Lato:wght@700&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Commissioner:wght@300&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
	
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	
	<title>Lunch With Friends</title>
	
	<style>
		h1 {
			text-align: center;
			color: white;
			font-size: 6em;
		}
		body {
			background-image: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)),
			url('https://mir-s3-cdn-cf.behance.net/project_modules/1400_opt_1/dfee8745499369.583332846bf73.jpeg');
			background-repeat:no-repeat;
			background-position: center;
			background-size: cover;
			padding: 80px;
			padding-top: 110px;
		}
		html {
			height: 100%
		}
		.g-signin2 {
			position: fixed;
			top: 25px;
			right: 25px;
		}
		.searchbar {
			display: flex;
			justify-content: center;
		}
		p {
			color: white;
			text-align: center;
		}
		#description {
			position: fixed;
			left: 0;
			right: 0;
			bottom: 100px;
		}
	</style>
</head>
<body>
	<h1>Lunch With Friends</h1>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
	<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
	
	<script>
		
	       function onSignIn(googleUser) {
		        // Useful data for your client-side scripts:
		        var profile = googleUser.getBasicProfile();
		        console.log("ID: " + profile.getId()); 
		        console.log('Full Name: ' + profile.getName());
		        console.log('Given Name: ' + profile.getGivenName());
		        console.log('Family Name: ' + profile.getFamilyName());
		        console.log("Image URL: " + profile.getImageUrl());
		        console.log("Email: " + profile.getEmail());

		        if(profile.getName() != null) {
		        var xhr = new XMLHttpRequest();
		        xhr.open('POST', 'http://localhost:8080/LunchWithFriends/JDBCUser_Access');
		        
		        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		        xhr.onload = function() {
		          console.log('Signed in as: ' + xhr.responseText);
		        };
		        
		        xhr.send("USERname=" + profile.getName() + "," + profile.getEmail() + "," + profile.getId());
		        
		        }
		        
	      }
    </script>
    	
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<form class="searchbar" action="http://localhost:8080/LunchWithFriends/SearchRestaurantDisplay"> <!-- replace this with a link to restaurants page -->
		<input type="text" name="searchname" placeholder="Search for your favorite restaurants...">
		<button type="submit"><i class="fa fa-search"></i></button>
		
	</form>
	
	<p id="description">A CSCI-201 project by Amanda Zhang, Amir Hegazy, Brenna Chen, Jaemyung Choi, and Soumika Guduru.</p>
</body>
</html>