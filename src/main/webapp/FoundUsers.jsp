<%@ page import="User.User" %>
<%@ page import="Database.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>

<html>

    <style><%@include file="/FoundUsersStyle.css"%></style>

    <%
        String username = request.getParameter("username");
        UserDAO UDAO = new UserDAO();
        User currentUser = (User)request.getSession().getAttribute("User");
        Double balance = currentUser.getBalance();
        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
        request.setAttribute("username", username);
        request.setAttribute("balance", balance);
        request.setAttribute("fullName", fullName);
    %>

    <head>
        <title> Search Results </title>
    </head>

    <body id = background-color>
        <ul class = "row-flex-container">
            <li class = flex-item>
                <a href = "/">
                    <img src = "images/Logo.png"
                         width = 125 height = 125>
                </a>
            </li>
            <li class = flex-item>
                <form action = "searchusers" method = "get">
                    <input type = "text" name = "username" id = "text-box-format"/>
                    <input type = "submit" value = "Search" id = "search-button-format"/>
                </form>
            </li>
            <li class = flex-item id = "user-status-format">
                <c:out value = "${fullName}"/>
                <br>
                Balance: <c:out value = "${balance}"/>
            </li>
        </ul>

        <h1 id = "text-color"> Search Results: </h1>
        <ul class = "row-flex-container-for-users">
            <c:forEach items = "${users}" var = "user">
                <li class = flex-item>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>">
                        <img src = "images/Logo.png" width = 75 height = 75>
                    </a>
                    <a href = "/profile?Username=<c:out value = "${user.username}"/>" id = "text-color">
                        <c:out value = "${user.username}"/>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </body>

</html>