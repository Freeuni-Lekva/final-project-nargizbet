package Sockets;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(
        value = "/game/blackjack/{tableId}",
        configurator = BlackjackConfigurator.class)
public class BlackjackWebsocket {

    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId) {

    }

    @OnMessage
    public void onMessage(Session session, String msg) {

    }

    @OnClose
    public void onClose(Session session, CloseReason reason){

    }


}
