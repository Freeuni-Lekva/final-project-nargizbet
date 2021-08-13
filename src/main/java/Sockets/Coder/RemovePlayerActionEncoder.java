package Sockets.Coder;

import Sockets.Action.DrawTableAction;
import Sockets.Action.RemovePlayerAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class RemovePlayerActionEncoder implements Encoder.Text<RemovePlayerAction> {

    @Override
    public String encode(RemovePlayerAction removePlayerAction) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(removePlayerAction);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

