<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Deposit Money</title>
	
	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
	<style><%@include file="/HomepageStyle.css"%></style>
	<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <style><%@include file="/Register.css"%></style>
</head>
<body>

	<div id="header_box">
	    <header id="upper_bar">
	        <div id="left_corner">
	            <a href="/"> <img src="Images/Logo.png" id="logo"> </a>
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
	                <a href="/profile?username=<c:out value="${username}"/>" id="bar_text"> <c:out value="${first_name}"/> <c:out value="${last_name}"/> </a>
	            </div>
	            <div id="bal_text"> Balance: <c:out value="${balance}"/>$ </div>
	        </div>
	    </header>
	</div>

	<form action="/balance" method="post">
	    <div class="container">
	        <h1>Deposit Money to Your Balance</h1>
	
	        <label for="username"><b>Amount</b></label>
	        <input type="number" placeholder="Enter Amount" name="amount" id="amount" required>
	
	        <button type="submit" class="registerbtn" id ="registerbtn">Deposit</button>
	    </div>
    </form>

</body>
</html>