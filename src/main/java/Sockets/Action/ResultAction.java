package Sockets.Action;

public class ResultAction implements Action{

    private static final String TYPE = "ResultAction";
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String getType() { return TYPE; }
}
