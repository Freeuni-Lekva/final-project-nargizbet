package Tests;

import Gameplay.Room.Chat;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Map.Entry;



public class ChatTest extends TestCase {
    private Chat chat;
    public void setUp() {
        chat = new Chat();
        chat.addMessage("bro1","baro");
        chat.addMessage("bro2","baro bijo");

    }

    public void test1() {
        ArrayList<Entry<String,String>> tmp = chat.getMessages();
        for(int i=0; i<tmp.size(); i++){
            Entry<String,String> curr = tmp.get(i);
            System.out.println(curr.getKey()+":"+curr.getValue());
        }
        System.out.println("--done--");
    }
    public void test2() {
        chat.addMessage("bro1", "mamen");
        chat.addMessage("bro2", "mabrah");
        chat.addMessage("bro1", "shinamanikena");
        chat.addMessage("bro2", "sanamanikena");
        ArrayList<Entry<String,String>> tmp = chat.getMessages();
        for(int i=0; i<tmp.size(); i++){
            Entry curr = tmp.get(i);
            System.out.println(curr.getKey()+":"+curr.getValue());
        }
        System.out.println("--done--");
    }

}