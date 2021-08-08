<%--
  Created by IntelliJ IDEA.
  User: nargizi
  Date: 07.08.21
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<link rel = "stylesheet" href = "SearchBar.css">

    <head>
        <title> Search Results </title>
        <link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <body>
        <form class = "example" action = "SearchUsersServlet" method = "get">
            <a class = "active" href = "#home">Home</a>
            <input type = "text" placeholder = "Search.." name = "search">
            <button type = "submit" id="searchbutton"><i class="fa fa-search"></i></button>
        </form>
    </body>

</html>

