package Gameplay.Games.Blackjack;

import Database.BalanceDAO;
import Gameplay.Games.Game;
import Gameplay.Room.Chat;
import Gameplay.Room.Table;
import User.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BlackJackTable implements Table {
    private int capacity;
    private Set<BlackjackPlayer> players;
    private Set<BlackjackPlayer> waitingPlayers;
    private Chat chat;
    private BlackjackGame game;
    private int currCap;
    private BalanceDAO balanceDAO;

    public BlackJackTable(BlackjackGame g, BalanceDAO balanceDAO){
        this.balanceDAO = balanceDAO;
        capacity = g.getCapacity();
        players = new HashSet<>();
        waitingPlayers = new HashSet<>();
        chat = new Chat();
        game = g;
    }

    public synchronized boolean addUser(User u, double amount){
        if(currCap==capacity) return false;
        players.add(new BlackjackPlayer(u, amount));
        waitingPlayers.add(new BlackjackPlayer(u, amount));
        currCap++;
        return true;
    }

    public synchronized void removeUser(User u){
        BlackjackPlayer player = null;
        for(var p : players){
            if(p.getUser().equals(u)) player = p;
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
}
