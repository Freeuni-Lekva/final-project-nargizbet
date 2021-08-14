package Servlets;

import Database.FriendsDAO;
import Database.UserDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFriendServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("User") == null) {
            response.sendRedirect("/homepage");
            return;
        }

        User currentUser = (User) request.getSession().getAttribute("User");
        UserDAO UDAO = new UserDAO();
        User friend = UDAO.getUser(request.getParameter("Username"));
        FriendsDAO FDAO = new FriendsDAO(UDAO);
        FDAO.removePair(currentUser, friend);
        response.sendRedirect("/profile?Username=" + friend.getUsername());
    }

}