package Sockets.Action;

public class AskBetAction implements Action{
    private static final String TYPE = "AskBetAction";
    double maxAmount;

    public double getMaxAmount() { return maxAmount; }

    public void setMaxAmount(double maxAmount) { this.maxAmount = maxAmount; }


    @Override
    public String getType() { return TYPE; }
}
