<%@ page import="User.User" %>
<%@ page import="Database.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>

<html>

    <style>
        <%@include file="/FoundUsersStyle.css"%>
        <%@include file="/UpperBar.css"%>
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
        <ul id="upper_bar">
            <li class = flex-item>
                <a href = "/">
                    <img src = "Images/Logo.png"
                         width = 125 height = 125 id="logo">
                </a>
            </li>
            <li class = flex-item>
                <form action = "searchusers" method = "get">
                    <input type = "text" name = "username" id = "search_bar" placeholder="Search User"/>
                    <input type = "submit" value = "Search" id = "search_button"/>
                </form>
            </li>

            <li class = flex-item id = "right_corner">
                    <div id="user_bar">
                        <i class='fas fa-user-alt' style='font-size:20px;color:white'></i>
                        <a href="/profile?Username=<c:out value="${currUser}"/>" id="bar_text">
                            <c:out value="${fullName}"/>
                        </a>
                    </div>
                    <a href="/balance" id="bal_text">
                        <div> Balance: <c:out value="${balance}"/>$ </div>
                    </a>
            </li>
        </ul>
    </div>

        <h1> Search Results: </h1>
        <ul id="user_list">
            <c:forEach items = "${users}" var = "user">
                <li class = flex-item>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>">
                        <img src = "Images/Logo.png" width = 100 height = 100>
                    </a>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>" id = "text-color">
                        <c:out value = "${user.username}"/>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </body>

</html>