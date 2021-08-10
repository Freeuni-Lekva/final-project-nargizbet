<%@ page import="java.util.List" %>
<%@ page import="Gameplay.Room.Table" %>
<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>

<%
    String gameName = (String) request.getParameter("gameName");
    List<Table> tables = (List) request.getServletContext().getAttribute(gameName + "Tables");
    request.setAttribute("tables", tables);
    User currentUser = (User) request.getSession().getAttribute("User");
    request.setAttribute("gameName", gameName);
    request.setAttribute("username", currentUser.getUsername());
    request.setAttribute("first_name", currentUser.getFirstName());
    request.setAttribute("last_name", currentUser.getLastName());
    request.setAttribute("balance", currentUser.getBalance());
%>

<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />

<style>
    <%@include file="/Styles/HomepageStyle.css"%>
    <%@include file="/Styles/UpperBar.css"%>
    <%@include file="/Styles/ViewTablesStyle.css"%>
</style>

<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>

<html>
    <head>
        <title> <%= request.getParameter("gameName") + " Tables" %> </title>
    </head>

    <body>
    <div id="header_box">
        <header id="upper_bar">
            <div id="left_corner">
                <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
            </div>
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

    <div id = dist> </div>

    <h2 id = title-format> <%= request.getParameter("gameName") + " Tables" %> </h2>

    <%
        int tableNum = 1;
        int tableId = 0;
        request.setAttribute("tableNum", tableNum);
        request.setAttribute("tableId", tableId);
    %>

    <ul class = row-flex-container>
        <c:forEach items="${tables}" var="table">
            <div id = table-format>
                Table <c:out value = "${tableNum}"/>
                <br>
                <c:out value = "${table.currentCapacity}"/> / <c:out value = "${table.maxCapacity}"/>
                <%
                    request.setAttribute("tableNum", ++tableNum);
                    request.setAttribute("tableId", ++tableId);
                %>
                <form action = "/jointable?tableId=<c:out value = "${tableId}"/>&gameName=<c:out value = "${gameName}"/>"
                      method = "post">
                    <input type = "text" placeholder = "Amount" required>
                    <button type="submit"> Join </button>
                </form>
            </div>
        </c:forEach>
    </ul>

    </body>
</html>