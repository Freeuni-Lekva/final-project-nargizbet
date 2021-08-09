package Servlets;

import Database.FriendsDAO;
import Database.UserDAO;
import Tests.FriendsDAOTest;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FriendRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FriendsDAO FDAO = (FriendsDAO)getServletContext().getAttribute("FriendsDAO");
        User curr = (User)request.getSession().getAttribute("User");

        Set<User> receivedRequests = FDAO.FriendRequestsRecieved(curr);
        request.setAttribute("received", receivedRequests);
        request.setAttribute("first_name", curr.getFirstName());
        request.setAttribute("last_name", curr.getLastName());
        request.setAttribute("balance", curr.getBalance());
        request.setAttribute("username", curr.getUsername());
        request.getRequestDispatcher("FriendRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username =request.getParameter("Username");
        UserDAO UDAO = (UserDAO)getServletContext().getAttribute("UserDAO");
        FriendsDAO FDAO = (FriendsDAO)getServletContext().getAttribute("FriendsDAO");
        User receiver = UDAO.getUser(username);
        User sender = (User)request.getSession().getAttribute("User");
        if (FDAO.isFriendRequest(sender, receiver)) {
            FDAO.addPair(sender, receiver);
            FDAO.removeFriendRequest(receiver, sender);
        } else {
            FDAO.addFriendRequest(sender, receiver);
        }
        request.getRequestDispatcher("/profile").forward(request, response);
    }
}
