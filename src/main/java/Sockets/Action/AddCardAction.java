package Sockets.Action;

import Gameplay.Games.Card;

import java.util.List;

public class AddCardAction {
    String userame;
    List<Card> cards;

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
