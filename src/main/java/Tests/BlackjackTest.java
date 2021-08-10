package Tests;

import Gameplay.Games.Blackjack.Blackjack;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import User.User;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.*;

public class BlackjackTest extends TestCase {

    User dummyUser = new User("", "", "", "");
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


    @Before
    protected void setUp(){
        fullDeck = new ArrayList<>();

        Card.Suit[] suits = Card.Suit.values();
        for (int i = 0; i < suits.length; i++)
            for (int j = 0; j < Card.values.length; j++)
                fullDeck.add(new Card(suits[i], Card.values[j]));

        player1 = new BlackjackPlayer(dummyUser, 10000);
        player2 = new BlackjackPlayer(dummyUser, 10000);
        player3 = new BlackjackPlayer(dummyUser, 10000);
        player4 = new BlackjackPlayer(dummyUser, 10000);
    }


    public void testTinyGame(){
        DummyDeck dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9)));

        Blackjack game = new Blackjack(dummyDeck);

        player1.setBet(10000);
        ArrayList<BlackjackPlayer> blackjackPlayer = new ArrayList<>();
        blackjackPlayer.add(player1);
        game.startGame(blackjackPlayer);
        game.stand();
        assertTrue(game.isGameOver());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);

        dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(4), fullDeck.get(5), fullDeck.get(6)));
        game = new Blackjack(dummyDeck);
        player1.clearCards();

        player1.setBet(20000);
        game.startGame(blackjackPlayer);
        game.stand();
        assertTrue(game.isGameOver());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 0.0);
    }

    public void testTinyGameWithHit(){
        DummyDeck dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9), fullDeck.get(10), fullDeck.get(11), fullDeck.get(12)));

        Blackjack game = new Blackjack(dummyDeck);

        player1.setBet(10000);
        ArrayList<BlackjackPlayer> blackjackPlayer = new ArrayList<>();
        blackjackPlayer.add(player1);
        game.startGame(blackjackPlayer);
        assertTrue(game.addCard());
        assertFalse(game.addCard());
        assertTrue(game.isGameOver());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 0.0);

        player1.addMoneyWon(10000);
        player1.clearCards();
        player1.setBet(10000);
        dummyDeck = new DummyDeck(Arrays.asList(fullDeck.get(0), fullDeck.get(1),
                fullDeck.get(2), fullDeck.get(3), fullDeck.get(8), fullDeck.get(9), fullDeck.get(10)));

        game = new Blackjack(dummyDeck);
        game.startGame(blackjackPlayer);
        assertTrue(game.addCard());
        game.stand();
        assertTrue(game.isGameOver());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);
    }

    public void testMultiplayerGame(){
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < 14; ++i){
            cards.add(fullDeck.get(i));
        }
        DummyDeck dummyDeck = new DummyDeck(cards);
        Blackjack game = new Blackjack(dummyDeck);

        ArrayList<BlackjackPlayer> blackjackPlayer = new ArrayList<>();
        //dealer 5
        player1.setBet(10000); // 9
        player2.setBet(10000); // 13
        player3.setBet(10000); // 17
        player4.setBet(10000); // 20
        blackjackPlayer.add(player1);
        blackjackPlayer.add(player2);
        blackjackPlayer.add(player3);
        blackjackPlayer.add(player4);

        game.startGame(blackjackPlayer);

        assertTrue(game.addCard()); // player1: 19
        game.stand();
        assertFalse(game.addCard()); // player2: 23
        game.removePlayer(player3);
        game.stand(); // player 4 20
        assertTrue(game.isGameOver());
        game.endGame();
        assertEquals(player1.getPlayingMoney(), 20000.0);
        assertEquals(player2.getPlayingMoney(), 0.0);
        assertEquals(player2.getPlayingMoney(), 0.0);
        assertEquals(player4.getPlayingMoney(), 20000.0);



    }






}
