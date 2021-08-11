package Sockets;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/chat/{tableId}")
public class ChatWebsocket {

    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId) {
        session.getUserProperties().put("id", tableId);
        System.out.println(tableId);
        System.out.println("User Connected");
    }

    @OnMessage
    public void onMessage(Session session, String msg, @PathParam("tableId") String tableId) {
        try {
            for (Session sess : session.getOpenSessions()) {
                if (sess.isOpen() && sess.getUserProperties().get("id").equals(tableId)){
                    sess.getBasicRemote().sendText(msg);
                }
            }
        } catch (IOException e) {}
    }
}
