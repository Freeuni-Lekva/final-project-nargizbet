<%@ page import="User.User" %>
<style><%@include file="/Profile.css"%></style>
<style><%@include file="/UpperBar.css"%></style>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=(String)request.getAttribute("givenUsername")%></title>
</head>
<body>

<div id="header_box">
    <header id="upper_bar">
        <div id="left_corner">
            <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
        </div>

        <div class="title">
            <h1><%=(String)request.getAttribute("givenUsername")%></h1>
        </div>

    </header>
</div>

<div class="navigate-side">
    <div class="profile">
        <img src="https://www.tenforums.com/geek/gars/images/2/types/thumb_15951118880user.png" alt="" width="100" height="100">
        <div class="balance">
            <h5><%=request.getAttribute("currBal")%><%="$"%></h5>
        </div>
        <div class="note-not-friend">
            You two are not friends
        </div>
        <form action="/friendrequests" method="POST">
            <button type="submit">Send friend request!</button>
            <input type="hidden" name="Username" value = <%=(String)request.getAttribute("givenUsername")%>>
        </form>
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
