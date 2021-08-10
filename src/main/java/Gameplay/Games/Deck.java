package Gameplay.Games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deck {
	
	private List<Card> cleanDeck;
	private Queue<Card> deck;
	
	/**
	 * generates clean deck;
	 */
	public Deck() {
		cleanDeck = new ArrayList<>();
		
		Card.Suit[] suits = Card.Suit.values();
		for (int i = 0; i < suits.length; i++) 
			for (int j = 0; j < Card.values.length; j++)
				cleanDeck.add(new Card(suits[i], Card.values[j]));
	
		deck = copyListToQueue(cleanDeck);
	}
	
	/**
	 * switches old deck with new, clean and sorted deck.
	 */
	public void generateFreshDeck() {
		deck = copyListToQueue(cleanDeck);
	}
	
	/**
	 * shuffles the current deck
	 */
	public void shuffleDeck() {
		List<Card> shuffledDeck = new ArrayList<>();
		shuffledDeck = copyQueueToList(deck);
		
		Collections.shuffle(shuffledDeck);
		
		deck = copyListToQueue(shuffledDeck);
	}
	
	public Card getTopCard() {
		return deck.poll();
	}
	
	public int getDeckSize() {
		return deck.size();
	}
	
	private Queue<Card> copyListToQueue(List<Card> list) {
		Queue<Card> queue = new LinkedList<>();
		
		for (Card card : list)
			queue.add(card);
		
		return queue;
	}
	
	private List<Card> copyQueueToList(Queue<Card> queue) {
		List<Card> list = new ArrayList<>();
		
		for (Card card : queue)
			list.add(card);
	
		return list;
	}
	
}
