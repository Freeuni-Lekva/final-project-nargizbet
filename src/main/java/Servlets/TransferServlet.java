package Servlets;

import Database.BalanceDAO;
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
        User to = (User) request.getAttribute("User");
        double amount = (double) request.getAttribute("amount");
        User from = (User) request.getSession().getAttribute("User");
        boolean success = from.transfer(to, amount);
        if(success){
            BalanceDAO BDAO = (BalanceDAO)getServletContext().getAttribute("BalanceDAO");
            BDAO.setBalance(to);
            BDAO.setBalance(from);
            request.getRequestDispatcher("TransactionSucceeded.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("TransactionFailed.jsp").forward(request, response);
        }
    }
}
