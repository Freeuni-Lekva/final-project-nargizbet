package Gameplay.Games.Blackjack;

import Gameplay.Games.Card;
import Gameplay.Games.Deck;
import Gameplay.Games.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlackjackGame implements Game {


    private static final String NAME = "Blackjack";
    private static final int CAPACITY = 4;
    private static final String DATABASE_NAME = "blackjack";
    private static final String IMAGE_NAME = "Blackjack.png";

    private ArrayList<BlackjackPlayer> inGamePlayers;
    private int currPlayer;
    private BlackjackDealer dealer;
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
        dealer = new BlackjackDealer();
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


    synchronized public void endGame(){
        for(int i=0; i < inGamePlayers.size(); i++){
            BlackjackPlayer currPlayer = inGamePlayers.get(i);
            if((dealer.getPoints()>currPlayer.getPoints() && !busted(dealer)) || currPlayer.getPoints()>21){
                currPlayer.betLost();
                currPlayer.setLastGameResult(BlackjackPlayer.LOST);
            }
            else if(dealer.getPoints() != currPlayer.getPoints()){
                currPlayer.addMoneyWon(currPlayer.getBet()*2);
                currPlayer.increaseWins();
                currPlayer.setLastGameResult(BlackjackPlayer.WON);
            }else{
                currPlayer.addMoneyWon(currPlayer.getBet());
                currPlayer.setLastGameResult(BlackjackPlayer.PUSH);
            }
            inGamePlayers.get(i).clearCards();
        }
        dealer.reset();
        ongoing = false;
        currPlayer = 0;
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
        ongoing = true;
    }

    synchronized public List<Card> dealerTurn(){

        List<Card> cards = new ArrayList<>();
        while(dealer.getPoints() < 17){
            Card card = deck.getTopCard();
            cards.add(card);
            dealer.addCard(card);
        }
        return cards;
    }

    synchronized public Card addCard(){
        BlackjackPlayer player = inGamePlayers.get(currPlayer);
        Card card = deck.getTopCard();
        player.addCard(card);
        if(busted(player)){
            switchTurn();
        }
        return card;
    }

    synchronized BlackjackDealer getDealer() {return dealer;}

    synchronized public boolean busted(BlackjackPlayer player){
        return player.getPoints() > 21;
    }

    synchronized public void stand(){
        switchTurn();
    }

    synchronized public BlackjackPlayer getCurrentPlayer(){
        if (inGamePlayers.size() <= currPlayer) return null;
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
