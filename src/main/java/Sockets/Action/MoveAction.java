package Sockets.Action;


public class MoveAction implements Action{

    public enum Move {Hit, Stand}

    private Move move;

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

}
