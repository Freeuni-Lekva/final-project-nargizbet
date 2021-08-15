package Tests;

import static org.junit.Assert.*;

import Gameplay.Games.Blackjack.BlackjackTable;
import Gameplay.Games.Deck;
import Gameplay.Room.Table;
import org.junit.Before;
import org.junit.Test;

import Gameplay.Games.Card;

public class CardTest {

	Card card1, card2, card3, card4;
	
	@Before
	public void setUp() throws Exception {
		card1 = new Card(Card.Suit.CLUBS, "2");
		card2 = new Card(Card.Suit.HEARTS, "A");
		card3 = new Card(Card.Suit.DIAMONDS, "10");
		card4 = new Card(Card.Suit.SPADES, "K");
	}

	@Test
	public void testGetSuit() {
		assertEquals(Card.Suit.CLUBS, card1.getSuit());
		assertEquals(Card.Suit.HEARTS, card2.getSuit());
		assertEquals(Card.Suit.DIAMONDS, card3.getSuit());
		assertEquals(Card.Suit.SPADES, card4.getSuit());
	}

	@Test
	public void testGetValue() {
		assertEquals("2", card1.getValue());
		assertEquals("A", card2.getValue());
		assertEquals("10", card3.getValue());
		assertEquals("K", card4.getValue());
	}

	@Test
	public void testGetPoints() {
		assertEquals(2, card1.getPoints());
		assertEquals(11, card2.getPoints());
		assertEquals(10, card3.getPoints());
		assertEquals(10, card4.getPoints());
	}

	@Test
	public void testGetColor() {
		assertEquals(Card.BLACK, card1.getColor());
		assertEquals(Card.RED, card2.getColor());
		assertEquals(Card.RED, card3.getColor());
		assertEquals(Card.BLACK, card4.getColor());
	}

	@Test
	public void testEdgeCases(){
		assertFalse(card1.equals(null));
		assertFalse(card2.equals(new Deck()));
	}
}
