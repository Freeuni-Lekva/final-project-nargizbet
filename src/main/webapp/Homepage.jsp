<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />
<style>
    <%@include file="/HomepageStyle.css"%>
    <%@include file="/UpperBar.css"%>
</style>

<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<html>
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
            <a href="/balance" id="bal_text">
                <div> Balance: <c:out value="${balance}"/>$ </div>
            </a>
        </div>
    </header>
</div>

<div id="game_list">
        <c:forEach items="${game_list}" var="elem">
            <div id="game_item">
                <img src="Images/<c:out value="${elem}"/>" id="game_image">
                <div id="inner_thingy"></div>
            </div>
        </c:forEach>
</div>


</body>
</html>