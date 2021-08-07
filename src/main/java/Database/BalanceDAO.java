package Database;

import User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceDAO {
    public synchronized void setBalance(User u) {
        try {
            String usrname = u.getUsername();
            double blnc = u.getBalance();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("UPDATE balances " +
                                                                   "SET balance = ? " +
                                                                   "WHERE username = ?");
            statement.setDouble(1, blnc);
            statement.setString(2, usrname);
            statement.executeUpdate();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public synchronized double getBalance(User u){
        try {
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("Select balance " +
                                                                   "FROM balances " +
                                                                   "WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Double db = rs.getDouble(1);
            con.close();
            return db;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public synchronized void addBalance(User u){
        try {
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("INSERT INTO balances VALUES (?, ?)");
            statement.setString(1, usrname);
            statement.setDouble(2, u.getBalance());
            statement.executeUpdate();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
