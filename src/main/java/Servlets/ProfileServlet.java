package Servlets;

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
import java.util.List;

public class ProfileServlet extends HttpServlet {
    public final static int MY_PROFILE = 0;
    public final static int FRIEND_PROFILE = 1;
    public final static int NOT_FRIEND_PROFILE = 2;
    public final static int NOT_REGISTERED_PROFILE = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User)req.getSession().getAttribute("User");
        FriendsDAO friends = (FriendsDAO)req.getServletContext().getAttribute("FriendsDAO");
        StatsDAO stats = (StatsDAO)req.getServletContext().getAttribute("StatsDAO");
        UserDAO UDAO = (UserDAO)req.getServletContext().getAttribute("UserDAO");
        String username = currentUser.getUsername();
        String firstName = currentUser.getFirstName();
        String lastName = currentUser.getLastName();
        String givenUsername = req.getParameter("Username");
        User givenUser = new User(givenUsername, "", "", "");
        if(!UDAO.userRegistered(givenUser)){
            req.setAttribute("username", givenUsername);
            req.setAttribute("ProfileType", NOT_REGISTERED_PROFILE);
            req.getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        }else if(username.equals(givenUsername) || currentUser.isFriendsWith(givenUser)){
                if(currentUser.isFriendsWith(givenUser)) currentUser = UDAO.getUser(username);
                List<User> friendsList = friends.getFriends(currentUser);
                req.setAttribute("FriendsList", friendsList);
                req.setAttribute("ProfilePicture", currentUser.getProfilePicture());
                req.setAttribute("first_name", currentUser.getFirstName());
                req.setAttribute("last_name", currentUser.getLastName());
                Game bj = new Blackjack();
                Game slots = new Slots();
                req.setAttribute("BJWins", stats.getWins(currentUser, bj));
                req.setAttribute("SlotMoneyGambled", stats.getWins(currentUser, slots));
                req.setAttribute("MemberSince", currentUser.getMemberSince());
                if(username.equals(givenUsername)) req.setAttribute("ProfileType", MY_PROFILE);
                else req.setAttribute("ProfileType", FRIEND_PROFILE);
                req.getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        }else {
            User usr = UDAO.getUser(username);
            req.setAttribute("first_name", usr.getFirstName());
            req.setAttribute("last_name", usr.getLastName());
            req.setAttribute("ProfileType", NOT_FRIEND_PROFILE);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
