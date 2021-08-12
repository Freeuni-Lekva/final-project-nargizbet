package Sockets;

import Gameplay.Games.Blackjack.BlackjackTable;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import Gameplay.Games.Blackjack.BlackjackGame;
import Sockets.Action.Action;
import Sockets.Action.BetAction;
import Sockets.Action.MoveAction;
import Sockets.Coder.*;

import Database.BalanceDAO;
import User.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(
        value = "/game/blackjack/{tableId}",
        configurator = BlackjackConfigurator.class,
        encoders = {BustedActionEncoder.class, AddCardActionEncoder.class,
                ClearActionEncoder.class, NextPlayerActionEncoder.class},
        decoders = {BlackjackActionDecoder.class})
public class BlackjackWebsocket {

    /**
     * @param session
     * @param config
     * @param tableId
     * @param money
     * @throws IOException
     * adds the new player in the table and updates the its balance(-playing money).
     * if the game has not started yet, calls askBet, therefore starting the game cycle.
     */
    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId, @PathParam("value") String money) throws IOException {
        ServletContext context = (ServletContext)(config.getUserProperties().get("context"));
        BalanceDAO BDAO = (BalanceDAO)(session.getUserProperties().get("BalanceDAO"));
        double playingMoney = Double.valueOf(money);
        BlackjackTable BJT = (BlackjackTable) (context.getAttribute(tableId));
        BlackjackGame BJGame = (BlackjackGame)BJT.getGame();
        HttpSession ses = (HttpSession)config.getUserProperties().get("session");
        User user = (User)ses.getAttribute("User");

        user.setBalance(user.getBalance()-playingMoney);
        BDAO.setBalance(user);
        BlackjackPlayer player = new BlackjackPlayer(user,playingMoney,session);

        BJT.addUser(player);

        session.getUserProperties().put("player",player);
        session.getUserProperties().put("tableId",tableId);
        session.getUserProperties().put("BalanceDao",context.getAttribute("BalanceDao"));
        session.getUserProperties().put("table",BJT);

        if(BJGame.isOngoing()) {
            player.getSession().getBasicRemote().sendText("askBet");
        }
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

    /**
     * @param session
     * @param reason
     * @throws IOException
     *
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        BlackjackPlayer player = (BlackjackPlayer)session.getUserProperties().get("player");
        BlackjackTable BJT = (BlackjackTable) (session.getUserProperties().get("tableId"));
        BalanceDAO BDAO = (BalanceDAO)(session.getUserProperties().get("BalanceDAO"));

        BJT.removeUser(player);

        User user = player.getUser();
        double beforeBalance = BDAO.getBalance(user);
        user.setBalance(beforeBalance+player.getPlayingMoney());
        BDAO.setBalance(user);
        player.getSession().getBasicRemote().sendText("clearHand");
    }


}
