package Servlets;

import Database.BalanceDAO;
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
        Double balance = Double.parseDouble(request.getParameter("balance"));
        currentUser.setBalance(balance);
        BDAO.setBalance(currentUser);
        response.sendRedirect("/homepage");
    }

}