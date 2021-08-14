<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
<style>
    <%@include file="/Styles/HomepageStyle.css"%>
    <%@include file="/Styles/UpperBar.css"%>
</style>

<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<html>

<head>
    <title> Homepage </title>
    <link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">
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

<div id="game_list">
        <c:forEach items="${game_list}" var="elem">
            <c:choose>
                <c:when test = "${elem.p1 eq \"Slots.PNG\"}">
                    <a href="/Slots.jsp?gameName=<c:out value="${elem.p2}"/>" id="game_item">
                </c:when>
                <c:otherwise>
                    <a href="/ViewTables.jsp?gameName=<c:out value="${elem.p2}"/>" id="game_item">
                </c:otherwise>
            </c:choose>
                        <img src="Images/<c:out value="${elem.p1}"/>" id="game_image">
                        <div id="inner_thingy"></div>
                    </a>
        </c:forEach>
</div>
</body>
</html>