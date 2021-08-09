package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Gameplay.Games.Blackjack;
import Gameplay.Games.Game;
import Gameplay.Games.Slots;
import User.User;

/**
 * Servlet implementation class HomepageServlet
 */
public class HomepageServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("User");
		if (user == null)
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		else {
			ArrayList<String> games = new ArrayList<>();
			games.add((new Blackjack()).getImageName());
			games.add((new Slots()).getImageName());
			request.setAttribute("game_list", games);
			request.setAttribute("first_name", user.getFirstName());
			request.setAttribute("last_name", user.getLastName());
			request.setAttribute("balance", user.getBalance());
			request.setAttribute("username", user.getUsername());
			request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
