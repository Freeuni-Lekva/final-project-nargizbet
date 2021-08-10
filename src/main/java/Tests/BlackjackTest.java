package Tests;

import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class BlackjackTest extends TestCase {

    class DummyDeck extends Deck{
        private Queue<Card> deck;

        public DummyDeck(List<Card> cardList){
            super();
            deck = cardList.stream().collect(Collectors.toCollection(LinkedList::new));
        }
    }

    List<Card> fullDeck;


    @Before
    protected void setUp(){
        fullDeck = new ArrayList<>();

        Card.Suit[] suits = Card.Suit.values();
        for (int i = 0; i < suits.length; i++)
            for (int j = 0; j < Card.values.length; j++)
                fullDeck.add(new Card(suits[i], Card.values[j]));
    }


    public void testSmallGame(){

    }






}
