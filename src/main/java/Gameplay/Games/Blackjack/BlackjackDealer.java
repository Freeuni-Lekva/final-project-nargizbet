package Gameplay.Games.Blackjack;

import User.User;

import javax.websocket.Session;


public class BlackjackDealer extends BlackjackPlayer{

    public static final User DEALER_USER = new User("dealer", "", "" ,"");

    public BlackjackDealer(){
        this(DEALER_USER, 0, null);
    }

    private BlackjackDealer(User user, double playingMoney, Session session) {
        super(user, playingMoney, session);
    }

    public void reset(){
        clearCards();
    }
}
