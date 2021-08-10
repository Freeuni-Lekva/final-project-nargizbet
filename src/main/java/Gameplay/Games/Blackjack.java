package Gameplay.Games;

import Database.BalanceDAO;
import User.User;

import java.util.ArrayList;

public class Blackjack implements Game {

    private ArrayList<BlackjackPlayer> inGamePlayers;
    private int currPlayer;
    private BlackjackPlayer dealer;
    private Deck deck;


    @Override
    public String getName() {
        return "Blackjack";
    }

    @Override
    public int getCapacity() {
        return 4;
    }

    @Override
    public String getDataBaseName() {
        return "blackjack";
    }

    public String getImageName(){
        return "Blackjack.png";
    }
    public Blackjack(){

    }

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
        for(int i=0; i<inGamePlayers.size(); i++){
            BlackjackPlayer currPlayer = inGamePlayers.get(i);
            if(dealer.getPoints()>currPlayer.getPoints() || currPlayer.getPoints()>21) currPlayer.betLost();
            else currPlayer.betWon();
        }
    }

    //setBets aketebs table an servleti?
    synchronized public void startGame(ArrayList<BlackjackPlayer> players){
        inGamePlayers = new ArrayList<BlackjackPlayer>();
        inGamePlayers.addAll(players);
        //TO-DO: ADD dealer player type
        dealer = new BlackjackPlayer(new User("","","",""), 99999999);

        deck.generateFreshDeck();
        deck.shuffleDeck();
        dealer.addCard(deck.getTopCard());
        dealer.addCard(deck.getTopCard());
        for(int i=0; i<inGamePlayers.size(); i++) {
            inGamePlayers.get(i).addCard(deck.getTopCard());
            inGamePlayers.get(i).addCard(deck.getTopCard());
        }
        inGamePlayers.add(dealer);
        currPlayer = 0;
    }


    //Nargizi
    synchronized public void addCard(){
        BlackjackPlayer player = inGamePlayers.get(currPlayer);
        player.addCard(deck.getTopCard());
        if(busted(player)) switchTurn();
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

    synchronized private void switchTurn(){
        ++currPlayer;
    }

}
