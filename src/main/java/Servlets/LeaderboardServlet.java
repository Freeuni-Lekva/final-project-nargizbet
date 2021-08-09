package Servlets;

import Database.StatsDAO;
import Gameplay.Games.Blackjack;
import Gameplay.Games.Game;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class LeaderboardServlet extends HttpServlet {
	private static final int LEADERBOARD_TOP = 10;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("User");
    	StatsDAO SDAO = (StatsDAO)getServletContext().getAttribute("StatsDAO");
        Game game = (Game)request.getAttribute("game");
        // temp
        	game = new Blackjack();
        //
        
        List<Map.Entry<User, Integer>> leaderboard = SDAO.getLeaderboard(game, LEADERBOARD_TOP);
        // temp
        	leaderboard.add(new AbstractMap.SimpleEntry<>(new User("1", "s", "s", "s"), 100000));
        	leaderboard.add(new AbstractMap.SimpleEntry<>(new User("2", "s", "s", "s"), 10000));
        	leaderboard.add(new AbstractMap.SimpleEntry<>(new User("3", "s", "s", "s"), 1000));
        	leaderboard.add(new AbstractMap.SimpleEntry<>(new User("4", "s", "s", "s"), 100));
        //
        request.setAttribute("leaderboard", leaderboard);
        
        int userPlace = SDAO.getUserPlace(user, game);
        int userWins = SDAO.getWins(user, game);
        request.setAttribute("userPlace", userPlace);
        request.setAttribute("userWins", userWins);
        
        request.setAttribute("gameName", game.getName());
        request.setAttribute("user", user);
        request.getRequestDispatcher("LeaderBoard.jsp").forward(request, response);
    }

}