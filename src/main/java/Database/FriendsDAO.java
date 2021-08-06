package Database;

import User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO {

    private DataSource dataSource;
    private UserDAO userDao;


    public FriendsDAO(DataSource dataSource, UserDAO userDao){
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    public FriendsDAO(DataSource dataSource){
        this.dataSource = dataSource;
        this.userDao = null;
    }


    public synchronized boolean addPair(User u1, User u2){
        try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = dataSource.getCon();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO friends (username1, username2) " +
                            "VALUES (?, ?);");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);

            int numRowsInserted = preparedStatement.executeUpdate();
            return numRowsInserted == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public synchronized boolean removePair(User u1, User u2){
        try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn  = dataSource.getCon();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM friends" +
                    " WHERE (username1 = ? AND username2 = ?) OR (username1 = ? AND username2 = ?)");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);
            preparedStatement.setString(3, secondUsername);
            preparedStatement.setString(4, firstUsername);

            int numRowsRemoved = preparedStatement.executeUpdate();
            return numRowsRemoved != 0; //returns true if pair was removed

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public synchronized List<User> getFriends(User u){
        List<User> friendList = new ArrayList<>();
        try {
            String currUsername = u.getUsername();

            Connection conn = dataSource.getCon();
            PreparedStatement preparedStatement =  conn.prepareStatement(
                    "SELECT * FROM friends WHERE username1 = ? OR username2 = ?");



            preparedStatement.setString(1, currUsername);
            preparedStatement.setString(2, currUsername);

            ResultSet resultSet =  preparedStatement.executeQuery();

            while(resultSet.next()){
                String firstUsername = resultSet.getString(1);
                String secondUsername = resultSet.getString(2);
                String friendUsername = firstUsername == currUsername ? secondUsername : firstUsername;

                User friend = userDao.getUser(friendUsername);
                friendList.add(friend);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friendList;
    }
    public synchronized boolean areFriends(User u1, User u2){
        try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = dataSource.getCon();
            PreparedStatement preparedStatement =  conn.prepareStatement(
                    "SELECT * FROM friends WHERE (username1 = ? AND username2 = ?) " +
                            "OR (username1 = ? AND username2 = ?)");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);
            preparedStatement.setString(3, secondUsername);
            preparedStatement.setString(4, firstUsername);

            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
