package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.StatsDAO;
import Database.UserDAO;
import User.User;

public class StatsDaoTest {

	StatsDAO statsDao;
	UserDAO userDao;

	User usr1, usr2, usr3, usr4;
	
	@Before
	public void setUp() throws Exception {
		this.statsDao = new StatsDAO();
		
		usr1 = new User("usr1", "usr", "1", "psw");
		usr2 = new User("usr2", "usr", "2", "psw");
		usr3 = new User("usr3", "usr", "3", "psw");
		usr4 = new User("usr4", "usr", "4", "psw");
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetWins() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddWin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLeaderboard() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserPlace() {
		fail("Not yet implemented");
	}

}
