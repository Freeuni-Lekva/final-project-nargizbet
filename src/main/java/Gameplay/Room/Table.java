package Gameplay.Room;

import Gameplay.Games.Game;
import User.User;

import java.util.ArrayList;

public class Table {
    private int capacity;
    private ArrayList<User> users;
    private Chat chat;
    private Game game;
    private int currCap;
    public Table(Game g){
        capacity = g.getCapacity();
        users = new ArrayList<User>(capacity);
        chat = new Chat();
        game = g;
    }
    public synchronized boolean addUser(User u){
        if(currCap==capacity) return false;
        users.add(u);
        currCap++;
        return true;
    }
    public synchronized void removeUser(User u){
        users.remove(u);
        currCap--;
    }
    public synchronized ArrayList<User> getUsers(){
        return users;
    }
    public int getMaxCapacity(){return capacity;}

    public synchronized int getCurrentCapacity(){return currCap;}

    public Chat getChat(){
        return chat;
    }

    public Game getGame(){
        return game;
    }
}
