package Servlets;

import Database.UserDAO;
import User.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


public class SearchUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("User") == null) {
            response.sendRedirect("/homepage");
            return;
        }

        UserDAO UDAO = (UserDAO) getServletContext().getAttribute("UserDAO");
        String username = request.getParameter("username");
        List<User> users = UDAO.getUsersLike(username);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/JSP/FoundUsers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
