<%@ page import="User.User" %>
<%@ page import="Database.FriendsDAO" %>
<%@ taglib uri = "http://java.sun.com/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<style><%@include file="/Styles/Profile.css"%></style>
<style><%@include file="/Styles/UpperBar.css"%></style>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=(String)request.getAttribute("givenUsername")%></title>
    <link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">
</head>
<body>

<div id="header_box">
    <header id="upper_bar">
        <div id="left_corner">
            <a href="/homepage"> <img src="../Images/Logo.png" id="logo"> </a>
        </div>

        <div class="title">
            <h1><%=(String)request.getAttribute("givenUsername")%></h1>
        </div>

    </header>
</div>

<%
    User currentUser = (User) request.getSession().getAttribute("User");
    User givenUser = (User) request.getAttribute("givenUser");
    FriendsDAO FDAO = (FriendsDAO) request.getServletContext().getAttribute("FriendsDAO");
    if (FDAO.isFriendRequest(currentUser, givenUser)) {
        request.setAttribute("message", "Friend Request Sent");
    } else {
        request.setAttribute("message", "Send Friend Request");
        if (FDAO.isFriendRequest(givenUser, currentUser)) request.setAttribute("message", "Accept Friend Request");
    }
    request.setAttribute("reqMessage", "Friend Request Sent");
%>

<div class="navigate-side">
    <div class="profile">
        <img src="/displayimage?Username=<%= (String)request.getAttribute("givenUsername") %>" alt="" class="img-style"/>

        <div class="note-not-friend">
            You two are not friends
        </div>
        <form action="/friendrequests" method="POST">
            <button class="friendreq-button" type="submit"
                    <c:if test = "${message eq reqMessage}"> disabled </c:if>>
                <c:out value = "${message}"/>
            </button>
            <input type="hidden" name="Username" value = <%=(String)request.getAttribute("givenUsername")%>>
        </form>

    </div>
</div>

<div class="main">
    <h2>BIO</h2>
    <div class="card">
        <div class="card-body">
            <table>
                <tbody>
                <tr>
                    <td id="name"><%=request.getAttribute("first_name")%> <%=request.getAttribute("last_name")%></td>
                </tr>
                <tr>
                    <td class="info">Member Since  <%=request.getAttribute("MemberSince")%></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
