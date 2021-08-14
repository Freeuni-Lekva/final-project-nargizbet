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
				"SELECT wins FROM " + game.getDataBaseName() + " WHERE username = ?;"
			);
			
			statement.setString(1, user.getUsername());
			
			ResultSet res = statement.executeQuery();
			
			if (res.next()) {
				wins = res.getInt(1);
			} else {
				wins = 0;
			}

			con.close();
		} catch (SQLException e) { e.printStackTrace(); }
			
		return wins;	
	}
	
	/**
	 * If both user and game are valid, the method increments
	 * number of wins in a game from a user by one. if either 
	 * user or game is invalid, the method throws an SQLException
	 * @param user
	 * @param game
	 */
	public synchronized void addWin(User user, Game game, int amount) {
		try {
			Connection con = DataSource.getCon();
			
			PreparedStatement firstStatement = con.prepareStatement(
				"SELECT * FROM " + game.getDataBaseName() + " WHERE username = ?;"
			);
			
			firstStatement.setString(1, user.getUsername());
			
			ResultSet res = firstStatement.executeQuery();
			
			PreparedStatement secondStatement;
			if (res.next()) {
				secondStatement = con.prepareStatement(
					"UPDATE " + game.getDataBaseName() + " SET wins = wins + ? WHERE username = ?;"
				);
				
				secondStatement.setInt(1, amount);
				secondStatement.setString(2, user.getUsername());
			} else {
				secondStatement = con.prepareStatement(
					"INSERT INTO " + game.getDataBaseName() + " VALUES (?, ?);"
				);
				
				secondStatement.setString(1, user.getUsername());
				secondStatement.setInt(2, amount);
			}
			
			secondStatement.executeUpdate();
			
			con.close();
		} catch (SQLException e) { e.printStackTrace();}
	}


	public synchronized void addWin(User user, Game game) {
		addWin(user, game, 1);
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
			
			Statement statement = con.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * FROM " + game.getDataBaseName() + " ORDER BY wins DESC;"
			);
	
			UserDAO users = new UserDAO();
			for (int i = 0; res.next() && i < leaderNum; i++) {
				User user = users.getUser(res.getString(1));
				Integer wins = res.getInt(2);
				
				Entry<User, Integer> pair = new SimpleEntry<>(user, wins);
				result.add(pair);
			}
			
			con.close();
		} catch (SQLException e) { e.printStackTrace(); }
			
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
				"SELECT win.r "
				+ "FROM (SELECT row_number() "
				+ "OVER (ORDER BY wins DESC) r, username u FROM " + game.getDataBaseName() + ") AS win "
				+ "WHERE win.u = ?;"
			); 
			
			statement.setString(1, user.getUsername());
			
			ResultSet res = statement.executeQuery();
			
			if (res.next())
				place = res.getInt(1);
			else 
				place = 0;
			
			con.close();
		} catch (SQLException e) { e.printStackTrace(); }
			
		return place;
	}

	public synchronized void addMoneyGambled(User user, Double moneyGambled) {
		try {
			Connection con = DataSource.getCon();

			PreparedStatement firstStatement = con.prepareStatement("SELECT * FROM slots WHERE username = ?;");
			firstStatement.setString(1, user.getUsername());
			ResultSet rs = firstStatement.executeQuery();

			PreparedStatement secondStatement;
			if (rs.next()) {
				secondStatement = con.prepareStatement("UPDATE slots SET wins = wins + ? WHERE username = ?;");
				secondStatement.setDouble(1, moneyGambled);
				secondStatement.setString(2, user.getUsername());
			} else {
				secondStatement = con.prepareStatement("INSERT INTO slots VALUES (?, ?);");
				secondStatement.setString(1, user.getUsername());
				secondStatement.setDouble(2, moneyGambled);
			}
			secondStatement.executeUpdate();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized Double getMoneyGambled(User user) {
		double moneyGambled = 0;
		try {
			Connection con = DataSource.getCon();
			PreparedStatement statement = con.prepareStatement("SELECT wins FROM slots WHERE username = ?;");
			statement.setString(1, user.getUsername());
			ResultSet res = statement.executeQuery();
			if (res.next()) moneyGambled = res.getDouble(1);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moneyGambled;
	}

}