package Sockets.Action;

public class NextPlayerAction implements Action{
    private static final String TYPE = "NextPlayerAction";
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
