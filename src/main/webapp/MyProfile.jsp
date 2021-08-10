<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><%@ page import="User.User" %>
<%@ page import="java.util.Set" %>

<style><%@include file="/Styles/Profile.css"%></style>
<style><%@include file="/Styles/UpperBar.css"%></style>

<!DOCTYPE html>
<html lang="en">
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>
</head>
<body>

    <div id="header_box">
        <header id="upper_bar">
            <div id="left_corner">
                <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
            </div>

            <div class="title">
                <h1>My Profile</h1>
            </div>

        </header>
    </div>

<div class="navigate-side">
    <div class="profile">

        <img class="img-style" src="/displayimage?Username=<%= (String)request.getAttribute("givenUsername") %>" alt="" />
        <form class="add-image" action="/addimage" method="post" enctype="multipart/form-data">
            <input type="file" id="image" name="image" accept=".jpg, .png"/>
            <button class="image-button" type="submit">Upload </button>
        </form>
        <div class="balance">
            <h5><%=request.getAttribute("currBal")%><%="$"%></h5>
        </div>

        <div class="gamelist">
            <h2>Games Played</h2>
            <ul class="games-text">
                <li><%="BlackJack: "%><%=request.getAttribute("BJWins")%> Wins</li>
                <li><%="Money gambled in Slots: "%><%=request.getAttribute("SlotMoneyGambled")%><%="$"%></li>
            </ul>
        </div>
        <form class="add-image" action="/friendrequests" method="get">
            <button id = "user_bar">Friend Requests</button>
        </form>

        <form method="POST" action="/logout" id="Logoute">
            <button type="submit" id="Logout_buttone"> <i class="fas fa-sign-out-alt" id="Logout_icon"></i>Log Out</button>
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

    <h3>Friend list</h3>
    <div class="friendlist">
        <table class="friends">
            <tbody>
            <%
                Set<User> friends = (Set<User>)request.getAttribute("FriendsList");
            %>
            <% for(User u : friends){%>
            <tr>
                <td>
                    <a href="/profile?Username=<%= u.getUsername()%>" id="inner_friend">
                    <%=u.getFirstName()%> <%=u.getLastName()%>
                    </a>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</div>
</div>
</body>
</html>