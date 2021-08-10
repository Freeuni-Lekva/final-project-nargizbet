package Gameplay.Games;

import User.User;

public class Card {
	
	public static final int BLACK = 0x000000;
	public static final int RED = 0xFF0000;
	
	public static enum Suit {
		SPADES, // ♠
		CLUBS, // ♣
		HEARTS, // ♥
		DIAMONDS // ♦
	};
	
	public static final String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	
	private Suit suit;
	private String value;
	private int points;
	private int color;
	
	public Card(Suit suit, String value) {
		assert(isValue(value));
		
		this.suit = suit;
		this.value = value ;
		
		if (suit == Suit.CLUBS || suit == Suit.SPADES)
			color = BLACK;
		else 
			color = RED;
		
		// value.matches("\\d+) return true if string is an integer
		if (value.matches("\\d+")) points = Integer.parseInt(value);
		else if (value.equals("A")) points = 11;
		else points = 10;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getColor() {
		return color;
	}
	
	private boolean isValue(String value) {
		for (int i = 0; i < values.length; i++) 
			if (values[i].equals(value)) 
				return true;
		
		return false;
	}
	
	@Override
    public boolean equals(Object otherCard) {
	    if (otherCard == this)
	        return true;
	    
		if (otherCard == null)
			return false;
		
	    if (!(otherCard instanceof Card))
	        return false;
		
		return this.suit == ((Card)otherCard).suit 
			   && this.value.equals(((Card)otherCard).value);
    }

    @Override
    public int hashCode() {
        return suit.hashCode() + value.hashCode();
    }
}
