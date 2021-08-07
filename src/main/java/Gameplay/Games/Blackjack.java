package Gameplay.Games;

public class Blackjack implements Game {
    @Override
    public String getName() {
        return "Blackjack";
    }

    @Override
    public int getCapacity() {
        return 4;
    }

    @Override
    public String getDataBaseName() {
        return "blackjack";
    }
}
