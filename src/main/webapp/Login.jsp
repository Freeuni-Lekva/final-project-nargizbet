<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style><%@include file="/Register.css"%></style>
</head>
<body>
	<div class = "center">
    	<a href =""><img src="/Images/Logo.png" alt="Nargizbet Logo" class="center"></a>
	</div>
	
	<form action="/register" method="post" onsubmit="return check()">
	    <div class="container">
	        <h1>Login</h1>
	
	        <label for="username"><b>Username</b></label>
	        <input type="text" placeholder="Enter Username" name="username" id="username" required>
	
	        <label for="psw"><b>Password</b></label>
	        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
			<span id = "msg"></span>
	
	        <button type="submit" class="loginbtn" id ="loginbtn" disabled>Login</button>
	    </div>
	    
	    <div class="container register">
	        <p>Don't have an account? <a href="Register.jsp">Register</a></p>
	    </div>
    </form>
</body>
</html>