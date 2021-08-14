<%@ page import="User.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Table</title>

	<style><%@include file="/Styles/BlackjackStyleTable.css"%></style>
	<script src="Javascript/BlackjackTableFront.js"></script>
	<script src="Javascript/BlackjackWebsocket.js"></script>
	<script src="Javascript/ChatScripts.js"></script>
</head>
<body onload="connectTable(<%= request.getParameter("tableId") %>, <%= request.getAttribute("amount") %>) ">
	<%
		User usr = (User)request.getSession().getAttribute("User");
		request.setAttribute("username", usr.getUsername());
		if (request.getAttribute("maxPlayers") == null)
			request.setAttribute("maxPlayers", 4);
	%>
	<input type="hidden" class="username" value="<%= request.getAttribute("username") %>">
	<input type="hidden" class="maxPlayers" value="<%= request.getAttribute("maxPlayers") %>">
	
	<div class="chat">
		<div class="tableContainer">
			<div class="messageWindow"></div>
		</div>
		<div class="sendMessage">
			<form action="" onsubmit="sendMessage(); return false;" id="message_bar">
				<input type="text" placeholder="Enter message" name="enterMessage"
					   class="enterMessage" required>
				<input type="submit" value="Send" id="send_button"/>
			</form>
		</div>
	</div>

	<div class="blackjack">
		<div class="message"></div>
		
		<div class="amount"><p class="amountLable">Amount: <%= request.getAttribute("amount") %>$</p></div>

		<div class="dealer">
			<img class="deckImage" src="/Images/CardBack.PNG" alt="">
			<div class="cards">
			</div>
		</div>

		<div class="enterBet" hidden>
			<input type="number" placeholder="Enter Bet" name="bet"
				   id="bet" required>
			<button id="enter_bet_button" onclick="setBet()">Enter</button>
		</div>

		<div id="middle_grid">
			<div class="action">
				<button class="button hitBtn" onclick="" hidden>Hit</button>
				<button class="button standBtn" onclick="" hidden>Stand</button>
			</div>

			<div class="users0">
				<div class="user emptyUser" id="upper_left">
					<div class="cards">
					</div>
				</div>

				<div class="user emptyUser" id="upper_right">
					<div class="cards">
					</div>
				</div>
			</div>
		</div>

		<div id="lower_grid">
			<div class="users1">
				<div class="user emptyUser" id="lower_left">
					<div class="cards">
        			</div>
				</div>

				<div class="user emptyUser" id="lower_right">
					<div class="cards">
					</div>
				</div>
			</div>
			
			<div class="tmpUsers"></div> <!-- no need to draw this, just stores users temporarily -->
		</div>
	</div> 
</body>
</html>