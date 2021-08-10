package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Gameplay.Games.Blackjack.BlackjackPlayer;
import User.User;

public class BlackjackPlayerTest {

	BlackjackPlayer player;
	
	@Before
	public void setUp() throws Exception {
		player = new BlackjackPlayer(new User("l", "d", "s", "d"), 1000);
	}

	@Test
	public void testSetterGetters() {
		assertEquals(1000, player.getPlayingMoney(), 1);
		
		player.setBet(100);
		assertEquals(100, player.getBet(), 1);
		assertEquals(900, player.getPlayingMoney(), 1);
		
		player.betLost();
		assertEquals(0, player.getBet(), 1);
		assertEquals(900, player.getPlayingMoney(), 1);
		
		player.setBet(100);
		player.setBet(800);
		assertEquals(100, player.getPlayingMoney(), 1);
		
		player.setBet(100);
		assertEquals(800, player.getPlayingMoney(), 1);
		player.addMoneyWon(300);
		assertEquals(1100, player.getPlayingMoney(), 1);
	}

//	@Test
//	public void testGetCurrentCards() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddCard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClearCards() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPoints() {
//		fail("Not yet implemented");
//	}

}
