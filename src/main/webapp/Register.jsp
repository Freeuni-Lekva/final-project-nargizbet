<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            document.getElementById('registerbtn').disabled = false;
        } else {
            document.getElementById('msg').style.color = 'red';
            document.getElementById('msg').innerHTML = 'Passwords don\'t match';
            document.getElementById('registerbtn').disabled = true;
        }
    }

        var check = function (){
            var password = document.getElementById('psw').value;
            var rep_password = document.getElementById('psw-repeat').value;
            if(password == '' || (password != rep_password)){
                return false;
            }
            if(document.getElementById('psw').value.length < minPassLength){
                alert("Password is too short")
                return false;
            }
            if(+document.getElementById('age').value < 18){
                alert("You must be at least 18 years old")
                return false;
            }
        }


    </script>
    <style><%@include file="/Register.css"%></style>
</head>
<body>
<div class="header">
    <div class = "center">
        <a href ="/"><img src="/Images/Logo.png" alt="Nargizbet Logo" class ="center"></a>
    </div>
</div>
<form action="/register" method="post" onsubmit="return check()">
    <div class="container">
        <h1>Register</h1>
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


        <button type="submit" class="registerbtn" id ="registerbtn" disabled>Register</button>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="Login.jsp">Sign in</a>.</p>
    </div>
</form>
</body>
</html>
