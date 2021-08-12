package Gameplay.Games.Blackjack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Gameplay.Games.Card;
import User.User;

import javax.websocket.Session;


public class BlackjackPlayer{

	private User user;
	private double playingMoney;
	private double bet;
	private List<Card> currentCards;
	private Session session;
	
	public BlackjackPlayer(User user, double playingMoney, Session session) {
		this.user = user;
		this.playingMoney = playingMoney;
		this.bet = 0;
		this.currentCards = new ArrayList<>();
		this.session = session;
	}

	public Session getSession() {return session;}

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

	public List<Card> getCurrentCards() {
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


	public boolean equals(Object other){
		if (other == null) return false;
		if(other instanceof BlackjackPlayer) return getUser().equals(((BlackjackPlayer) other).getUser());
		else return false;
	}

	@Override
	public int hashCode() {
		return user.hashCode();
	}
}
