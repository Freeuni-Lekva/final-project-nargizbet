package Servlets;

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
        User user = (User)request.getAttribute("user");
        if (UDAO.getUser(user.getUsername()) != null) {
            request.setAttribute("ErrorMessage", "Provided username is already taken");
            request.getRequestDispatcher("TryRegisterAgain.jsp").forward(request, response);
        } else if (user.getPassword().length() < User.passwordMinimumLength) {
            String errorMessage = "Password must contain at least " + User.passwordMinimumLength + " symbols";
            request.setAttribute("ErrorMessage", errorMessage);
            request.getRequestDispatcher("TryRegisterAgain.jsp").forward(request, response);
        } else {
            UDAO.addUser(user);
            request.getRequestDispatcher("HomepageServlet").forward(request, response);
        }
    }

}