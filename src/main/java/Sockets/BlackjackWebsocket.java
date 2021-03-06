package Sockets;

import Database.StatsDAO;
import Gameplay.Games.Blackjack.BlackjackTable;
import Gameplay.Games.Blackjack.BlackjackPlayer;
import Gameplay.Games.Blackjack.BlackjackGame;
import Sockets.Action.Action;
import Sockets.Action.BetAction;
import Sockets.Action.MoveAction;
import Sockets.Action.SkipAction;
import Sockets.Coder.*;

import Database.BalanceDAO;
import User.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.valueextraction.Unwrapping;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(
        value = "/game/blackjack/{tableId}/{amount}",
        configurator = BlackjackConfigurator.class,
        encoders = {BlackjackActionEncoder.class},
        decoders = {BlackjackActionDecoder.class})
public class BlackjackWebsocket {


    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId, @PathParam("amount") String money) throws IOException {
        System.out.println("CONNECTED black jack");
        ServletContext context = (((HttpSession)config.getUserProperties().get("session")).getServletContext());
        BalanceDAO BDAO = (BalanceDAO)(context.getAttribute("BalanceDAO"));
        double playingMoney = Double.valueOf(money);
        BlackjackTable BJT = ((List<BlackjackTable>)context.getAttribute("BlackjackTables")).get(Integer.valueOf(tableId));
        BlackjackGame BJGame = (BlackjackGame)BJT.getGame();
        HttpSession ses = (HttpSession)config.getUserProperties().get("session");
        User user = (User)ses.getAttribute("User");

        user.setBalance(user.getBalance()-playingMoney);
        BDAO.setBalance(user);
        BlackjackPlayer player = new BlackjackPlayer(user,playingMoney,session);

        session.getUserProperties().put("player",player);
        session.getUserProperties().put("tableId", tableId);
        session.getUserProperties().put("context", context);
        session.getUserProperties().put("table", BJT);
        BJT.addUser(player);


        if(!BJGame.isOngoing()) {
            BJT.askBet(player);
        }
    }

    @OnMessage
    public void onMessage(Session session, Action action) {
        BlackjackTable table = (BlackjackTable) session.getUserProperties().get("table");
        BlackjackPlayer player = (BlackjackPlayer) session.getUserProperties().get("player");

        if(action instanceof BetAction){
            table.bet(player, (BetAction) action);
        }else if(action instanceof MoveAction){
            table.move((MoveAction) action);
        }else if(action instanceof SkipAction){
            table.skip(player);
        }
    }


    //TO-DO update score in DAO
    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        BlackjackPlayer player = (BlackjackPlayer)session.getUserProperties().get("player");
        BlackjackTable BJT = (BlackjackTable) (session.getUserProperties().get("table"));
        ServletContext context = (ServletContext)(session.getUserProperties().get("context"));
        BalanceDAO BDAO = (BalanceDAO) context.getAttribute("BalanceDAO");
        StatsDAO SDAO = (StatsDAO) context.getAttribute("StatsDAO");

        BJT.removeUser(player);

        User user = player.getUser();
        double beforeBalance = BDAO.getBalance(user);
        user.setBalance(beforeBalance+player.getPlayingMoney());
        BDAO.setBalance(user);
        SDAO.addWin(player.getUser(), BJT.getGame(), player.getWins());
    }


}
