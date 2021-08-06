package Gameplay.Room;


import java.util.Map.Entry;
import java.util.ArrayList;

import java.util.AbstractMap.SimpleEntry;
public class Chat {

    private ArrayList<Entry<String,String>> entries;

    public Chat() {
        entries = new ArrayList<>();
    }

    public synchronized void addMessage(String name, String message) {
        entries.add(new SimpleEntry<>(name, message));
    }

    public synchronized ArrayList<Entry<String,String>> getMessages() {
        return entries;
    }

}
