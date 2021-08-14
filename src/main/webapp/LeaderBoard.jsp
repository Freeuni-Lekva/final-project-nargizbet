<%@ page import="User.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>LeaderBoard</title>
	<link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">

	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
	<style>
		<%@include file="/Styles/UpperBar.css"%>
		<%@include file="/Styles/LeaderboardStyle.css" %>
	</style>
	<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
</head>

<body>
	<div id="header_box">
	    <header id="upper_bar">
	        <div id="left_corner">
	            <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
	        </div>
	
	        <form method="get" action="/searchusers" id="middle_part">
	            <div>
	            <input type="text" placeholder="Search User" name="username" id="search_bar">
	            <input type="submit" value="Search" id="search_button"/>
	            </div>
	        </form>
	
	        <div id="right_corner">
	            <div id="user_bar">
	                <i class='fas fa-user-alt' style='font-size:20px;color:white'></i>
	                <a href="/profile?Username=<c:out value="${user.username}"/>" id="bar_text">
	                    <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>
	                </a>
	            </div>
				<form method="POST" action="/logout" id="Logout">
					<i class="fas fa-sign-out-alt" id="Logout_icon"></i>
					<button type="submit" id="Logout_button"> Log Out</button>
				</form>
	            <a href="/balance" id="bal_text">
					<i class="far fa-money-bill-alt"></i>
                	Balance: <c:out value="${user.balance}"/>$ 
	            </a>
	        </div>
	    </header>
	</div>
	
	<div id="list_box">
		
		<label for="list" id="leaderboard_label"><b><c:out value="${gameName}"/> Leaderboard</b></label>
		<div name="list" class="list" id="list">
			<%
				int place = 1;
				request.setAttribute("currNum", place);
			%>
        	<c:forEach items="${leaderboard}" var="elem">
        		<li class="list_item" id="list_item">
					<div class="place"><c:out value="${currNum}"/>.</div>
        			<div class="winner_username"><c:out value="${elem.key.username}"/></div>
        			<div class="winner_name">
        				<c:out value="${elem.key.firstName} ${elem.key.lastName}"/>
       				</div>
       				<div class="amount_won"><c:out value="${elem.value}"/></div>
        		</li>
				<%
					request.setAttribute("currNum", ++place);
				%>
        	</c:forEach>
			<div class="dot">.</div>
			<div class="dot">.</div>
			<div class="dot">.</div>
		</div>
        	<li value="<c:out value="${userWins}"/>" class="list_user" id="user_list">
				<div class="place"><c:out value="${userPlace}"/>.</div>
        		<div class="winner_username"><c:out value="${user.username}"/></div>
       			<div class="winner_name">
       				<c:out value="${user.firstName} ${user.lastName}"/>
    			</div>
      			<div class="amount_won"><c:out value="${userWins}"/></div>
        	</li>
	</div>
	
</body>
</html>