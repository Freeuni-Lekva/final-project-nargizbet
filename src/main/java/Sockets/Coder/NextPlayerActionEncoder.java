package Sockets.Coder;

import Sockets.Action.AddCardAction;
import Sockets.Action.NextPlayerAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class NextPlayerActionEncoder implements Encoder.Text<NextPlayerAction> {
    @Override
    public String encode(NextPlayerAction nextPlayerAction) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(nextPlayerAction);
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
