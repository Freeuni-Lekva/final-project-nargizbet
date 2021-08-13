package Sockets.Action;

import Gameplay.Games.Blackjack.BlackjackDealer;
import Gameplay.Games.Blackjack.BlackjackPlayer;

import java.util.List;

public class DrawTableAction implements Action{

    private static final String TYPE = "DrawTableAction";
    private BlackjackDealer dealer;
    private BlackjackPlayer currPlayer;
    private List<BlackjackPlayer> players;

    public BlackjackDealer getDealer() {
        return dealer;
    }

    public void setDealer(BlackjackDealer dealer) {
        this.dealer = dealer;
    }

    public BlackjackPlayer getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(BlackjackPlayer currPlayer) {
        this.currPlayer = currPlayer;
    }

    public List<BlackjackPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<BlackjackPlayer> players) {
        this.players = players;
    }

    @Override
    public String getType() { return TYPE; }
}
