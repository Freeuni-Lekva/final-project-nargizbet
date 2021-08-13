package Sockets.Coder;

import Sockets.Action.AddPlayerAction;
import Sockets.Action.DrawTableAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DrawTableActionEncoder implements Encoder.Text<DrawTableAction> {

    @Override
    public String encode(DrawTableAction drawTableAction) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(drawTableAction);
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
