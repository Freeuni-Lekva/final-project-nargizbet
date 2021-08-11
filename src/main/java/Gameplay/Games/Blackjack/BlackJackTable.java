package Gameplay.Games.Blackjack;

import Database.BalanceDAO;
import Gameplay.Games.Game;
import Gameplay.Room.Chat;
import Gameplay.Room.Table;
import User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BlackJackTable implements Table {
    private int capacity;
    private List<BlackjackPlayer> players;
    private Set<BlackjackPlayer> waitingPlayers;
    private Chat chat;
    private BlackjackGame game;
    private int currCap;
    private BalanceDAO balanceDAO;

    public BlackJackTable(BlackjackGame g, BalanceDAO balanceDAO){
        this.balanceDAO = balanceDAO;
        capacity = g.getCapacity();
        players = new ArrayList<>();
        waitingPlayers = new HashSet<>();
        chat = new Chat();
        game = g;
    }

    public synchronized boolean addUser(BlackjackPlayer player){
        if(currCap==capacity) return false;
        players.add(player);
        waitingPlayers.add(player);
        currCap++;
        return true;
    }

    public synchronized void removeUser(BlackjackPlayer player){
        for(var p : players){
            if(p.getUser().equals(player)) player = p;
        }
        if(player == null) return;
        players.remove(player);
        waitingPlayers.remove(player);
        game.removePlayer(player);
        currCap--;
        updateBalance(player);
    }
    public synchronized List<User> getUsers(){
        return players.stream().map(BlackjackPlayer::getUser).collect(Collectors.toList());
    }
    public int getMaxCapacity(){return capacity;}

    public synchronized int getCurrentCapacity(){return currCap;}

    public Chat getChat(){
        return chat;
    }

    public Game getGame(){
        return game;
    }

    private void updateBalance(BlackjackPlayer p){
        p.getUser().setBalance(p.getUser().getBalance() + p .getPlayingMoney());
        balanceDAO.addBalance(p.getUser());
    }

    public synchronized void askMove(BlackjackPlayer player) throws IOException {
        player.getSession().getBasicRemote().sendText("askMove");
    }

    public synchronized void askBet(BlackjackPlayer player) throws IOException {
        player.getSession().getBasicRemote().sendText("askBet");
    }

    public synchronized void move(String move){

    }

    public synchronized void bet(BlackjackPlayer player, double bet){

    }

    public synchronized void startGame(){

    }

    /**
     * sends the results for all the wins and losses
     * also calls askBet on first player to restart the game cycle
     */
    public synchronized void endGame() throws IOException {
        game.endGame();
        for(BlackjackPlayer p : players){
            if(game.lost(p)) {
                p.getSession().getBasicRemote().sendText("playerLost");
            }else{
                p.getSession().getBasicRemote().sendText("playerWon");
            }
        }
        askBet(players.get(0));
    }


    /*
    askMove() -> getCurrPlayer get their session and ask for move
    askBet(player) -> ask given player their bet/ give chance to leave game
    move(move) -> getCurrPlayer, make move + send move data to every player then if(game is not over) askMove() else endGame()
    bet(player, bet) -> sets given bet for given player // count number of bets set if
    endGame() -> end game calculate and send results
    startGame() -> clears table for everyone and calls askMove
     */

}
