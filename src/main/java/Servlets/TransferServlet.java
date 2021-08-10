package Servlets;

import Database.BalanceDAO;
import Database.UserDAO;
import User.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


public class TransferServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO UDAO = (UserDAO)getServletContext().getAttribute("UserDAO");
        String toUsername = (String) request.getParameter("User");
        User to = UDAO.getUser(toUsername);
        double amount = Double.valueOf(request.getParameter("amount"));
        User from = (User) request.getSession().getAttribute("User");
        boolean success = from.transfer(to, amount);
        if(success){
            BalanceDAO BDAO = (BalanceDAO)getServletContext().getAttribute("BalanceDAO");
            BDAO.setBalance(to);
            BDAO.setBalance(from);
            request.setAttribute("status", "Transfer Approved");
            request.setAttribute("message", "Transfer completed successfully");
        }else{
            request.setAttribute("status", "Transfer Failed");
            request.setAttribute("message", "Not enough money to transfer");
        }
        request.getRequestDispatcher("TransferStatus.jsp").forward(request, response);
    }
}
