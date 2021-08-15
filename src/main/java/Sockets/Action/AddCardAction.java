package Sockets.Action;

import Gameplay.Games.Card;

import java.util.List;

public class AddCardAction implements Action{
    private static final String TYPE = "AddCardAction";
    private String username;
    private List<Card> cards;
    private int numCardsInHand;

    @Override
    public String getType(){return TYPE;}

    public String getUsername() {
        return username;
    }

    public void setUserame(String userame) {
        this.username = userame;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setNumCardsInHand(int numCardsInHand){
        this.numCardsInHand = numCardsInHand;
    }

    public int getNumCardsInHand(){
       return numCardsInHand;
    }

}
