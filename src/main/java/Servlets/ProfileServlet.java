package Servlets;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.StatsDAO;
import Database.UserDAO;
import Gameplay.Games.Blackjack.BlackjackGame;
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

        if (req.getSession().getAttribute("User") == null) {
            resp.sendRedirect("/homepage");
            return;
        }

        User currentUser = (User)req.getSession().getAttribute("User");
        FriendsDAO friends = (FriendsDAO)req.getServletContext().getAttribute("FriendsDAO");
        StatsDAO stats = (StatsDAO)req.getServletContext().getAttribute("StatsDAO");
        UserDAO UDAO = (UserDAO)req.getServletContext().getAttribute("UserDAO");
        BalanceDAO BDAO = (BalanceDAO)req.getServletContext().getAttribute("BalanceDAO");
        String username = currentUser.getUsername();
        String givenUsername = req.getParameter("Username");
        User givenUser = new User(givenUsername, "", "", "");
        req.setAttribute("givenUsername",givenUsername);
        if(username.equals(givenUsername) || currentUser.isFriendsWith(givenUser)){
            boolean isMyProfile = true;
            if(currentUser.isFriendsWith(givenUser)) {
                currentUser = UDAO.getUser(givenUsername);
                isMyProfile = false;
            }
            Set<User> friendsList = friends.getFriends(currentUser);
            req.setAttribute("FriendsList", friendsList);
            req.setAttribute("ProfilePicture", UDAO.getProfilePicture(currentUser.getUsername()));
            req.setAttribute("first_name", currentUser.getFirstName());
            req.setAttribute("last_name", currentUser.getLastName());
            req.setAttribute("currBal", BDAO.getBalance(currentUser));
            Game bj = new BlackjackGame();
            Game slots = new Slots();
            req.setAttribute("BJWins", stats.getWins(currentUser, bj));
            req.setAttribute("SlotMoneyGambled", stats.getMoneyGambled(currentUser));
            req.setAttribute("MemberSince", UDAO.getMembership(currentUser));
            if(isMyProfile) req.getServletContext().getRequestDispatcher("/JSP/MyProfile.jsp").forward(req, resp);
            else req.getServletContext().getRequestDispatcher("/JSP/FriendProfile.jsp").forward(req, resp);
        } else {
            User usr = UDAO.getUser(givenUsername);
            req.setAttribute("first_name", usr.getFirstName());
            req.setAttribute("last_name", usr.getLastName());
            req.setAttribute("ProfilePicture", UDAO.getProfilePicture(currentUser.getUsername()));
            req.setAttribute("MemberSince", UDAO.getMembership(currentUser));
            req.setAttribute("givenUser", givenUser);
            req.getServletContext().getRequestDispatcher("/JSP/NotFriendProfile.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
