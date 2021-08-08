package Database;

import User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class FriendsDAO {

    private UserDAO userDao;
  
    public FriendsDAO(UserDAO userDao){
        this.userDao = userDao;
    }

    public synchronized boolean addPair(User u1, User u2){
        try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = DataSource.getCon();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO friends (username1, username2) " +
                            "VALUES (?, ?);");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);

            int numRowsInserted = preparedStatement.executeUpdate();

            conn.close();
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

            Connection conn  = DataSource.getCon();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM friends" +
                    " WHERE (username1 = ? AND username2 = ?) OR (username1 = ? AND username2 = ?)");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);
            preparedStatement.setString(3, secondUsername);
            preparedStatement.setString(4, firstUsername);

            int numRowsRemoved = preparedStatement.executeUpdate();

            conn.close();
            return numRowsRemoved != 0; //returns true if pair was removed

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public synchronized Set<User> getFriends(User u){
        Set<User> friendList = new HashSet<>();
        try {
            String currUsername = u.getUsername();

            Connection conn = DataSource.getCon();
            PreparedStatement preparedStatement =  conn.prepareStatement(
                    "SELECT * FROM friends WHERE username1 = ? OR username2 = ?");

            preparedStatement.setString(1, currUsername);
            preparedStatement.setString(2, currUsername);

            ResultSet resultSet =  preparedStatement.executeQuery();

            while(resultSet.next()){
                String firstUsername = resultSet.getString(1);
                String secondUsername = resultSet.getString(2);
                String friendUsername = firstUsername.equals(currUsername) ? secondUsername : firstUsername;

                User friend = userDao.getUser(friendUsername);
                friendList.add(friend);
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friendList;
    }
    
    public synchronized boolean areFriends(User u1, User u2){
        try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = DataSource.getCon();
            PreparedStatement preparedStatement =  conn.prepareStatement(
                    "SELECT * FROM friends WHERE (username1 = ? AND username2 = ?) " +
                            "OR (username1 = ? AND username2 = ?)");

            preparedStatement.setString(1, firstUsername);
            preparedStatement.setString(2, secondUsername);
            preparedStatement.setString(3, secondUsername);
            preparedStatement.setString(4, firstUsername);

            ResultSet resultSet =  preparedStatement.executeQuery();
            conn.close();
            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    
    /**
     * adds a friends request from u1 to u2, returns 
     * false if the request could not be sent
     * @param u1 - Friend request sender
     * @param u2 - Friend request receiver
     * @return true - friend request added to the database;
     * 		   false - friend request could not be added to the database
     */
    public synchronized boolean addFriendRequest(User u1, User u2) {
    	try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = DataSource.getCon();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO friend_requests (username1, username2) " +
                "VALUES (?, ?);"
    		);

            statement.setString(1, firstUsername);
            statement.setString(2, secondUsername);

            int numRowsInserted = statement.executeUpdate();

            conn.close();
            
            return (numRowsInserted == 1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    
    /**
     * returns true if friend request is sent from u1 to u2.
     * returns false otherwise.
     * @param u1 - request sender
     * @param u2 - request receiver 
     * @return true - request is sent from u1 to u2;
     * 		   false - request is not sent from u1 to u2
     */
    public synchronized boolean isFriendRequest(User u1, User u2) {
    	boolean result = false;
    	try {
            String firstUsername = u1.getUsername();
            String secondUsername = u2.getUsername();

            Connection conn = DataSource.getCon();
            PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM friend_requests WHERE (username1 = ? AND username2 = ?);"
    		);

            statement.setString(1, firstUsername);
            statement.setString(2, secondUsername);

            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
            
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    
    public synchronized Set<User> FriendRequestsSent(User user) {
    	Set<User> result = new HashSet<>();
    	
    	try {
    		Connection conn = DataSource.getCon();
    		PreparedStatement statement = conn.prepareStatement(
				"SELECT username2 FROM friend_requests WHERE username1 = ?;"
			);
    		
    		statement.setString(1, user.getUsername());
    		
    		ResultSet resultSet = statement.executeQuery();
    		
    		while (resultSet.next()) {
    			String username2 = resultSet.getString(1);

    			User user2 = userDao.getUser(username2);
    			result.add(user2);
    		}
    	} catch (SQLException throwables) {
            throwables.printStackTrace();
    	}
    	
    	return result;
    }
    
    public synchronized Set<User> FriendRequestsRecieved(User user) {
		Set<User> result = new HashSet<>();
    	
    	try {
    		Connection conn = DataSource.getCon();
    		PreparedStatement statement = conn.prepareStatement(
				"SELECT username1 FROM friend_requests WHERE username2 = ?;"
			);
    		
    		statement.setString(1, user.getUsername());
    		
    		ResultSet resultSet = statement.executeQuery();
    		
    		while (resultSet.next()) {
    			String username1 = resultSet.getString(1);

    			User user1 = userDao.getUser(username1);
    			result.add(user1);
    		}
    	} catch (SQLException throwables) {
            throwables.printStackTrace();
    	}
    	
    	return result;
    }
}
