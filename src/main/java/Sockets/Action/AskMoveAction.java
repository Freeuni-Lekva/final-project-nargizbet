package Sockets.Action;

public class AskMoveAction implements Action{
    private static final String TYPE = "AskMoveAction";
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getType() { return TYPE; }
}
