package Blackjack;

import Database.BalanceDAO;
import java.util.List;


interface BlackjackPlayer{}

public class Blackjack {
    private List<BlackjackPlayer> players;
    private int currPlayer;
    private BalanceDAO balanceDAO;
    private Deck deck;


//Gigi
    synchronized public void removePlayer(BlackjackPlayer player){

    }

    synchronized public void endGame(){

    }

    synchronized public void startGame(BlackjackPlayer[] players){

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
