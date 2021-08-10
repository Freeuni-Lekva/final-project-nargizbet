package Blackjack;

import Database.BalanceDAO;
import java.util.ArrayList;
import java.util.List;


interface BlackjackPlayer{}

public class Blackjack {
    private ArrayList<BlackjackPlayer> inGamePlayers;
    private int currPlayer;
    private BalanceDAO balanceDAO;
    private BlackjackPlayer dealer;
    private Deck deck;

//Gigi
    synchronized public void removePlayer(BlackjackPlayer player){
        int indexToRemove = inGamePlayers.indexOf(player);
        if(indexToRemove<currPlayer) currPlayer--;
        player.betLost();
        inGamePlayers.remove(player);
    }

    synchronized public void endGame(){
        for(int i=0; i<inGamePlayers.size(); i++){
            BlackjackPlayer currPlayer = inGamePlayers.get(i);
            if(dealer.getPoint()>currPlayer.getPoint() || currPlayer.getPoint()>21) currPlayer.betLost();
            else currPlayer.addMoneyWon();
        }
    }
    //setBets aketebs table an servleti?
    synchronized public void startGame(ArrayList<BlackjackPlayer> players){
        inGamePlayers = new ArrayList<BlackjackPlayer>().addAll(players);
        dealer = new BlackjackPlayer();
        deck = new Deck();
        deck.shuffle();
        dealer.addCard();
        dealer.addCard();
        for(int i=0; i<inGamePlayers.size(); i++) {
            inGamePlayers.get(i).addCard();
            inGamePlayers.get(i).addCard();
        }
        inGamePlayers.add(dealer);
        currPlayer = 0;
    }


//Nargizi
    synchronized public void addCard(){
        BlackjackPlayer player = players.get(currPlayer);
        player.addCard(deck.getTopCard());
        if(busted(player)) switchTurn();
    }

    synchronized boolean busted(BlackjackPlayer player){
        return player.countPoints() > 21;
    }

    synchronized public void stand(){
        switchTurn();
    }

    synchronized public BlackjackPlayer getCurrentPlayer(){
        return players.get(currPlayer);
    }

    synchronized private void switchTurn(){
        ++currPlayer;
    }

}
