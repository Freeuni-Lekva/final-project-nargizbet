package Servlets;

import Gameplay.Games.Blackjack.BlackjackTable;
import Gameplay.Games.Blackjack.BlackjackGame;
import Gameplay.Room.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddTableServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("User") == null) {
            response.sendRedirect("/homepage");
            return;
        }

        String gameName = request.getParameter("gameName");
        List<Table> tables = (List) request.getServletContext().getAttribute(gameName + "Tables");
        Table newBlackjackTable = new BlackjackTable(new BlackjackGame());
        tables.add(newBlackjackTable);
        request.getServletContext().setAttribute(gameName + "Tables", tables);
        request.getRequestDispatcher("/JSP/ViewTables.jsp").forward(request, response);
    }

}