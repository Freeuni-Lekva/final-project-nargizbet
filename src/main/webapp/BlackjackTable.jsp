<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Table</title>

	<script src="Javascript/BlackjackTableFront.js"></script>
</head>
<body onload="connect(<%= request.getParameter("tableId") %>) ">
	<input type="hidden" class="username" value="<%= request.getParameter("username") %>">
	<input type="hidden" class="maxPlayers" value="<%= request.getParameter("maxPlayers") %>">
	
	<div class="chat">
		<div class="messageWindow"></div> 
		<div class="sendMessage">
			<form action="" onsubmit=" sendMessage(); return false">
				<input type="text" placeholder="Enter message" name="enterMessage" 
					class="enterMessage" required>
				
				<button type="submit" class="sendBtn" class="sendBtn">Send</button>
			</form>
		</div>
	</div>

	<div class="blackjack">
		<div class="amount"><p class="amountLable">Amount: </p></div>
		
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