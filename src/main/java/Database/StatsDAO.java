package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Gameplay.Games.Game;
import User.User;

public class StatsDAO {
	
	/**
	 * The method takes a user and a game and tries to return 
	 * a number of win by a user in a specified game.
	 * If either game or user is invalid, or user is not in a database,
	 * the method throws an SQLException. in Slots, number of wins means
	 * money won.
	 * @param user 
	 * @param game
	 * @return Returns a number of wins in a game by a user
	 */
	public synchronized int getWins(User user, Game game) {
		int wins = 0;
		
		try {
			Connection con = DataSource.getCon();
		
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

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			e.printStackTrace();
		}
			
		return wins;	
	}
	
	/**
	 * If both user and game are valid, the method increments
	 * number of wins in a game from a user by one. if either 
	 * user or game is invalid, the method throws an SQLException
	 * @param user
	 * @param game
	 */
	public synchronized void addWin(User user, Game game) {
		try {
			Connection con = DataSource.getCon();
			
			PreparedStatement firstStatement = con.prepareStatement(
				"SELECT * FROM ? WHERE username = ?;"
			);
			
			firstStatement.setString(1, game.getDataBaseName());
			firstStatement.setString(2, user.getUsername());
			
			ResultSet res = firstStatement.executeQuery();
			
			PreparedStatement secondStatement;
			if (res.next()) {
				secondStatement = con.prepareStatement(
					"UPDATE ? SET wins = wins + 1 WHERE username = ?;"
				);
				
				secondStatement.setString(1, game.getDataBaseName());
				secondStatement.setString(2, user.getUsername());
			} else {
				secondStatement = con.prepareStatement(
					"INSERT INTO ? VALUES (?, 1);"
				);
				
				secondStatement.setString(1, game.getDataBaseName());
				secondStatement.setString(2, user.getUsername());
			}
			
			secondStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * The method returns a leaderboard of users in a sorted order. Whomever has the most wins,
	 * is the first and so on. returns a list of leader-wins pair.
	 * @param game
	 * @return returns a leaderboard list of leader(user)-wins(integer) pair.
	 */
	public synchronized List<Entry<User, Integer>> getLeaderboard(Game game, int leaderNum)	{
		List<Entry<User, Integer>> result = new ArrayList<>();
		
		try {
			Connection con = DataSource.getCon();
			
			PreparedStatement statement = con.prepareStatement(
				"SELECT * 1 FROM ? ORDER BY wins DESC;"
			);
			
			statement.setString(1, game.getDataBaseName());
			ResultSet res = statement.executeQuery();
	
			UserDAO users = new UserDAO();
			for (int i = 0; i < leaderNum || res.next(); i++) {
				User user = users.getUser(res.getString(1));
				Integer wins = res.getInt(2);
				
				Entry<User, Integer> pair = new SimpleEntry<>(user, wins);
				result.add(pair);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			e.printStackTrace();
		}
			
		return result;
	}
	
	/**
	 * the method returns what place the user is on a game leaderboard.
	 * it throws an SQLException if either user or a game is invalid.
	 * @param user
	 * @param game
	 * @return return users place on a game leaderboard
	 */
	public synchronized int getUserPlace(User user, Game game) {
		int place = 0;
		
		try {
			Connection con = DataSource.getCon();
			
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			e.printStackTrace();
		}
			
		return place;
	}
}
