package Tests;

import Gameplay.Games.Blackjack;
import Gameplay.Games.BlackjackPlayer;
import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import User.User;
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
    BlackjackPlayer p1, p2, p3, p4;
    ArrayList<BlackjackPlayer> RandGamePlayers;
    @Before
    protected void setUp(){
        p1 = new BlackjackPlayer(new User("a","","",""),1000);
        p2 = new BlackjackPlayer(new User("b","","",""),1000);
        p3 = new BlackjackPlayer(new User("c","","",""),1000);
        p4 = new BlackjackPlayer(new User("d","","",""),1000);
        RandGamePlayers = new ArrayList<>();
        RandGamePlayers.add(p1);
        RandGamePlayers.add(p2);
        RandGamePlayers.add(p3);
        RandGamePlayers.add(p4);
        fullDeck = new ArrayList<>();

        Card.Suit[] suits = Card.Suit.values();
        for (int i = 0; i < suits.length; i++)
            for (int j = 0; j < Card.values.length; j++)
                fullDeck.add(new Card(suits[i], Card.values[j]));
    }


    public void testSmallGame(){

    }

    public void testRandomGame(){
        for(int i=0; i<RandGamePlayers.size();i++){
            RandGamePlayers.get(i).setBet(100);
        }
        Blackjack blackjack = new Blackjack(new Deck());
        blackjack.startGame(RandGamePlayers);
        for(int i=0; i<4; i++) blackjack.stand();
        blackjack.endGame();
        int sum = 0;
        for(int i=0; i<RandGamePlayers.size(); i++){
            sum += RandGamePlayers.get(i).getPlayingMoney();
            System.out.print(RandGamePlayers.get(i).getPlayingMoney() +"  ");
        }
        System.out.println();
        assertTrue(sum<=4400);
        assertTrue(sum>=3600);
    }
    public void testRandomGameWithBusts(){
        for(int i=0; i<RandGamePlayers.size();i++){
            RandGamePlayers.get(i).setBet(100);
        }
        Blackjack blackjack = new Blackjack(new Deck());
        blackjack.startGame(RandGamePlayers);
        while(RandGamePlayers.indexOf(blackjack.getCurrentPlayer())!=-1){
            blackjack.addCard();
        }
        int sum = 0;
        for(int i=0; i<RandGamePlayers.size(); i++){
            sum += RandGamePlayers.get(i).getPlayingMoney();
            System.out.print(RandGamePlayers.get(i).getPlayingMoney() +"  ");
        }
        System.out.println();
        assertTrue(sum==3600);
    }
    public void testRandomGameWith1Remove(){
        for(int i=0; i<RandGamePlayers.size();i++){
            RandGamePlayers.get(i).setBet(100);
        }
        Blackjack blackjack = new Blackjack(new Deck());
        blackjack.startGame(RandGamePlayers);
        RandGamePlayers.remove(p1);
        blackjack.removePlayer(p1);
        for(int i=0; i<3; i++) blackjack.stand();
        blackjack.endGame();
        int sum = 0;
        for(int i=0; i<RandGamePlayers.size(); i++){
            sum += RandGamePlayers.get(i).getPlayingMoney();
            System.out.print(RandGamePlayers.get(i).getPlayingMoney() +"  ");
        }
        System.out.println();
        assertTrue(p1.getPlayingMoney()==900);
        assertTrue(sum<=3300);
    }
    public void testRandomGameWith2Remove(){
        for(int i=0; i<RandGamePlayers.size();i++){
            RandGamePlayers.get(i).setBet(100);
        }
        Blackjack blackjack = new Blackjack(new Deck());
        blackjack.startGame(RandGamePlayers);

        blackjack.removePlayer(p1);
        RandGamePlayers.remove(p1);
        blackjack.stand();
        blackjack.removePlayer(p2);
        RandGamePlayers.remove(p2);
        blackjack.stand();
        blackjack.endGame();
        int sum = 0;
        for(int i=0; i<RandGamePlayers.size(); i++){
            sum += RandGamePlayers.get(i).getPlayingMoney();
            System.out.print(RandGamePlayers.get(i).getPlayingMoney() +"  ");
        }
        System.out.println();
        assertTrue(p1.getPlayingMoney()==900);
        assertTrue(p2.getPlayingMoney()==900);
        assertTrue(sum<=2200);
        assertTrue(sum>=1800);
    }
    
}
