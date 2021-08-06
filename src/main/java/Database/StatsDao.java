package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.sql.DataSource;

import Gameplay.Games.Game;
import User.User;

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
		int wins = 0;
		
		PreparedStatement statement = con.prepareStatement(
			"SELECT wins FROM ? WHERE username = ?;"
		);
		
		statement.setString(1, game.getDataBaseName());
		statement.setString(2, user.getUsername());
		
		ResultSet res = statement.executeQuery();
		
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
		
		statement.setString(1, game.getDataBaseName());
		statement.setString(2, user.getUsername());
		
		statement.executeUpdate();
	}
	
	/**
	 * The method returns a leaderboard of users in a sorted order. Whomever has the most wins,
	 * is the first and so on. retruns a list of leader-wins pair.
	 * @param game
	 * @return returns a leaderboard list of leader(user)-wins(integer) pair.
	 * @throws SQLException for an invalid game.
	 */
	public synchronized List<Entry<User, Integer>> getLeaderboard(Game game, int leaderNum) 
			throws SQLException 
	{
		List<Entry<User, Integer>> result = new ArrayList<>();
		
		PreparedStatement statement = con.prepareStatement(
			"SELECT * 1 FROM ? ORDER BY wins DESC;"
		);
		
		statement.setString(1, game.getDataBaseName());
		ResultSet res = statement.executeQuery();

		for (int i = 0; i < leaderNum || res.next(); i++) {
			User user = UserDao.getUser(res.getString(1));
			Integer wins = res.getInt(2);
			
			Entry<User, Integer> pair = new SimpleEntry<>(user, wins);
			result.add(pair);
		}
		
		return result;
	}
	
	/**
	 * the method returns what place the user is on a game leaderboard.
	 * it throws an SQLException if either user or a game is invalid.
	 * @param user
	 * @param game
	 * @return return users place on a game leaderboard
	 * @throws SQLException for invalid game or user
	 */
	public synchronized int getUserPlace(User user, Game game) throws SQLException {
		int place = 0;
		
		PreparedStatement statement = con.prepareStatement(
			"SELECT win.r"
			+ "FROM (SELECT row_number() OVER (ORDER BY wins DESC) r, username u FROM ?) AS win"
			+ "WHERE win.u = ?;"
		); 
		
		statement.setString(1, game.getDataBaseName());
		statement.setString(2, user.getUsername());
		
		ResultSet res = statement.executeQuery();
		
		if (res.next())
			place = res.getInt(1);
		else 
			throw new SQLException("Result not found");
		
		return place;
	}
	
	// temporary
	static class UserDao {
		public static User getUser(String username) {
			return null;
		}
	}
}
