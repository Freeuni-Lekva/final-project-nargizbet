package Sockets.Action;


public class MoveAction implements Action{
    private static final String TYPE = "MoveAction";

    public enum Move {Hit, Stand}

    private Move move;

    @Override
    public String getType() { return TYPE; }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

}
