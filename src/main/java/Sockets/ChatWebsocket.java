package Sockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

@ServerEndpoint("/chat/{tableId}")
public class ChatWebsocket {

    @OnOpen
    public void onOpen(final Session session, EndpointConfig config, @PathParam("tableId") String tableId) {
        System.out.println("User Connected");
        session.getUserProperties().put("id", tableId);
    }

    @OnMessage
    public void onMessage(Session session, String msg, @PathParam("tableId") String tableId) {
        try {
            for (Session sess : session.getOpenSessions()) {
                if (sess.isOpen() && sess.getUserProperties().containsKey("id") && sess.getUserProperties().get("id").equals(tableId)){
                    sess.getBasicRemote().sendText(msg);
                }
            }
        } catch (IOException e) {}
    }
}
