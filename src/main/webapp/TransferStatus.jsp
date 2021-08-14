<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>

<html>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <style>
        <%@include file="/Styles/FoundUsersStyle.css"%>
        <%@include file="/Styles/UpperBar.css"%>
    </style>

    <%
        User currentUser = (User)request.getSession().getAttribute("User");
        request.setAttribute("username", currentUser.getUsername());
        request.setAttribute("balance", currentUser.getBalance());
    %>

    <head>
        <title> <%= request.getAttribute("status") %> </title>
        <link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">
    </head>

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
                    <a href="/profile?Username=<c:out value="${username}"/>" id="bar_text">
                        <c:out value="${first_name}"/> <c:out value="${last_name}"/>
                    </a>
                </div>
                <form method="POST" action="/logout" id="Logout">
                    <i class="fas fa-sign-out-alt" id="Logout_icon"></i>
                    <button type="submit" id="Logout_button"> Log Out</button>
                </form>

                <a href="/balance" id="bal_text">
                    <div> Balance: <c:out value="${balance}"/>$ </div>
                </a>

            </div>
        </header>
    </div>

    <div id="message_card">
        <h1 id = transaction-status-format> <%= request.getAttribute("status") %> </h1>
        <h2 id = transaction-message-format> <%= request.getAttribute("message") %> </h2>
        <br> <br>
        <form action = "/profile" method = "get">
            <div id = go-back-button-center>
                <input class = go-back-button-format type = "submit" value = "Go Back"/>
                <input type="hidden" name="Username" value="<c:out value="${givenUsername}"/>">
            </div>
        </form>
    </div>


    </body>

</html>