package Sockets.Coder;

import Sockets.Action.Action;
import Sockets.Action.BetAction;
import Sockets.Action.MoveAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

/* bet
type: "bet",
amount: 10
 */

/* move
type: "move",
move: "hit"
 */

public class BlackjackActionDecoder implements Decoder.Text<Action>{
    @Override
    public Action decode(String s) throws DecodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = null;
        try {
            data = objectMapper.readValue(s, new TypeReference<Map<String,Object>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String type = (String) data.get("type");
        if(type.equals("move")){
            MoveAction action = new MoveAction();
            if(((String) data.get("move")).equals("hit")) action.setMove(MoveAction.Move.Hit);
            else action.setMove(MoveAction.Move.Stand);
            return action;
        }else if(type.equals("bet")){
            BetAction action = new BetAction();
            action.setAmount(Integer.valueOf((String) data.get("amount")));
            return action;
        }
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = null;
        try {
            data = objectMapper.readValue(s, new TypeReference<Map<String,Object>>(){});
            if(data.size() == 2 && data.get("type") != null &&
                    (data.containsKey("move") ||
                    data.containsKey("amount"))) return true;
            else return false;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
