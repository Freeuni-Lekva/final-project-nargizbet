package Sockets;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class BlackjackConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig conf, HandshakeRequest req,
                                HandshakeResponse resp){
        conf.getUserProperties().put("context", ((HttpSession)req.getHttpSession()).getServletContext());
    }
}
