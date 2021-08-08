package Gameplay.Games;

public class Slots implements Game{
    @Override
    public String getName() {
        return "Slots";
    }

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public String getDataBaseName() {
        return "slots";
    }

    @Override
    public String getImageName() {
        return "Slots.jpeg";
    }


}
