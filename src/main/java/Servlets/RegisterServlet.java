package Servlets;

import Database.UserDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO UDAO = (UserDAO)request.getServletContext().getAttribute("UDAO");
        User user = (User)request.getAttribute("user");
        if (UDAO.getUser(user.getUsername()) == null) {
            UDAO.addUser(user);
            request.getRequestDispatcher("HomepageServlet").forward(request, response);
        } else {
            request.getRequestDispatcher("TryRegisterAgain.jsp").forward(request, response);
        }
    }

}
