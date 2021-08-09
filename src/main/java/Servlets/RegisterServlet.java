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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("psw");
        User user = new User(username, password, firstName, lastName);

        if (UDAO.userRegistered(user)) {
            request.setAttribute("ErrorMessage", "Provided username is already taken");
            request.getRequestDispatcher("TryRegisterAgain.jsp").forward(request, response);
        } else if (user.getPassword().length() < User.PASSWORD_MINIMUM_LENGTH) {
            String errorMessage = "Password must contain at least " + User.PASSWORD_MINIMUM_LENGTH + " symbols";
            request.setAttribute("ErrorMessage", errorMessage);
            request.getRequestDispatcher("TryRegisterAgain.jsp").forward(request, response);
        } else {
            UDAO.addUser(user);
            request.getSession().setAttribute("User", user);
            request.getRequestDispatcher("HomepageServlet").forward(request, response);
        }
    }

}