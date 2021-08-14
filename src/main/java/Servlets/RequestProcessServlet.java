package Servlets;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.UserDAO;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("User") == null) {
            response.sendRedirect("/homepage");
            return;
        }

        FriendsDAO FDAO = (FriendsDAO)getServletContext().getAttribute("FriendsDAO");
        UserDAO UDAO = (UserDAO)getServletContext().getAttribute("UserDAO");
        String username = request.getParameter("Username");
        User user1 = UDAO.getUser(username);
        User user2 = (User) request.getSession().getAttribute("User");

        boolean result = FDAO.removeFriendRequest(user1, user2);
        if(result){
            String type = request.getParameter("Type");
            if(type.equals("accept")){
                FDAO.addPair(user1, user2);
            }
        }
        response.sendRedirect("/friendrequests");
    }
}