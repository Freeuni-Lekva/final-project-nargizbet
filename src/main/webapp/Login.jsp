<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <style><%@include file="/Register.css"%></style>
</head>
<body>
	<div class="center">
    	<a href="/"><img src="/Images/Logo.png" alt="Nargizbet Logo" class="center"></a>
	</div>
	
	<form action="/login" method="post">
	    <div class="container">
	        <h1>Login</h1>
	        <p><c:out value="${ErrorMessage}"/></p>
	
	        <label for="username"><b>Username</b></label>
	        <input type="text" placeholder="Enter Username" name="username" id="username" required>
	
	        <label for="psw"><b>Password</b></label>
	        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
			<span id = "msg"></span>
	
	        <button type="submit" class="registerbtn" id ="registerbtn">Login</button>
	    </div>
	    
	    <div class="container signin">
	        <p>Don't have an account? <a href="Register.jsp">Register</a></p>
	    </div>
    </form>
</body>
</html>