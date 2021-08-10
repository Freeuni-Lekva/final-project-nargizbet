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
	 * switches old deck with new, clean and sorted deck.
	 */
	public void generateFreshDeck() {
		if (cleanDeck == null) {
			cleanDeck = new ArrayList<>();
			
			Card.Suit[] suits = Card.Suit.values();
			for (int i = 0; i < suits.length; i++) 
				for (int j = 0; j < Card.values.length; j++)
					cleanDeck.add(new Card(suits[i], Card.values[j]));
		}
			
		copyListToQueue(cleanDeck, deck);
	}
	
	/**
	 * shuffles the current deck
	 */
	public void shuffleDeck() {
		List<Card> shuffledDeck = new ArrayList<>();
		copyQueueToList(deck, shuffledDeck);
		
		Collections.shuffle(shuffledDeck);
		
		copyListToQueue(shuffledDeck, deck);
	}
	
	public Card getTopCard() {
		return deck.poll();
	}
	
	public int getDeckSize() {
		return deck.size();
	}
	
	private void copyListToQueue(List<Card> list, Queue<Card> queue) {
		queue = new LinkedList<>();
		for (Card card : list)
			queue.add(card);
	}
	
	private void copyQueueToList(Queue<Card> queue, List<Card> list) {
		list = new ArrayList<>();
		for (Card card : queue)
			list.add(card);
	}
	
}
