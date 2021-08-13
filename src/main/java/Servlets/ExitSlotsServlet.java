package Servlets;

import Database.BalanceDAO;
import Database.StatsDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExitSlotsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("User");
        BalanceDAO BDAO = (BalanceDAO) request.getServletContext().getAttribute("BalanceDAO");
        StatsDAO SDAO = (StatsDAO) request.getServletContext().getAttribute("StatsDAO");
        Double newBalance = Double.parseDouble(request.getParameter("balance"));
        currentUser.setBalance(newBalance);
        BDAO.setBalance(currentUser);
        Double moneyGambled = Double.parseDouble(request.getParameter("moneyGambled"));
        SDAO.addMoneyGambled(currentUser, moneyGambled);
        response.sendRedirect("/homepage");
    }

}