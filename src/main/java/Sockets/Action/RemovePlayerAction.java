package Sockets.Action;

public class RemovePlayerAction implements Action{

    private static final String TYPE = "RemovePlayerAction";
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
