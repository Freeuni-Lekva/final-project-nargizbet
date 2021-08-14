package Sockets.Action;

public class ResultAction implements Action{

    private static final String TYPE = "ResultAction";
    String result;
    double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String getType() { return TYPE; }
}
