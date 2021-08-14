package Gameplay.Games.Blackjack;

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
    private int betCount;

    public BlackjackTable(BlackjackGame g){
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
        sendAddPlayerAction(player);
        sendDrawTableAction(player);
        return true;
    }

    public synchronized void removeUser(BlackjackPlayer player){
        players.remove(player);
        waitingPlayers.remove(player);
        game.removePlayer(player);
        currCap--;
        sendRemovePlayerAction(player);
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

    public synchronized void askMove(){
        sendAskMoveAction(game.getCurrentPlayer());
    }

    public synchronized void askBet(BlackjackPlayer player){
        sendAskBetAction(player);
    }

    public synchronized void move(MoveAction move){
        BlackjackPlayer player = game.getCurrentPlayer();
        if(move.getMove() == MoveAction.Move.Hit){
            Card card = game.addCard();
            boolean busted = game.busted(player);
            sendDrawCardsAction(player, Arrays.asList(card));
            if(busted) players.stream().forEach(player1 -> sendBustedPlayerAction(player));
        }else{
            game.stand();
        }
        if(!game.isDealersTurn()){
            sendNextPlayerAction(game.getCurrentPlayer());
            askMove();
        }
        else{
            sendNextPlayerAction(game.getDealer());
            endGame();
        }
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

        game.startGame();
        players.stream().forEach(player -> { sendDrawCardsAction(player, player.getCurrentCards());});
        sendDrawCardsAction(game.getDealer(), game.getDealer().getCurrentCards());
        askMove();
    }

    public synchronized void endGame(){
        List<Card> cards = game.dealerTurn();
        sendDrawCardsAction(game.getDealer(), cards);

        game.endGame();
        for(BlackjackPlayer p : players){
            if(p.getLastGameResult() == BlackjackPlayer.LOST) {
                    sendResultAction(p,"playerResult");
            }else if(p.getLastGameResult() == BlackjackPlayer.WON){
                    sendResultAction(p,"playerWon");
            }else if(p.getLastGameResult() == BlackjackPlayer.PUSH){
                    sendResultAction(p, "playerPush");
            }
        }
        players.stream().forEach(player -> sendClearAction(player));
        players.stream().forEach(player -> askBet(player));
    }

    private void sendResultAction(BlackjackPlayer player, String result) {
        ResultAction resultAction = new ResultAction();
        resultAction.setResult(result);
        try {
            player.getSession().getBasicRemote().sendObject(resultAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendAskMoveAction(BlackjackPlayer player){
        AskMoveAction askMoveAction = new AskMoveAction();
        try {
            player.getSession().getBasicRemote().sendObject(askMoveAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendAskBetAction(BlackjackPlayer player){
        AskBetAction askBetAction = new AskBetAction();
        askBetAction.setMaxAmount(player.getPlayingMoney());
        try {
            player.getSession().getBasicRemote().sendObject(askBetAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
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

        players.stream().forEach(player1 -> {
            try {
                player1.getSession().getBasicRemote().sendObject(nextPlayerAction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendDrawCardsAction(BlackjackPlayer player, List<Card> cards){
        AddCardAction addCardAction = new AddCardAction();
        addCardAction.setCards(cards);
        addCardAction.setUserame(player.getUser().getUsername());
        players.stream().forEach(player1 -> {
            try {
                player1.getSession().getBasicRemote().sendObject(addCardAction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendDrawTableAction(BlackjackPlayer player){
        DrawTableAction drawTableAction = new DrawTableAction();
        drawTableAction.setDealer(game.getDealer());
        drawTableAction.setCurrPlayer(game.getCurrentPlayer());
        drawTableAction.setPlayers(players);

        try {
            player.getSession().getBasicRemote().sendObject(drawTableAction);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    private void sendRemovePlayerAction(BlackjackPlayer player){
        RemovePlayerAction removePlayerAction = new RemovePlayerAction();
        removePlayerAction.setUsername(player.getUser().getUsername());

        players.stream().forEach(player1 -> {
            try {
                player1.getSession().getBasicRemote().sendObject(removePlayerAction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendAddPlayerAction(BlackjackPlayer player){
        AddPlayerAction addPlayerAction = new AddPlayerAction();
        addPlayerAction.setUsername(player.getUser().getUsername());

        players.stream().forEach(player1 -> {
            if(!player1.getUser().getUsername().equals(player.getUser().getUsername())){
            try {
                player1.getSession().getBasicRemote().sendObject(addPlayerAction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }});

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
