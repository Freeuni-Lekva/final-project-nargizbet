package Gameplay.Room;

public class Entry{
    private String name;
    private String text;
    public Entry(String n, String t){
        name = n;
        text = t;
    }
    public String getName(){
        return name;
    }
    public String getText(){
        return text;
    }
}
