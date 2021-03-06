<%@ page import="User.User" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<style><%@include file="/Styles/Profile.css"%></style>
<style><%@include file="/Styles/UpperBar.css"%></style>
<script src="../Javascript/AmountCheckScript.js"></script>
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
<div class="navigate-side">
    <div class="profile">
        <img src="/displayimage?Username=<%= (String)request.getAttribute("givenUsername") %>" alt="" class="img-style"/>
        <div class="note-friend">
            You two are friends
        </div>
        <form action="/deletefriend" method="POST">
            <button class="friendreq-button" type="submit"> Unfriend </button>
            <input type="hidden" name="Username" value = <%=(String)request.getAttribute("givenUsername")%>>
        </form>
        <form action="/transfer" method="POST" onsubmit="return checkAmount()">
            <label for="amount">Amount</label>
            <input class="transfer-amount" type="number" name="amount" id="amount" required>
            <input type="hidden" name="User" value=<%=(String)request.getAttribute("givenUsername")%>>
            <button class="transfer-button" type="submit">Deposit</button>
        </form>

        <div class="gamelist-f">
            <h2>Games Played</h2>
            <ul class="games-text">
                <li><%="BlackJack: "%><%=request.getAttribute("BJWins")%> Wins</li>
                <li><%="Money gambled in Slots: "%><%=request.getAttribute("SlotMoneyGambled")%><%="$"%></li>
            </ul>
        </div>
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
