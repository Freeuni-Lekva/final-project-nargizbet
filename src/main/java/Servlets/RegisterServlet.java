package Servlets;

import Database.BalanceDAO;
import Database.UserDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO UDAO = (UserDAO)request.getServletContext().getAttribute("UserDAO");
        BalanceDAO BDAO = (BalanceDAO)request.getServletContext().getAttribute("BalanceDAO");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("psw");
        User user = new User(username, password, firstName, lastName);

        if (UDAO.userRegistered(user)) {
            request.setAttribute("ErrorMessage", "Provided username is already taken");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else {
            UDAO.addUser(user);
            BDAO.addBalance(user);
            request.getSession().setAttribute("User", user);
            response.sendRedirect("/");
        }

    }

}