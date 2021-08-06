package java.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

public class StatsDao {

	private Connection con;
	
	public StatsDao(DataSource dataSource) throws SQLException {
		con = dataSource.getConnection();
	}
	
	/**
	 * The method takes a user and a game and tries to return 
	 * a number of win by a user in a specified game.
	 * If either game or user is invalid, or user is not in a database,
	 * the method throws an SQLException. in Slots, number of wins means
	 * money won.
	 * @param user 
	 * @param game
	 * @return Returns a number of wins in a game by a user
	 * @throws SQLException for invalid user or game
	 */
	public synchronized int getWins(User user, Game game) throws SQLException {
		PreparedStatement statement = con.prepareStatement(
			"SELECT wins FROM ? WHERE username = ?;"
		);
		
		statement.setString(1, game.getDatabaseName());
		statement.setString(2, user.getUsername());
		
		ResultSet res = statement.executeQuery();
		int wins = 0;
		
		if (res.next()) {
			wins = res.getInt(1);
		} else
			throw new SQLException("No result found");
		
		return wins;	
	}
	
	/**
	 * If both user and game are valid, the method increments
	 * number of wins in a game from a user by one. if either 
	 * user or game is invalid, the method throws an SQLException
	 * @param user
	 * @param game
	 * @throws SQLException for invalid user or game
	 */
	public synchronized void addWin(User user, Game game) throws SQLException {
		PreparedStatement statement = con.prepareStatement(
			"UPDATE ? SET wins = wins + 1 WHERE username = ?;"
		);
		
		statement.setString(1, game.getDatabaseName());
		statement.setString(2, user.getUsername());
		
		statement.executeUpdate();
	}
	
	public synchronized List<Pair<User, Integer> getLeaderboard() {
		return null;
	}
	
	public synchronized int getUserPlace() {
		return 0;
	}
	
	
	
	// temporary
	interface User {
		public String getUsername();
	}
	
	// temorary
	interface Game {
		public String getDatabaseName();
	}
}
