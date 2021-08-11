package Gameplay.Games.Blackjack;

import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import Gameplay.Games.Game;
import User.User;

import java.util.ArrayList;

public class BlackjackGame implements Game {


    private static final String NAME = "Blackjack";
    private static final int CAPACITY = 4;
    private static final String DATABASE_NAME = "blackjack";
    private static final String IMAGE_NAME = "Blackjack.png";

    private ArrayList<BlackjackPlayer> inGamePlayers;
    private int currPlayer;
    private BlackJackDealer dealer;
    private Deck deck;
    private boolean ongoing;


    @Override
    public String getName() { return NAME; }
    @Override
    public int getCapacity() { return CAPACITY; }
    @Override
    public String getDataBaseName() { return DATABASE_NAME; }
    @Override
    public String getImageName(){ return IMAGE_NAME; }

    public BlackjackGame(){ this(new Deck()); }

    public BlackjackGame(Deck deck)
    {
        this.deck = deck;
        dealer = new BlackJackDealer();
        inGamePlayers = new ArrayList<>();
    }

    synchronized public boolean addPlayer(BlackjackPlayer player){
        if(isOngoing()) return false;
        inGamePlayers.add(player);
        return true;
    }


    synchronized public boolean removePlayer(BlackjackPlayer blackjackPlayer){
        int indexToRemove = inGamePlayers.indexOf(blackjackPlayer);
        if(indexToRemove == -1) return false;
        if(indexToRemove < currPlayer) currPlayer--;
        inGamePlayers.get(indexToRemove).betLost();
        inGamePlayers.remove(indexToRemove);
        return true;
    }

    synchronized public boolean lost(BlackjackPlayer player){
        return player.getBet()==0;
    }

    synchronized public void endGame(){
        dealerTurn();

        for(int i=0; i < inGamePlayers.size(); i++){
            BlackjackPlayer currPlayer = inGamePlayers.get(i);
            if((dealer.getPoints()>currPlayer.getPoints() && !busted(dealer)) || currPlayer.getPoints()>21) currPlayer.betLost();
            else{ currPlayer.addMoneyWon(currPlayer.getBet()*2); }
            inGamePlayers.get(i).clearCards();
        }
        dealer.reset();
        ongoing = false;
    }

    //setBets aketebs table an servleti?
    synchronized public void startGame(){

        deck.generateFreshDeck();
        deck.shuffleDeck();
        dealer.addCard(deck.getTopCard());
        dealer.addCard(deck.getTopCard());
        for(int i=0; i<inGamePlayers.size(); i++) {
            inGamePlayers.get(i).addCard(deck.getTopCard());
            inGamePlayers.get(i).addCard(deck.getTopCard());
        }
        currPlayer = 0;
        ongoing = true;
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

    synchronized BlackJackDealer getDealer() {return dealer;}

    synchronized boolean busted(BlackjackPlayer player){
        return player.getPoints() > 21;
    }

    synchronized public void stand(){
        switchTurn();
    }

    synchronized public BlackjackPlayer getCurrentPlayer(){
        return inGamePlayers.get(currPlayer);
    }

    synchronized public boolean isDealersTurn(){
        return currPlayer == inGamePlayers.size();
    }

    synchronized public boolean isOngoing() {return ongoing;}

    synchronized private void switchTurn(){
        ++currPlayer;
    }




}
