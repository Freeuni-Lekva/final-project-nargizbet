package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Gameplay.Games.Deck;
import Gameplay.Games.Card;

public class DeckTest {

	Deck deck;
	
	@Before
	public void setUp() throws Exception {
		deck = new Deck();
	}
	
	@Test
	public void testGetTopCard() {
		for (int i = 0; i < Card.Suit.values().length; i++)
			for (int j = 0; j < Card.values.length; j++)
				assertEquals(new Card(Card.Suit.values()[i], Card.values[j]), deck.getTopCard());
	}

	@Test
	public void testGetDeckSize() {
		int realSize = Card.Suit.values().length * Card.values.length;
		
		for (int i = 0; i < Card.Suit.values().length / 2; i++)
			for (int j = 0; j < Card.values.length; j++) {
				realSize--;
				deck.getTopCard();
				assertEquals(realSize, deck.getDeckSize());
			}
		
		deck.shuffleDeck();
		
		for (int i = Card.Suit.values().length / 2; i < Card.Suit.values().length; i++)
			for (int j = 0; j < Card.values.length; j++) {
				realSize--;
				deck.getTopCard();
				assertEquals(realSize, deck.getDeckSize());
			}
	}
	
	@Test
	public void testGenerateFreshDeck() {
		deck.shuffleDeck();
		deck.getTopCard();
		deck.generateFreshDeck();
		testGetTopCard();
	}

}
