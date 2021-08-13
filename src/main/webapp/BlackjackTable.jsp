<%@ page import="User.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="Javascript/BlackjackTableFront.js"></script>
<script src="Javascript/BlackjackWebsocket.js"></script>
<script src="Javascript/ChatScripts.js"></script>
<html>
<head>
	<meta charset="UTF-8">
	<title>Table</title>

</head>
<body onload="connectBlackjack(<%= request.getParameter("tableId") %>, <%= request.getAttribute("amount")%>) ">
<%
	User usr = (User)request.getSession().getAttribute("User");
	request.setAttribute("username", usr.getUsername());
%>

	<input type="hidden" class="username" value="<%= request.getAttribute("username") %>">
	<input type="hidden" class="maxPlayers" value="<%= request.getParameter("maxPlayers") %>">
	
	<div class="chat">
		<div class="messageWindow"></div> 
		<div class="sendMessage">
			<form action="" onsubmit="sendMessage(); return false;">
				<input type="text" placeholder="Enter message" name="enterMessage" 
					class="enterMessage" required>
				<input type="submit" value="Send"/>
			</form>
		</div>
	</div>

	<div class="blackjack">
		<div class="amount"><p class="amountLable">Amount: </p>
			<button class="amount-button" onclick="" hidden>Bet</button>
			<input class="amount-field" type="number" name="the-amount">
		</div>
		<div class="action">
			<button class="button hitBtn" onclick="" hidden>Hit</button>
			<button class="button standBtn" onclick="" hidden>Stand</button>
		</div>

		<div class="dealer">
			<img class="deckImage" src="" alt=""> <!-- to be added -->
			<div class="cards"></div>
		</div>

		<div class="users">
			<script> drawBlank() </script>
		</div>
	</div> 
</body>
</html>