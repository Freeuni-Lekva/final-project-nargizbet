<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>LeaderBoard</title>
	
	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
	<style><%@include file="/HomepageStyle.css"%></style>
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
	            <a href="/balance" id="bal_text">
                	Balance: <c:out value="${user.balance}"/>$ 
	            </a>
	        </div>
	    </header>
	</div>
	
	<div id="list_box">
		<ol class="list" id="list">
        	<c:forEach items="${leaderboard}" var="elem">
        		<li class="list_item" id="list_item">
        			<div id="winner_username"><c:out value="${elem.key.username}"/></div>
        			<div id="winner_name">
        				<c:out value="${elem.key.firstName} ${elem.key.lastName}"/>
       				</div>
       				<div id="amount_won"><c:out value="${elem.value}"/></div>
        		</li>
        	</c:forEach>
		</ol>
	</div>
	
</body>
</html>