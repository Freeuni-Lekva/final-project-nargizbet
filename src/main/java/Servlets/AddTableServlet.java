package Servlets;

import Gameplay.Games.Blackjack.Blackjack;
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
        String gameName = (String) request.getAttribute("gameName");
        List<Table> tables = (List) request.getServletContext().getAttribute(gameName + "Tables");
        Table newBlackjackTable = new Table(new Blackjack());
        tables.add(newBlackjackTable);
        request.getServletContext().setAttribute(gameName + "Tables", tables);
        request.getRequestDispatcher("ViewTables.jsp").forward(request, response);
    }

}