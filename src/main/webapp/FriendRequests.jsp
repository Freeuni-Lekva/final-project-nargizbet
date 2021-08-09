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
    <style><%@include file="/FriendRequests.css"%></style>
</head>
<body>
    <div id="header">
        <div class = "left">
            <a href ="/"><img src="/Images/Logo.png" alt="Nargizbet Logo" class ="center"></a>
            <h1 id="headText">Friend Requests: </h1>
        </div>
    </div>
    <div id="requestList">
        <ul>
            <c:forEach var="u" items="${received}">
                <li><div class="container">
                    <a href= /profile?username= <c:out value = "${u.username}"/>
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
