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
        String username = request.getParameter("username");
        String psw = request.getParameter("psw");
        
        User user = new User(username, psw, "", "");
        
        if (!UDAO.userRegistered(user) || !UDAO.isCorrectPass(user)) {
            request.setAttribute("ErrorMessage", "Account does not exist. Try again.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
        	user = UDAO.getUser(username); // get full user info
        	request.getSession().setAttribute("User", user);
        	
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}
