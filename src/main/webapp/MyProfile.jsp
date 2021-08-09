<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><%@ page import="User.User" %>
<%@ page import="Database.FriendsDAO" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="Database.StatsDAO" %>
<%@ page import="Gameplay.Games.Game" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Strangers Profile Page</title>
    <!-- Custom Css -->
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="navigate-top">
    <div class="title">
        <h1>My Profile</h1>
    </div>
</div>

<div class="navigate-side">
    <div class="profile">
<%--
        needs to be changed
--%>
        <img src="https://www.tenforums.com/geek/gars/images/2/types/thumb_15951118880user.png" alt="" width="100" height="100">
    </div>
</div>

<div class="main">
    <h2>IDENTITY</h2>
    <div class="card">
        <div class="card-body">
            <i class="fa fa-pen fa-xs edit"></i>
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
       <li class="fa fa-pen fa-xs edit"></li>
        <table>
            <tbody>
            <tr>
                <td><%="BlackJack :"%></td>
                <td><%=request.getAttribute("BJWins")%></td>
                <td><%="W"%></td>
                <td><%="Money gambled in Slots:"%></td>
                <td><%=request.getAttribute("SlotMoneyGambled")%></td>
                <td><%="$"%></td>
            </tr>
            </tbody>
        </table>
    </div>
    <h3>Friend list</h3>
    <div class="friendlist">
        <li class="fa fa-pen fa-xs edit"></li>
        <table>
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