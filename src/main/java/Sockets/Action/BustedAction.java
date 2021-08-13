package Sockets.Action;

public class BustedAction implements Action{
    private static final String TYPE = "BustedAction";
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
