package Sockets.Coder;

import Sockets.Action.Action;
import Sockets.Action.AddPlayerAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class BlackjackActionEncoder implements Encoder.Text<Action> {

    @Override
    public String encode(Action action) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(action);
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

