package Sockets.Action;

public class AskBetAction implements Action{
    private static final String TYPE = "AskBetAction";
    String username;
    double maxAmount;

    public double getMaxAmount() { return maxAmount; }

    public void setMaxAmount(double maxAmount) { this.maxAmount = maxAmount; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getType() { return TYPE; }
}
