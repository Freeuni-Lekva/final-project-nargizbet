package Blackjack;

import Database.BalanceDAO;

import java.util.List;


interface BlackjackPlayer{}

public class Blackjack {
    private List<BlackjackPlayer> players;
    private int currPlayer;
    private BalanceDAO balanceDAO;


//Gigi
    synchronized public void removePlayer(BlackjackPlayer player){

    }

    synchronized public void endGame(){

    }

    synchronized public void startGame(BlackjackPlayer[] players){

    }


//Nargizi
    synchronized public void addCard(){

    }

    synchronized public void stand(){

    }

    synchronized public BlackjackPlayer getCurrentPlayer(){
        return null;
    }

    synchronized private void switchTurn(){

    }





}
