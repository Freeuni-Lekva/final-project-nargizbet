<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Deposit Money</title>
	<link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">
	
	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
	<style>
		<%@include file="/Styles/Register.css"%>
		<%@include file="/Styles/UpperBar.css"%>
	</style>
	<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
	<script src="Javascript/AmountCheckScript.js"></script>
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
	                <a href="/profile?Username=<c:out value="${username}"/>" id="bar_text"> <c:out value="${first_name}"/> <c:out value="${last_name}"/> </a>
	            </div>
				<form method="POST" action="/logout" id="Logout">
					<i class="fas fa-sign-out-alt" id="Logout_icon"></i>
					<button type="submit" id="Logout_button"> Log Out</button>
				</form>
	            <div id="bal_text">
					<i class="far fa-money-bill-alt"></i>
					Balance: <c:out value="${balance}"/>$
				</div>
	        </div>
	    </header>
	</div>

	<form action="/balance" method="post" onsubmit="return checkAmount()">
	    <div class="container">
	        <h1>Deposit Money to Your Balance</h1>
	        <label for="amount"><b>Amount</b></label>
	        <input type="number" placeholder="Enter Amount" name="amount" id="amount" step="0.01" required>
	        <button type="submit" class="registerbtn" id ="registerbtn">Deposit</button>
	    </div>
    </form>

</body>
</html>
