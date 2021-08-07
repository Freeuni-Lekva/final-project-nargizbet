package Tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DataSource;
import Database.StatsDAO;
import Database.UserDAO;
import Gameplay.Games.Blackjack;
import Gameplay.Games.Game;
import User.User;

public class StatsDAOTest {

	StatsDAO statsDao;
	UserDAO userDao;
	Connection con;
	
	User usr1, usr2, usr3, usr4;
	Blackjack game;
	
	@Before
	public void setUp() throws Exception {
		statsDao = new StatsDAO();
		userDao = new UserDAO();
		game = new Blackjack();
		con = DataSource.getCon();
		
		usr1 = new User("usr1", "usr", "1", "psw1");
		usr2 = new User("usr2", "usr", "2", "psw2");
		usr3 = new User("usr3", "usr", "3", "psw3");
		usr4 = new User("usr4", "usr", "4", "psw4");
		
		Statement statement = con.createStatement();
		userDao.addUser(usr1);
		userDao.addUser(usr2);
		userDao.addUser(usr3);
		userDao.addUser(usr4);
		
		statement.executeUpdate("INSERT INTO blackjack VALUES (\"usr1\", 1);");
		statement.executeUpdate("INSERT INTO blackjack VALUES (\"usr2\", 2);");
		statement.executeUpdate("INSERT INTO blackjack VALUES (\"usr3\", 3);");
		statement.executeUpdate("INSERT INTO blackjack VALUES (\"usr4\", 4);");
	}
	
	@After
	public void tearDown() throws Exception {
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM blackjack;");
		statement.executeUpdate("DELETE FROM users;");
		
		con.close();
	}

	@Test
	public void testGetWins() {
		assertEquals(1, statsDao.getWins(usr1, game));
		assertEquals(2, statsDao.getWins(usr2, game));
		assertEquals(3, statsDao.getWins(usr3, game));
		assertEquals(4, statsDao.getWins(usr4, game));
	}

	@Test
	public void testAddWin() {
		statsDao.addWin(usr1, game);
		statsDao.addWin(usr1, game);
		statsDao.addWin(usr1, game);
		
		statsDao.addWin(usr4, game);
		statsDao.addWin(usr4, game);
		
		User newUser = new User("newUser", "new", "User", "newPsw");
		userDao.addUser(newUser);
		statsDao.addWin(newUser, game);
		statsDao.addWin(newUser, game);
		

		assertEquals(4, statsDao.getWins(usr1, game));
		assertEquals(6, statsDao.getWins(usr4, game));
		assertEquals(2, statsDao.getWins(newUser, game));
	}

	@Test
	public void testGetLeaderboard() {
		List<Entry<User, Integer>> result = statsDao.getLeaderboard(game, 4);
		assertEquals(usr4, result.get(0).getKey());
		assertEquals(4, result.get(0).getValue().intValue());
		assertEquals(usr3, result.get(1).getKey());
		assertEquals(3, result.get(1).getValue().intValue());
		assertEquals(usr2, result.get(2).getKey());
		assertEquals(2, result.get(2).getValue().intValue());
		assertEquals(usr1, result.get(3).getKey());
		assertEquals(1, result.get(3).getValue().intValue());
		
		statsDao.addWin(usr3, game);
		statsDao.addWin(usr3, game);
		
		result = statsDao.getLeaderboard(game, 4);
		assertEquals(usr3, result.get(0).getKey());
		assertEquals(5, result.get(0).getValue().intValue());
		assertEquals(usr4, result.get(1).getKey());
		assertEquals(4, result.get(1).getValue().intValue());
	}

	@Test
	public void testGetUserPlace() {
		assertEquals(4, statsDao.getUserPlace(usr1, game));
		assertEquals(3, statsDao.getUserPlace(usr2, game));
		assertEquals(2, statsDao.getUserPlace(usr3, game));
		assertEquals(1, statsDao.getUserPlace(usr4, game));
		
		User newUser = new User("newUser", "new", "User", "newPsw");
		userDao.addUser(newUser);
		statsDao.addWin(newUser, game);
		statsDao.addWin(newUser, game);
	
		assertEquals(4, statsDao.getUserPlace(newUser, game));
		assertEquals(5, statsDao.getUserPlace(usr1, game));
	}
	
	@Test
	public void testThreadSafety() throws InterruptedException {
		Thread[] threads = new Worker[4];
		
		threads[0] = new Worker(usr1);
		threads[1] = new Worker(usr2);
		threads[2] = new Worker(usr3);
		threads[3] = new Worker(usr4);
		
		for (int i = 0 ; i < 4; i++) 
			threads[i].run();
		
		for (int i = 0 ; i < 4; i++) 
			threads[i].join();
		
		assertEquals(101, statsDao.getWins(usr1, game));
		assertEquals(102, statsDao.getWins(usr2, game));
		assertEquals(103, statsDao.getWins(usr3, game));
		assertEquals(104, statsDao.getWins(usr4, game));
		
		assertEquals(4, statsDao.getUserPlace(usr1, game));
		assertEquals(3, statsDao.getUserPlace(usr2, game));
		assertEquals(2, statsDao.getUserPlace(usr3, game));
		assertEquals(1, statsDao.getUserPlace(usr4, game));
	}
	
	private class Worker extends Thread {
		User user;
		
		public Worker(User user) {
			this.user = user;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				statsDao.getWins(user, game);
				statsDao.addWin(user, game);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
