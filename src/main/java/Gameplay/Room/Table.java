package Gameplay.Room;

import Gameplay.Games.Game;

import java.util.ArrayList;
interface User{}
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

    synchronized boolean addUser(User u){
        if(currCap==capacity) return false;
        users.add(u);
        return true;
    }
    synchronized void removeUser(User u){
        users.remove(u);//?
    }
    synchronized ArrayList<User> getUsers(){
        return users;
    }

    Chat getChat(){
        return chat;
    }

    Game getGame(){
        return game;
    }
}
