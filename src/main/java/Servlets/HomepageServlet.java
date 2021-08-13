package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Gameplay.Games.Blackjack.BlackjackGame;
import Gameplay.Games.Slots.Slots;
import User.User;

/**
 * Servlet implementation class HomepageServlet
 */
public class HomepageServlet extends HttpServlet {

	public class Pair{
		public String p1;
		public String p2;
		public Pair(String p1, String p2){
			this.p1 = p1;
			this.p2 = p2;
		}

		public String getP1() {
			return p1;
		}

		public String getP2() {
			return p2;
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("User");
		if (user == null) {
            request.setAttribute("ErrorMessage", "");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		} else {
			ArrayList<Pair> games = new ArrayList<>();
			games.add(new Pair((new BlackjackGame()).getImageName(), new BlackjackGame().getName()));
			games.add(new Pair((new Slots()).getImageName(), new Slots().getName()));;
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
