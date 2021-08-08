package Servlets;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.StatsDAO;
import Database.UserDAO;
import Gameplay.Games.Blackjack;
import Gameplay.Games.Game;
import Gameplay.Games.Slots;
import User.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User)req.getSession().getAttribute("User");
        FriendsDAO friends = (FriendsDAO)req.getServletContext().getAttribute("FriendsDAO");
        StatsDAO stats = (StatsDAO)req.getServletContext().getAttribute("StatsDAO");
        UserDAO UDAO = (UserDAO)req.getServletContext().getAttribute("UserDAO");
        BalanceDAO BDAO = (BalanceDAO)req.getServletContext().getAttribute("BalanceDAO");
        String username = currentUser.getUsername();
        String givenUsername = req.getParameter("Username");
        User givenUser = new User(givenUsername, "", "", "");
        if(username.equals(givenUsername) || currentUser.isFriendsWith(givenUser)){
            boolean isMyProfile = true;
            if(currentUser.isFriendsWith(givenUser)) {
                currentUser = UDAO.getUser(givenUsername);
                isMyProfile = false;
            }
            Set<User> friendsList = friends.getFriends(currentUser);
            req.setAttribute("FriendsList", friendsList);
            req.setAttribute("ProfilePicture", currentUser.getProfilePicture());
            req.setAttribute("first_name", currentUser.getFirstName());
            req.setAttribute("last_name", currentUser.getLastName());
            req.setAttribute("currBal", BDAO.getBalance(currentUser));
            Game bj = new Blackjack();
            Game slots = new Slots();
            req.setAttribute("BJWins", stats.getWins(currentUser, bj));
            req.setAttribute("SlotMoneyGambled", stats.getWins(currentUser, slots));
            req.setAttribute("MemberSince", currentUser.getMemberSince());
            if(isMyProfile) req.getServletContext().getRequestDispatcher("/MyProfile.jsp").forward(req, resp);
            else req.getServletContext().getRequestDispatcher("/FriendProfile.jsp").forward(req, resp);
        }else {
            User usr = UDAO.getUser(givenUsername);
            req.setAttribute("first_name", usr.getFirstName());
            req.setAttribute("last_name", usr.getLastName());
            req.setAttribute("ProfilePicture", usr.getProfilePicture());
            req.getServletContext().getRequestDispatcher("/NotFriendProfile.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
