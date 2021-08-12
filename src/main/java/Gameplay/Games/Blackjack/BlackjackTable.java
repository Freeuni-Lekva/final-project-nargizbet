package Gameplay.Games.Blackjack;

import Database.BalanceDAO;
import Gameplay.Games.Card;
import Gameplay.Games.Game;
import Gameplay.Room.Chat;
import Gameplay.Room.Table;
import Sockets.Action.*;
import User.User;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BlackjackTable implements Table {
    private int capacity;
    private List<BlackjackPlayer> players;
    private Set<BlackjackPlayer> waitingPlayers;
    private Chat chat;
    private BlackjackGame game;
    private int currCap;
    private BalanceDAO balanceDAO;
    private int betCount;

    public BlackjackTable(BlackjackGame g, BalanceDAO balanceDAO){
        this.balanceDAO = balanceDAO;
        capacity = g.getCapacity();
        players = new ArrayList<>();
        waitingPlayers = new HashSet<>();
        chat = new Chat();
        game = g;
        betCount = 0;
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

    public synchronized void askMove(){

    }

    public synchronized void askBet(BlackjackPlayer player){

    }

    public synchronized void move(MoveAction move){
        BlackjackPlayer player = game.getCurrentPlayer();
        if(move.getMove() == MoveAction.Move.Hit){
            Card card = game.addCard();
            boolean busted = game.busted(player);
            players.stream().forEach(player1 -> sendDrawCardsAction(player, Arrays.asList(card)));
            if(busted) players.stream().forEach(player1 -> sendBustedPlayerAction(player));
        }else{
            game.stand();
        }

        players.stream().forEach(player1 -> sendNextPlayerAction(game.getCurrentPlayer()));
        if(!game.isDealersTurn()) askMove();
        else endGame();
    }

    public synchronized void bet(BlackjackPlayer player, BetAction bet){
        player.setBet(bet.getAmount());
        ++betCount;
        if(betCount == players.size()) startGame();
    }

    public synchronized void startGame(){
        betCount = 0;

        waitingPlayers.stream().forEach(player -> game.addPlayer(player));
        waitingPlayers.clear();

        players.stream().forEach(player -> sendClearAction(player));

    }

    public synchronized void endGame(){

    }

    private void sendBustedPlayerAction(BlackjackPlayer player){
        BustedAction bustedAction = new BustedAction();
        bustedAction.setUsername(player.getUser().getUsername());
        try {
            player.getSession().getBasicRemote().sendObject(bustedAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendNextPlayerAction(BlackjackPlayer player){
        NextPlayerAction nextPlayerAction = new NextPlayerAction();
        nextPlayerAction.setUsername(player.getUser().getUsername());
        try {
            player.getSession().getBasicRemote().sendObject(nextPlayerAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendDrawCardsAction(BlackjackPlayer player, List<Card> cards){
        AddCardAction addCardAction = new AddCardAction();
        addCardAction.setCards(cards);
        addCardAction.setUserame(player.getUser().getUsername());
        try {
            player.getSession().getBasicRemote().sendObject(addCardAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendClearAction(BlackjackPlayer player){
        ClearAction clearAction = new ClearAction();
        try {
            player.getSession().getBasicRemote().sendObject(clearAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
}
