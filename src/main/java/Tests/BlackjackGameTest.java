package Tests;

import Gameplay.Games.Blackjack.BlackjackGame;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import Sockets.Action.*;
import User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.*;

public class BlackjackGameTest extends TestCase {

    User dummyUser1 = new User("1", "", "", "");
    User dummyUser2 = new User("2", "", "", "");
    User dummyUser3 = new User("3", "", "", "");
    User dummyUser4 = new User("4", "", "", "");
    BlackjackPlayer player1;
    BlackjackPlayer player2;
    BlackjackPlayer player3;
    BlackjackPlayer player4;

    class DummyDeck extends Deck{
        private Queue<Card> deck;

        public DummyDeck(List<Card> cardList){
            deck = new LinkedList<>();
            for(int i = 0; i < cardList.size(); ++i){
                deck.add(cardList.get(i));
            }
        }

        @Override
        public Card getTopCard() {
            return deck.poll();
        }

        @Override
        public void shuffleDeck() {
            return;
        }

        @Override
        public void generateFreshDeck() {
            return;
        }
    }

    List<Card> fullDeck;
    BlackjackPlayer p1, p2, p3, p4;
    ArrayList<BlackjackPlayer> RandGamePlayers;
    @Before
    protected void setUp(){
        p1 = new BlackjackPlayer(new User("a","","",""),1000, null);
        p2 = new BlackjackPlayer(new User("b","","",""),1000, null);
        p3 = new BlackjackPlayer(new User("c","","",""),1000, null);
        p4 = new BlackjackPlayer(new User("d","","",""),1000, null);
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

        player1 = new BlackjackPlayer(dummyUser1, 10000, null);
        player2 = new BlackjackPlayer(dummyUser2, 10000, null);
        player3 = new BlackjackPlayer(dummyUser3, 10000, null);
        player4 = new BlackjackPlayer(dummyUser4, 10000, null);
    }


    public void testTinyGame(){
        DummyDeck dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9)));

        BlackjackGame game = new BlackjackGame(dummyDeck);

        player1.setBet(10000);
        game.addPlayer(player1);
        game.startGame();
        game.stand();
        assertTrue(game.isDealersTurn());
        game.dealerTurn();
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);

        dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(4), fullDeck.get(5), fullDeck.get(6)));
        game = new BlackjackGame(dummyDeck);

        game.addPlayer(player1);

        player1.setBet(20000);
        game.startGame();
        game.stand();
        assertTrue(game.isDealersTurn());
        game.dealerTurn();
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 0.0);
    }

    public void testTinyGameWithHit(){
        DummyDeck dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9), fullDeck.get(10), fullDeck.get(11), fullDeck.get(12)));

        BlackjackGame game = new BlackjackGame(dummyDeck);

        player1.setBet(10000);
        game.addPlayer(player1);
        game.startGame();
        game.addCard();
        assertTrue(!game.busted(player1));
        game.addCard();
        assertTrue(game.busted(player1));
        assertTrue(game.isDealersTurn());
        game.dealerTurn();
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 0.0);

        player1.addMoneyWon(10000);
        player1.clearCards();
        player1.setBet(10000);
        dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9), fullDeck.get(10)));

        game = new BlackjackGame(dummyDeck);
        game.addPlayer(player1);
        game.startGame();
        game.addCard();
        assertTrue(!game.busted(player1));
        game.stand();
        assertTrue(game.isDealersTurn());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);
    }


    public void testMultiplayerGame(){
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < 14; ++i){
            cards.add(fullDeck.get(i));
        }
        DummyDeck dummyDeck = new DummyDeck(cards);
        BlackjackGame game = new BlackjackGame(dummyDeck);

        ArrayList<BlackjackPlayer> blackjackPlayer = new ArrayList<>();
        //dealer 5
        player1.setBet(10000); // 9
        player2.setBet(10000); // 13
        player3.setBet(10000); // 17
        player4.setBet(10000); // 20
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        game.startGame();

        game.addCard();
        assertTrue(!game.busted(player1)); // player1: 19
        game.stand();
        game.addCard();
        assertTrue(game.busted(player2)); // player2: 23
        assertTrue(game.removePlayer(player3));
        game.stand(); // player 4 20
        assertTrue(game.isDealersTurn());
        game.dealerTurn();
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);
        assertEquals(player2.getPlayingMoney(), 0.0);
        assertEquals(player2.getPlayingMoney(), 0.0);
        assertEquals(player4.getPlayingMoney(), 20000.0);

    }


    public void testRandomGame(){
        for(int i=0; i<RandGamePlayers.size();i++){
            RandGamePlayers.get(i).setBet(100);
        }
        BlackjackGame blackjackGame = new BlackjackGame(new Deck());
        RandGamePlayers.stream().forEach((blackjackPlayer -> blackjackGame.addPlayer(blackjackPlayer)));
        blackjackGame.startGame();
        for(int i=0; i<4; i++) blackjackGame.stand();
        blackjackGame.endGame();
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
        BlackjackGame blackjackGame = new BlackjackGame(new Deck());
        RandGamePlayers.stream().forEach((blackjackPlayer -> blackjackGame.addPlayer(blackjackPlayer)));
        blackjackGame.startGame();
        while(!blackjackGame.isDealersTurn()){
            blackjackGame.addCard();
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
        BlackjackGame blackjackGame = new BlackjackGame(new Deck());
        RandGamePlayers.stream().forEach((blackjackPlayer -> blackjackGame.addPlayer(blackjackPlayer)));
        blackjackGame.startGame();
        RandGamePlayers.remove(p1);
        blackjackGame.removePlayer(p1);
        for(int i=0; i<3; i++) blackjackGame.stand();
        blackjackGame.endGame();
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
        BlackjackGame blackjackGame = new BlackjackGame(new Deck());
        RandGamePlayers.stream().forEach((blackjackPlayer -> blackjackGame.addPlayer(blackjackPlayer)));
        blackjackGame.startGame();

        blackjackGame.removePlayer(p1);
        RandGamePlayers.remove(p1);
        blackjackGame.stand();
        blackjackGame.removePlayer(p2);
        RandGamePlayers.remove(p2);
        blackjackGame.stand();
        blackjackGame.endGame();
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

    public void test1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Card c1 = new Card(Card.Suit.DIAMONDS, "A");
        Card c2 = new Card(Card.Suit.DIAMONDS, "2");
        Card c3 = new Card(Card.Suit.SPADES, "2");

        List<Card> cards = Arrays.asList(c1, c2, c3);
        AddCardAction rs = new AddCardAction();
        rs.setCards(cards);
        Action action = rs;
        System.out.println(objectMapper.writeValueAsString(action));
    }

    public void testGetters() {
        BlackjackGame bjg = new BlackjackGame();
        assertEquals("Blackjack", bjg.getName());
        assertEquals(4, bjg.getCapacity());
        assertEquals("Blackjack.png", bjg.getImageName());
    }

}
