package Gameplay.Games;

import User.User;

public interface Game {

    /**
     * @return the name of the game;
     */
    String getName();

    /**
     * @return maximum capacity for the game;
     */
    int getCapacity();


    /**
     * @return name of the database ... 
     */
    String getDataBaseName();


    String getImageName();

}
