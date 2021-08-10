package Servlets;

import Database.StatsDAO;
import Gameplay.Games.Game;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LeaderboardServlet extends HttpServlet {
	private static final int LEADERBOARD_TOP = 10;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("User");
    	StatsDAO SDAO = (StatsDAO)getServletContext().getAttribute("StatsDAO");
        Game game = (Game)request.getAttribute("game");
        
        List<Map.Entry<User, Integer>> leaderboard = SDAO.getLeaderboard(game, LEADERBOARD_TOP);
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
