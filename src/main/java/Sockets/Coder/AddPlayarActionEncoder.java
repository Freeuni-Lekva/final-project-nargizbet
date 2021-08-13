package Sockets.Coder;

import Sockets.Action.AddCardAction;
import Sockets.Action.AddPlayerAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class AddPlayarActionEncoder implements Encoder.Text<AddPlayerAction> {

    @Override
    public String encode(AddPlayerAction addPlayerAction) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(addPlayerAction);
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
