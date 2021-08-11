package Sockets;

import Gameplay.Games.Blackjack.BlackjackTable;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import Sockets.Action.Action;
import Sockets.Action.BetAction;
import Sockets.Action.MoveAction;
import Sockets.Coder.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/game/blackjack/{tableId}",
        configurator = BlackjackConfigurator.class,
        encoders = {BustedActionEncoder.class, AddCardActionEncoder.class,
                ClearActionEncoder.class, NextPlayerActionEncoder.class},
        decoders = {BlackjackActionDecoder.class})
public class BlackjackWebsocket {

    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId) {

    }

    @OnMessage
    public void onMessage(Session session, Action action) {
        BlackjackTable table = (BlackjackTable) session.getUserProperties().get("table");

        if(action instanceof BetAction){
            BlackjackPlayer player = (BlackjackPlayer) session.getUserProperties().get("player");
            table.bet(player, (BetAction) action);
        }else if(action instanceof MoveAction){
            table.move((MoveAction) action);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){

    }


}
