package Gameplay.Games.Blackjack;

import User.User;

public class BlackJackDealer extends BlackjackPlayer{

    public BlackJackDealer(){
        this(null, 0);
    }

    private BlackJackDealer(User user, double playingMoney) {
        super(user, playingMoney);
    }

    public void reset(){
        clearCards();
    }
}
