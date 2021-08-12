package Sockets.Action;

import Gameplay.Games.Card;

import java.util.List;

public class AddDealerCardAction implements Action{
    private static final String TYPE = "AddDealerCardAction";
    private List<Card> cards;

    @Override
    public String getType(){return TYPE;}

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
