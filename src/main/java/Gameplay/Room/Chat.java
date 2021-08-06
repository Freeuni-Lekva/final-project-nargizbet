package Gameplay.Room;


import java.util.ArrayList;

public class Chat {

    private ArrayList<entry> entries;

    public Chat() {
        entries = new ArrayList<>();
    }

    public synchronized void addMessage(String name, String message) {
        entries.add(new entry(name, message));
    }

    public synchronized ArrayList<entry> getMessages() {
        return entries;
    }

}
