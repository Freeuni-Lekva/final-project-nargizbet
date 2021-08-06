package Database;

import User.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public static String hashStr(byte[] password){
        try {
            MessageDigest ms = MessageDigest.getInstance("SHA");
            ms.update(password);
            return hexToString(ms.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public synchronized boolean userRegistered(User u){
        try {
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("Select * " +
                                                                   "FROM users " +
                                                                   "WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            return rs.next();
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
            String psw = hashStr(u.getPassword().getBytes());

            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("INSERT INTO users " +
                                                                   "(username, first_name, last_name, member_since, psw) " +
                                                                    "VALUES (?, ?, ?, SYSDATE(), ?)");
            statement.setString(1, usrname);
            statement.setString(2, frst_name);
            statement.setString(3, last_name);
            statement.setString(4, psw);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public synchronized User getUser(String username) {
        User user = null;
        
        try {
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("SELECT* FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            
	        if(res.next()) {
                String password = res.getString(2);
                String firstName = res.getString(3);
                String lastName = res.getString(4);
                user = new User(username, password, firstName, lastName);
	        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        return user;
    }
}