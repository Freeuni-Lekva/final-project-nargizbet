<%@ page import="User.User" %>
<%@ page import="java.util.Set" %>
<style><%@include file="/Profile.css"%></style>
<style><%@include file="/HomepageStyle.css"%></style>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=(String)request.getAttribute("givenUsername")%></title>
    <!-- Custom Css -->
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="navigate-top">
    <div class="title">
        <h1><%=(String)request.getAttribute("givenUsername")%></h1>
    </div>
</div>

<div class="navigate-side">
    <div class="profile">
<%--
        has to change
--%>
        <img src="https://www.tenforums.com/geek/gars/images/2/types/thumb_15951118880user.png" alt="" width="100" height="100">
        <div class="note-not-friend">
            You two are friends
        </div>
        <form action="/deletefriend" method="POST">
            <button type="submit">Send friend request!</button>
            <input type="hidden" name="Username" value = <%=(String)request.getAttribute("givenUsername")%>>
        </form>
    </div>
</div>

<div class="main">
    <h1>BIO</h1>
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
