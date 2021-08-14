<%@ page import="User.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Table</title>
	<link rel="shortcut icon" href="/Images/NargizbetIcon.ico" type="image/x-icon">

	<style><%@include file="/Styles/BlackjackStyleTable.css"%></style>
	<script src="../Javascript/BlackjackTableFront.js"></script>
	<script src="../Javascript/BlackjackWebsocket.js"></script>
	<script src="../Javascript/ChatScripts.js"></script>

</head>
<body onload="connectTable(<%= request.getParameter("tableId") %>, <%= request.getParameter("amount") %>) ">
	<%
		User usr = (User)request.getSession().getAttribute("User");
		request.setAttribute("username", usr.getUsername());
		if (request.getAttribute("maxPlayers") == null)
			request.setAttribute("maxPlayers", 4);
	%>
	<input type="hidden" class="username" value="<%= request.getAttribute("username") %>">
	<input type="hidden" class="maxPlayers" value="<%= request.getAttribute("maxPlayers") %>">

	<div class="displayMessage"></div>

	<div class="chat">
		<div class="tableContainer">
			<div class="messageWindow"></div>
		</div>
		<div class="sendMessage">
			<form action="" onsubmit="sendMessage(); return false;" id="message_bar">
				<input type="text" placeholder="Enter message" name="enterMessage"
					   class="enterMessage" id="message_box" required>
				<input type="submit" value="Send" id="send_button"/>
			</form>
		</div>
	</div>
	<div id="timer_text" style="position: absolute; color: white;"></div>

	<div class="blackjack">

		<div class="amount">
			<p class="amountLable">Amount: <%= request.getParameter("amount") %>$</p>
			<input type="hidden" class="amountValue" value="<%= request.getParameter("amount") %>">
		</div>

		<div class="upper_grid">
			<div id="left_side">
				<a href="/JSP/ViewTables.jsp?gameName=Blackjack" onclick="leaveTable();" id="logo_container">
					<img src="/Images/Logo.png" id="bjlogo">
				</a>
			</div>
			<div class="dealer">
				<img class="deckImage" src="/Images/CardBack.PNG" alt="">
				<div class="cards">
				</div>
			</div>
		</div>

		<div id="middle_grid">
			<div class="action">
				<button class="button hitBtn" onclick="" hidden>Hit</button>
				<button class="button standBtn" onclick="" hidden>Stand</button>
				<div class="enterBet" hidden>
					<input type="number" placeholder="Enter Bet" name="bet"
						   id="bet" required>
					<button id="enter_bet_button" onclick="setBet()">Enter</button>
				</div>
			</div>
			
			<div class="user emptyUser" id="upper_left">
				<div class="cards">
				</div>
			</div>

			<div class="user emptyUser" id="upper_right">
				<div class="cards">
				</div>
			</div>
		</div>

		<div id="lower_grid">
			<div class="user emptyUser" id="lower_left">
				<div class="cards">
       			</div>
			</div>

			<div class="user emptyUser" id="lower_right">
				<div class="cards">
				</div>
			</div>
		</div>
	</div>
	<script src="../Javascript/countdown.js"></script>
</body>
</html>