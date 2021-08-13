<%--
  Created by IntelliJ IDEA.
  User: nargizi
  Date: 08.08.21
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>Friend Requests</title>
    <style><%@include file="/Styles/FriendRequests.css"%></style>
    <style><%@include file="/Styles/UpperBar.css"%></style>
</head>
<body>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>


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
                <i class="far fa-money-bill-alt"></i>
                <div> Balance: <c:out value="${balance}"/>$ </div>
            </a>
        </div>
    </header>
</div>


    <div id="requestList">
        <h1 id="headText">Friend Requests: </h1>
        <ul>
            <c:forEach var="u" items="${received}">
                <li><div class="container">
                    <a href ="/profile?Username=<c:out value = "${u.username}"/>">
                     <p class="user"><c:out value = "${u.username}"/></p>
                    </a>
                    <div class="buttons">
                        <form action="/requestprocess" method="post">
                            <button type="submit" class="confirmbtn" id ="confirmbtn">Confirm</button>
                            <input type="hidden" name="Username" value=<c:out value = "${u.username}"/> />
                            <input type="hidden" name="Type" value="accept">
                        </form>
                        <form action="/requestprocess" method="post">
                            <button type="submit" class="deletebtn" id ="deletebtn">Delete Request</button>
                            <input type="hidden" name="Username" value=<c:out value = "${u.username}"/> />
                            <input type="hidden" name="Type" value="delete">
                        </form>
                    </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
