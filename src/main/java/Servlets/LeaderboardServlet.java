package Servlets;

import Database.StatsDAO;
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

public class LeaderboardServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StatsDAO SDAO = (StatsDAO)request.getServletContext().getAttribute("StatsDAO");
        Game game = (Game)request.getAttribute("game");
        int leaderNum = (Integer)request.getAttribute("leaderNum");
        List<Map.Entry<User, Integer>> leaderboard = SDAO.getLeaderboard(game, leaderNum);
        User user = (User)request.getAttribute("user");
        int userPlace = SDAO.getUserPlace(user, game);
        leaderboard.add(new AbstractMap.SimpleEntry<>(user, userPlace));
        request.setAttribute("leaderboard", leaderboard);
        request.getRequestDispatcher("Leaderboard.jsp").forward(request, response);
    }

}