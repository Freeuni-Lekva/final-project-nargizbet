package Servlets;

import Database.UserDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO UDAO = (UserDAO)request.getServletContext().getAttribute("UserDAO");
        User user = (User)request.getAttribute("user");
        if (!UDAO.userRegistered(user)) {
            request.setAttribute("ErrorMessage", "Account is not found");
            request.getRequestDispatcher("LoginFailed.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("HomepageServlet").forward(request, response);
        }
    }
}
