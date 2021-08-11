package Sockets.Action;

import Gameplay.Games.Card;

import java.util.List;

public class AddCardAction implements Action{
    private static final String TYPE = "AddCardAction";
    private String userame;
    private List<Card> cards;

    @Override
    public String getType(){return TYPE;}

    public String getUserame() {
        return userame;
    }

    public void setUserame(String userame) {
        this.userame = userame;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


}
