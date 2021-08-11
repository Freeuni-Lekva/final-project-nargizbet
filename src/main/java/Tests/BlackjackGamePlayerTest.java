package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import Gameplay.Games.Card;
import org.junit.Before;
import org.junit.Test;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import User.User;

public class BlackjackGamePlayerTest {

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

	@Test
	public void testCards() {
		Card card1 = new Card(Card.Suit.SPADES, "A");
		Card card2 = new Card(Card.Suit.DIAMONDS, "3");
		Card card3 = new Card(Card.Suit.DIAMONDS, "2");
		
		player.addCard(card1);
		player.addCard(card2);
		player.addCard(card3);
		
		Set<Card> realResult = new HashSet<>();
		realResult.add(card1);
		realResult.add(card2);
		realResult.add(card3);
		
		assertEquals(realResult, player.getCurrentCards());
		
		player.clearCards();
		player.addCard(card3);
		
		realResult.clear();
		realResult.add(card3);

		assertEquals(realResult, player.getCurrentCards());
	}

	@Test
	public void testGetPoints() {
		Card card1 = new Card(Card.Suit.SPADES, "A");
		Card card2 = new Card(Card.Suit.HEARTS, "J");
		Card card3 = new Card(Card.Suit.DIAMONDS, "5");
		Card card4 = new Card(Card.Suit.CLUBS, "A");
		Card card5 = new Card(Card.Suit.HEARTS, "5");
		
		player.addCard(card1);
		assertEquals(11, player.getPoints());
		player.addCard(card2);
		assertEquals(21, player.getPoints());
		player.addCard(card3);
		assertEquals(16, player.getPoints());
		player.addCard(card4);
		assertEquals(17, player.getPoints());
		player.addCard(card5);
		assertEquals(22, player.getPoints());
	}

}
