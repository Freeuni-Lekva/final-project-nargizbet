<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>

<head>
    <title>Create Account</title>
    <style></style>
    <script>
        const minPassLength = 8;
        var checkPassword = function() {
        if (document.getElementById('psw').value ==
            document.getElementById('psw-repeat').value) {
            document.getElementById('msg').innerHTML = '';
        } else {
            document.getElementById('msg').style.color = 'red';
            document.getElementById('msg').innerHTML = 'Passwords don\'t match';
        }
    }

        var check = function (){
            var password = document.getElementById('psw').value;
            var rep_password = document.getElementById('psw-repeat').value;
            if(password != rep_password){
                return false;
            }
            if(+document.getElementById('age').value < 18){
                alert("You must be at least 18 years old")
                return false;
            }
            if(document.getElementById('psw').value.length < minPassLength){
                alert("Password must contain at least " + minPassLength + " characters");
                return false;
            }
        }


    </script>
    <style><%@include file="/Styles/Register.css"%></style>
    <style><%@include file="/Styles/UpperBar.css"%></style>
</head>
<body>
<body>
<div id="header_box">
    <header id="upper_bar">
        <div id="left_corner">
            <a href=""> <img src="Images/Logo.png" id="logo"> </a>
        </div>
    </header>
</div>
<form action="/register" method="post" onsubmit="return check()">
    <div class="container">
        <h1>Register</h1>

        <h3><c:out value="${ErrorMessage}"/></h3>

        <p>Please fill in this form to create an account.</p>


        <label for="firstName"><b>First Name</b></label>
        <input type="text" placeholder="Enter First Name" name="firstName" id="firstName" required>

        <label for="lastName"><b>Last Name</b></label>
        <input type="text" placeholder="Enter Last Name" name="lastName" id="lastName" required>

        <label for="username"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="username" id="username" required>

        <label for="age"><b>Age</b></label>
        <input type="number" placeholder="Enter Age" name="age" id="age" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" onkeyup="checkPassword()" placeholder="Enter Password" name="psw" id="psw" required>

        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" onkeyup="checkPassword()" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>
        <span id = "msg"></span>


        <button type="submit" class="registerbtn" id ="registerbtn">Register</button>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="Login.jsp">Sign in</a>.</p>
    </div>
</form>
</body>
</html>
