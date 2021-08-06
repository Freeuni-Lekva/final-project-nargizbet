package Tests;

import Gameplay.Room.Chat;
import Gameplay.Room.entry;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTest extends TestCase {
    private Chat chat;
    public void setUp() {
        chat = new Chat();
        chat.addMessage("bro1","baro");
        chat.addMessage("bro2","baro bijo");

    }

    public void test1() {
        ArrayList<entry> tmp = chat.getMessages();
        for(int i=0; i<tmp.size(); i++){
            entry curr = tmp.get(i);
            System.out.println(curr.getName()+":"+curr.getText());
        }
        System.out.println("--done--");
    }
    public void test2() {
        chat.addMessage("bro1", "mamen");
        chat.addMessage("bro2", "mabrah");
        chat.addMessage("bro1", "shinamanikena");
        chat.addMessage("bro2", "sanamanikena");
        ArrayList<entry> tmp = chat.getMessages();
        for(int i=0; i<tmp.size(); i++){
            entry curr = tmp.get(i);
            System.out.println(curr.getName()+":"+curr.getText());
        }
        System.out.println("--done--");
    }

}