<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Login</title>
    
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
	<style><%@include file="/HomepageStyle.css"%></style>
	<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <style><%@include file="/Register.css"%></style>
</head>
<body>
	<div id="header_box">
	    <header id="upper_bar">
	        <div id="left_corner">
	            <a href=""> <img src="Images/Logo.png" id="logo"> </a>
	        </div>
	    </header>
	</div>
	
	<form action="/login" method="post">
	    <div class="container">
	        <h1>Login</h1>
	        <p><c:out value="${ErrorMessage}"/></p>
	
	        <label for="username"><b>Username</b></label>
	        <input type="text" placeholder="Enter Username" name="username" id="username" required>
	
	        <label for="psw"><b>Password</b></label>
	        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
	
	        <button type="submit" class="registerbtn" id ="registerbtn">Login</button>
	    </div>
	    
	    <div class="container signin">
	        <p>Don't have an account? <a href="Register.jsp">Register</a></p>
	    </div>
    </form>
</body>
</html>