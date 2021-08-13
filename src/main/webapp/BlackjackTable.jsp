<%@ page import="User.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="Javascript/BlackjackTableFront.js"></script>
<script src="Javascript/ChatScripts.js"></script>
<html>
<head>
	<meta charset="UTF-8">
	<title>Table</title>

</head>
<body onload="connect(<%= request.getParameter("tableId") %>) ">
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
		<div class="message"></div>
		
		<div class="amount"><p class="amountLable">Amount: <%= request.getAttribute("amount") %>$</p></div>


		<div class="dealer">
			<img class="deckImage" src="/Images/CardBack.PNG" alt="">
			<div class="cards">
				<div class="card" data-value="9♥">
					<div class="middle_suit">♥</div>
				</div>
			</div>
		</div>

		<div id="middle_grid">
			<div class="action">
				<button class="button hitBtn" onclick="" >Hit</button>
				<button class="button standBtn" onclick="">Stand</button>
			</div>

			<div class="user emptyUser" id="upper_left">

			</div>

			<div class="user emptyUser" id="upper_right">
				<div class="cards">
					<div class="card" data-value="9♥" id="lw">
						<div class="middle_suit">♥</div>
					</div>
				</div>
				<p class="username">username</p>
				<p class="bet">bet: 0</p>
			</div>

		</div>

		<div id="lower_grid">
			<div class="user emptyUser" id="lower_left">
				<div class="cards">
					<div class="card" data-value="9♥">
						<div class="middle_suit">♥</div>
					</div>
				</div>
				<p class="username">username</p>
				<p class="bet">bet: 0</p>
			</div>

			<div class="user emptyUser" id="lower_right">
				<div class="cards">
					<div class="card" data-value="9♥">
						<div class="middle_suit">♥</div>
					</div>
				</div>
				<p class="username">username</p>
				<p class="bet">bet: 0</p>
			</div>
		</div>

	</div> 
</body>
</html>