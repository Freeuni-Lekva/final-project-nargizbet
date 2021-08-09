package Database;

import User.User;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public synchronized boolean userRegistered(User u){
        try {
        	if (u == null) return false;
        	
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("Select * " +
                                                                   "FROM users " +
                                                                   "WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            boolean ans = rs.next();
            con.close();
            return ans;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public synchronized void addUser(User u){
        try {
            String usrname = u.getUsername();
            String frst_name = u.getFirstName();
            String last_name = u.getLastName();
            String psw = u.getPassword();

            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("INSERT INTO users " +
                                                                   "(username, first_name, last_name, member_since, psw) " +
                                                                    "VALUES (?, ?, ?, SYSDATE(), ?)");
            statement.setString(1, usrname);
            statement.setString(2, frst_name);
            statement.setString(3, last_name);
            statement.setString(4, psw);
            statement.executeUpdate();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public synchronized List<User> getUsersLike(String usernamePattern) {
        List<User> users = null;
        Connection conn = DataSource.getCon();
        try {
            users = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE username LIKE ? ");
            statement.setString(1, '%' + usernamePattern + '%');
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String username = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(6);
                users.add(new User(username, password, firstName, lastName));
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public synchronized InputStream getProfilePicture(String username){
        try {

            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            InputStream inputStream = null;
            if(rs.next()){
                inputStream = rs.getBinaryStream("profile_picture");
            }
            con.close();
            return inputStream;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public synchronized boolean setProfilePicture(String username, InputStream inputStream){
        try {

            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("UPDATE users SET profile_picture = ? " +
                    "                                               WHERE username = ?");
            statement.setString(2, username);
            statement.setBinaryStream(1, inputStream);
            int i = statement.executeUpdate();
            con.close();
            return i != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public synchronized User getUser(String username) {
        User user = null;
        try {
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("SELECT* FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            
	        if(res.next()) {
                String password = res.getString(6);
                String firstName = res.getString(2);
                String lastName = res.getString(3);
                BalanceDAO BDAO = new BalanceDAO();
                user = new User(username, password, firstName, lastName, 1);
                Double balance = BDAO.getBalance(user);
                user.setBalance(balance);
	        }
	        con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public synchronized boolean isCorrectPass(User u){
        try {
            String usrname = u.getUsername();
            String psw = u.getPassword();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("SELECT psw FROM users WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String actualPsw = rs.getString(1);
            con.close();
            if(actualPsw.equals(psw)) return true;
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public synchronized Date getMembership(User u){
        try {
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("SELECT member_since FROM users WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Date memberSince = rs.getDate(1);
            con.close();
            return memberSince;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}