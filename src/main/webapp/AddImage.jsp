<%@ page import="User.User" %>
<%@ page import="Database.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: nargizi
  Date: 09.08.21
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


    <img src="/displayimage?Username=<%= ((User)request.getSession().getAttribute("User")).getUsername()%>" />

<form action="/addimage" method="post" enctype="multipart/form-data">
    <input type="file" id="image" name="image" accept=".jpg, .png"/>
    <button type="submit">upload </button>
</form>

</body>
</html>
