<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>

<html>

    <style><%@include file="/Styles/FoundUsersStyle.css"%></style>

    <%
        User currentUser = (User)request.getSession().getAttribute("User");
        request.setAttribute("username", currentUser.getUsername());
        request.setAttribute("balance", currentUser.getBalance());
    %>

    <head>
        <title> <%= request.getAttribute("status") %> </title>
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
                <c:out value = "${username}"/>
                <br>
                Balance: <c:out value = "${balance}"/>
            </li>
        </ul>

        <h1 id = transaction-status-format> <%= request.getAttribute("status") %> </h1>
        <h2 id = transaction-message-format> <%= request.getAttribute("message") %> </h2>
        <br> <br>
        <form action = "profile?Username=<%=(request.getParameter("givenUsername"))%>" method = "get">
            <div id = go-back-button-center>
                <input class = go-back-button-format type = "submit" value = "Go Back"/>
            </div>
        </form>

    </body>

</html>