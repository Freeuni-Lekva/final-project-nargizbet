<%@ page import="User.User" %>
<style><%@include file="/Styles/Profile.css"%></style>
<style><%@include file="/Styles/UpperBar.css"%></style>

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
    <div id="left_corner">
        <a href="/"> <img src="Images/Logo.png" id="logo"> </a>
    </div>
</div>

<div class="navigate-side">
    <div class="profile">
        <%--
        has to change
--%>
        <img src="https://www.tenforums.com/geek/gars/images/2/types/thumb_15951118880user.png" alt="" width="100" height="100">
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
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
