package Gameplay.Games.Blackjack;

import User.User;

import javax.websocket.Session;


public class BlackjackDealer extends BlackjackPlayer{

    public BlackjackDealer(){
        this(null, 0, null);
    }

    private BlackjackDealer(User user, double playingMoney, Session session) {
        super(user, playingMoney, session);
    }

    public void reset(){
        clearCards();
    }
}
