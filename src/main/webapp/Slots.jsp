<%@ page import="User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User currentUser = (User) request.getSession().getAttribute("User");
    request.setAttribute("balance", currentUser.getBalance());
%>

<html>

<style>
    <%@include file="/Styles/SlotsStyle.css"%>
    <%@include file="/Styles/UpperBar.css"%>
</style>

<head>
    <title> Slots </title>
    <link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">
    <script src = "Javascript/SlotsScript.js"></script>
</head>

<body style = "background: #25292b">

<div id="header_box">
    <header id="upper_bar">

        <div id="left_corner">
            <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
        </div>

        <div id = title-format> NARGIZBET SLOTS </div>

        <div id = "right_corner">
            <form action = /exitslots method = post>
                <button id = exit-button type = submit> Exit </button>
                <input id = balance type = "hidden" name = "balance" value = "<%= request.getAttribute("balance") %>">
                <input id = moneyGambled type = hidden name = moneyGambled value = 0>
            </form>
        </div>

    </header>
</div>

<div id = dist> </div>

<div class = container>

    <div class = slot id = slot0>
        <img src = "Images/Logo.png" class = image-format id="slot0-img"/>
    </div>

    <div class = slot id = slot1>
        <img src = "Images/Logo.png" class = image-format id="slot1-img"/>
    </div>

    <div class = slot id = slot2>
        <img src = "Images/Logo.png" class = image-format id="slot2-img"/>
    </div>

</div>

<br>
<br>

<div class = info-container>
    <div id = info-format>
        Matching 2 pictures results in winning 3x the amount you bet <br>
        Matching 3 pictures results in winning 10x the amount you bet <br>
        Matching 3 diamonds results in winning 100x the amount you bet
    </div>
</div>

<div class = info-container>
    <p id = "balance-text-format"> Current balance: $<%= request.getAttribute("balance") %> </p>
    <p id = "money-gambled-text-format"> Money gambled: $0 </p>
</div>

<div class = info-container id = column-flex>
    <input id = input-format type = number placeholder = "Enter the amount you wish to bet" required>
    <button id = spin-button onclick = "spin()"> SPIN! </button>
</div>

</body>

</html>