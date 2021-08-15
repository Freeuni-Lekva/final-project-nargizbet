package Sockets.Action;

import Gameplay.Games.Card;

public class FlipCardAction implements Action{
    private static final String TYPE = "FlipCardAction";

    private Card card;

    public void setCard(Card c){
        card = c;
    }

    public Card getCard(){
        return card;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
