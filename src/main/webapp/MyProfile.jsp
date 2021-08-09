<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><%@ page import="User.User" %>
<%@ page import="java.util.Set" %>

<style><%@include file="/Profile.css"%></style>
<style><%@include file="/HomepageStyle.css"%></style>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>
</head>
<body>

<div class="navigate-top">
    <div class="title">
        <h1>My Profile</h1>
    </div>
    <div id="left_corner">
        <a href="/"> <img src="Images/Logo.png" id="logo"> </a>
    </div>

</div>

<div class="navigate-side">
    <div class="profile">
<%--
        needs to be changed
--%>
        <img src="https://www.tenforums.com/geek/gars/images/2/types/thumb_15951118880user.png" alt="" width="100" height="100">
    <div class="balance">
        <h5><%=request.getAttribute("currBal")%><%="$"%></h5>
    </div>
    </div>
</div>

<div class="main">
    <h2>BIO</h2>
    <div class="card">
        <div class="card-body">
            <i class="info"></i>
            <table>
                <tbody>
                <tr>
                    <td>Name</td>
                    <td>:</td>
                    <td><%=request.getAttribute("first_name")%></td>
                </tr>
                <tr>
                    <td>Lastname</td>
                    <td>:</td>
                    <td><%=request.getAttribute("last_name")%></td>
                </tr>
                <tr>
                    <td>Member Since</td>
                    <td><%=request.getAttribute("MemberSince")%></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <h2>Games Played</h2>
    <div class="gamelist">
       <ul>
           <li><%="BlackJack :"%><%=request.getAttribute("BJWins")%><%="W"%></li>
           <li><%="Money gambled in Slots:"%><%=request.getAttribute("SlotMoneyGambled")%><%="$"%></li>
       </ul>
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
                <td><%=u.getFirstName()%></td>
                <td><%=u.getLastName()%></td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</div>
</div>
</body>
</html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>