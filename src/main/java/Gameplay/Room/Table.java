package Gameplay.Room;

import Gameplay.Games.Game;
import User.User;

import java.util.List;

public interface Table {

    boolean addUser(User u, double amount);

    void removeUser(User u);

    List<User> getUsers();

    int getMaxCapacity();

    int getCurrentCapacity();

    Chat getChat();

    Game getGame();
}
