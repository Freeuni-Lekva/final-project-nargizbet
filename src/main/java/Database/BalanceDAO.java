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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public synchronized double getBalance(User u){
        try {
            String usrname = u.getUsername();
            Connection con = DataSource.getCon();
            PreparedStatement statement = con.prepareStatement("Select balance " +
                                                                   "WHERE username = ?");
            statement.setString(1, usrname);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
