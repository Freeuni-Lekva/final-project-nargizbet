package Gameplay.Games.Blackjack;

import java.util.HashSet;
import java.util.Set;

import Gameplay.Games.Card;
import User.User;

public class BlackjackPlayer {

	private User user;
	private double playingMoney;
	private double bet;
	private Set<Card> currentCards;
	
	public BlackjackPlayer(User user, double playingMoney) {
		this.user = user;
		this.playingMoney = playingMoney;
		this.bet = 0;
		this.currentCards = new HashSet<>();
	}
	
	public User getUser() {
		return user;
	}

	public double getPlayingMoney() {
		return playingMoney;
	}

	public double getBet() {
		return bet;
	}

	public void setBet(double bet) {
		playingMoney += this.bet;
		playingMoney -= bet;
		
		this.bet = bet;
	}
	
	public void addMoneyWon(double moneyWon) {
		bet = 0;
		playingMoney += moneyWon;
	}

	public Set<Card> getCurrentCards() {
		return currentCards;
	}
	
	public void addCard(Card card) {
		currentCards.add(card);
	}
	
	public void clearCards() {
		currentCards.clear();
	}
	
	public void betLost() {
		bet = 0;
	}
	
	public int getPoints() {
		int points = 0;
		int numAces = 0;
		
		for (Card card : currentCards) {
			points += card.getPoints();
			
			if (card.getValue().equals("A"))
				numAces++;
		}
		
		while (points > 21 && numAces > 0) {
			points -= 10;	// ace point changes from 11 to 1
			numAces--;
		}
		
		return points;
	}
	
}
