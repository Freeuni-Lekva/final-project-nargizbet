<%@ page import="java.util.List" %>
<%@ page import="Gameplay.Room.Table" %>
<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>

<%
    String gameName = request.getParameter("gameName");
    if(gameName.equals("Slots")) request.getRequestDispatcher("/Slots.jsp").forward(request, response);
    List<Table> tables = (List) request.getServletContext().getAttribute(gameName + "Tables");
    request.setAttribute("tables", tables);
    User currentUser = (User) request.getSession().getAttribute("User");
    request.setAttribute("gameName", gameName);
    request.setAttribute("username", currentUser.getUsername());
    request.setAttribute("first_name", currentUser.getFirstName());
    request.setAttribute("last_name", currentUser.getLastName());
    request.setAttribute("balance", currentUser.getBalance());

    String message = (String) request.getAttribute("message");
    request.setAttribute("message", message);
%>

<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Dancing+Script" />

<style>
    <%@include file="/Styles/HomepageStyle.css"%>
    <%@include file="/Styles/UpperBar.css"%>
    <%@include file="/Styles/ViewTablesStyle.css"%>
</style>

<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<script src="Javascript/AmountCheckScript.js"></script>

<html>
    <head>
        <title> <%= request.getParameter("gameName") + " Tables" %> </title>
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

                    <a href="/leaderboard?gamename=<c:out value = "${gameName}"/>" id="bal_text1">
                        <i class="fas fa-trophy"></i>
                        Leaderboard
                    </a>

                </div>
            </header>
        </div>

        <div id = dist> </div>

        <h2 id = title-format> <c:out value = "${gameName}"/> Tables </h2>

        <c:if test = "${not empty message}">
            <h3 id = message-format> <c:out value = "${message}"/> </h3>
        </c:if>

        <%
            int tableNum = 1;
            int tableId = -1;
            request.setAttribute("tableNum", tableNum);
            request.setAttribute("tableId", tableId);
        %>

        <ul class = container>
            <c:forEach items="${tables}" var="table">
                <div id = table-format>
                    Table <c:out value = "${tableNum}"/>
                    <br>
                    <c:out value = "${table.currentCapacity}"/> / <c:out value = "${table.maxCapacity}"/>
                    <%
                        request.setAttribute("tableNum", ++tableNum);
                        request.setAttribute("tableId", ++tableId);
                    %>
                    <form action = "/jointable"
                          method = "post" onsubmit="return checkAmountTables(<c:out value = "${tableId}"/>)">

                        <input type = "number" placeholder = "Amount" name = "amount" id="amount<c:out value = "${tableId}"/>" required max="999999999">
                        <input type = hidden name = tableId value = <c:out value = "${tableId}"/> >
                        <input type = hidden name = gameName value = <c:out value = "${gameName}"/> >
                        <button type="submit"> Join </button>
                    </form>
                </div>
            </c:forEach>

            <div id = add-table-format>
                <form action = "/addtable" method = "post">
                    <input type = hidden name = gameName value = <c:out value = "${gameName}"/> >
                    <button id = add-table-button-format type="submit"> Create Table </button>
                </form>
            </div>

        </ul>

    </body>
</html>