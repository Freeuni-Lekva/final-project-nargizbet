package Sockets.Action;

public class BetAction implements Action{
    private static final String TYPE = "BetAction";
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String getType() { return TYPE; }
}
