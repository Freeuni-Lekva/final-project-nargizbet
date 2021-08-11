package Sockets;

import Database.BalanceDAO;
import Gameplay.Games.Blackjack.BlackJackTable;
import Gameplay.Games.Blackjack.BlackjackGame;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import User.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(
        value = "/game/blackjack/{tableId}/{value}",
        configurator = BlackjackConfigurator.class)
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
        BlackJackTable BJT = (BlackJackTable) (context.getAttribute(tableId));
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
    public void onMessage(Session session, String msg) {

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
        BlackJackTable BJT = (BlackJackTable) (session.getUserProperties().get("tableId"));
        BalanceDAO BDAO = (BalanceDAO)(session.getUserProperties().get("BalanceDAO"));

        BJT.removeUser(player);

        User user = player.getUser();
        double beforeBalance = BDAO.getBalance(user);
        user.setBalance(beforeBalance+player.getPlayingMoney());
        BDAO.setBalance(user);
        player.getSession().getBasicRemote().sendText("clearHand");
    }


}
