<%@ page import="User.User" %>
<%@ page import="Database.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>

<html>

    <style>
        <%@include file="/Styles/FoundUsersStyle.css"%>
        <%@include file="/Styles/UpperBar.css"%>
    </style>


    <%
        String username = request.getParameter("username");
        UserDAO UDAO = new UserDAO();
        User currentUser = (User)request.getSession().getAttribute("User");
        Double balance = currentUser.getBalance();
        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
        request.setAttribute("username", username);
        request.setAttribute("balance", balance);
        request.setAttribute("fullName", fullName);
        request.setAttribute("currUser", currentUser.getUsername());
    %>

    <head>
        <title> Search Results </title>
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
                    <a href="/profile?Username=<c:out value="${currUser}"/>" id="bar_text">
                        <c:out value="${fullName}"/>
                    </a>
                </div>
                <form method="POST" action="/logout" id="Logout">
                    <i class="fas fa-sign-out-alt" id="Logout_icon"></i>
                    <button type="submit" id="Logout_button"> Log Out</button>
                </form>
                <a href="/balance" id="bal_text">
                    <i class="far fa-money-bill-alt"></i>
                    <div> Balance: <c:out value="${balance}"/>$ </div>
                </a>
            </div>
        </header>
    </div>

        <h1> Search Results: </h1>
        <ul id="user_list">
            <c:forEach items = "${users}" var = "user">
                <li class = flex-item>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>">
                        <img src="/displayimage?Username=<c:out value = "${user.username}"/>" alt="" width="100" height="100"
                        id="prof_img"/>
                    </a>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>" id = "text-color">
                        <c:out value = "${user.username}"/>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </body>

</html>