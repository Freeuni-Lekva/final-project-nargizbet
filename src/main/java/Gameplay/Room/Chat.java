package Gameplay.Room;


import java.util.ArrayList;

public class Chat {

    private ArrayList<Entry> entries;

    public Chat() {
        entries = new ArrayList<>();
    }

    public synchronized void addMessage(String name, String message) {
        entries.add(new Entry(name, message));
    }

    public synchronized ArrayList<Entry> getMessages() {
        return entries;
    }

}
