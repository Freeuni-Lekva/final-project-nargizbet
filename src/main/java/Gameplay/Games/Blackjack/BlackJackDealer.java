package Gameplay.Games.Blackjack;

import User.User;

import javax.jms.Session;

public class BlackJackDealer extends BlackjackPlayer{

    public BlackJackDealer(){
        this(null, 0, null);
    }

    private BlackJackDealer(User user, double playingMoney, Session session) {
        super(user, playingMoney, session);
    }

    public void reset(){
        clearCards();
    }
}
