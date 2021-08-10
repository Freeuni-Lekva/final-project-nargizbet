package Gameplay.Games.Blackjack;

import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import Gameplay.Games.Game;
import User.User;

import java.util.ArrayList;

public class Blackjack implements Game {


    private static final String NAME = "Blackjack";
    private static final int CAPACITY = 4;
    private static final String DATABASE_NAME = "blackjack";
    private static final String IMAGE_NAME = "Blackjack.png";

    private ArrayList<BlackjackPlayer> inGamePlayers;
    private int currPlayer;
    private BlackjackPlayer dealer;
    private Deck deck;


    @Override
    public String getName() { return NAME; }
    @Override
    public int getCapacity() { return CAPACITY; }
    @Override
    public String getDataBaseName() { return DATABASE_NAME; }
    @Override
    public String getImageName(){ return IMAGE_NAME; }

    public Blackjack(){ deck = new Deck(); }

    public Blackjack(Deck deck){
        this.deck = deck;
    }


    synchronized public void removePlayer(BlackjackPlayer player){
        int indexToRemove = inGamePlayers.indexOf(player);
        if(indexToRemove<currPlayer) currPlayer--;
        player.betLost();
        inGamePlayers.remove(player);
    }

    synchronized public void endGame(){
        dealerTurn();

        for(int i=0; i < inGamePlayers.size(); i++){
            BlackjackPlayer currPlayer = inGamePlayers.get(i);
            if((dealer.getPoints()>currPlayer.getPoints() && !busted(dealer)) || currPlayer.getPoints()>21) currPlayer.betLost();
            else{ currPlayer.addMoneyWon(currPlayer.getBet()*2); }
        }
    }

    //setBets aketebs table an servleti?
    synchronized public void startGame(ArrayList<BlackjackPlayer> players){
        inGamePlayers = new ArrayList<BlackjackPlayer>();
        inGamePlayers.addAll(players);
        //TO-DO: ADD dealer player type
        dealer = new BlackjackPlayer(new User("","","",""), Integer.MAX_VALUE);

        deck.generateFreshDeck();
        deck.shuffleDeck();
        dealer.addCard(deck.getTopCard());
        dealer.addCard(deck.getTopCard());
        for(int i=0; i<inGamePlayers.size(); i++) {
            inGamePlayers.get(i).addCard(deck.getTopCard());
            inGamePlayers.get(i).addCard(deck.getTopCard());
        }
        currPlayer = 0;
    }

    synchronized private void dealerTurn(){
        while(dealer.getPoints() < 17){
            Card card = deck.getTopCard();
            dealer.addCard(card);

        }
    }

    synchronized public boolean addCard(){
        BlackjackPlayer player = inGamePlayers.get(currPlayer);
        player.addCard(deck.getTopCard());
        if(busted(player)){
            switchTurn();
            return false;
        }
        return true;
    }

    synchronized boolean busted(BlackjackPlayer player){
        return player.getPoints() > 21;
    }

    synchronized public void stand(){
        switchTurn();
    }

    synchronized public BlackjackPlayer getCurrentPlayer(){
        return inGamePlayers.get(currPlayer);
    }

    synchronized public boolean isGameOver(){
        return currPlayer == inGamePlayers.size();
    }

    synchronized private void switchTurn(){
        ++currPlayer;
    }

}
