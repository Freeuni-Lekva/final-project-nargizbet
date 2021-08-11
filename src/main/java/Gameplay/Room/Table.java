package Gameplay.Room;

import Gameplay.Games.Blackjack.BlackjackPlayer;
import Gameplay.Games.Game;
import User.User;

import javax.jms.Session;
import java.util.List;

public interface Table {

    boolean addUser(BlackjackPlayer player);

    void removeUser(BlackjackPlayer player);

    List<User> getUsers();

    int getMaxCapacity();

    int getCurrentCapacity();

    Chat getChat();

    Game getGame();
}
