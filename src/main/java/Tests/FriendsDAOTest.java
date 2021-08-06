package Tests;


import Database.DataSource;
import Database.FriendsDAO;
import User.User;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAOTest extends TestCase {

    FriendsDAO storage;

    protected void setUp() throws FileNotFoundException {

        ScriptRunner runner = new ScriptRunner(DataSource.getCon());
        Reader reader = new BufferedReader(new FileReader("DatabaseTables.sql"));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
        storage = new FriendsDAO(new Database.DataSource());
    }

    public void testInsert(){
        assertTrue(storage.addPair(new User("user", "pass", "name", "last"),
                new User("user2", "pass", "name", "last")));
    }

    public void testRemovePair(){
        assertTrue(storage.addPair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.removePair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));

        //removing non-existing pair
        assertTrue(!storage.removePair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
    }

    public void testAreFriends(){
        assertTrue(storage.addPair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.addPair(new User("JJ1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.addPair(new User("giorgi1", "123", "gio", "gio"),
                new User("ara3", "123", "rgi", "rgi")));

        assertTrue(storage.areFriends(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.areFriends(new User("JJ1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.areFriends(new User("giorgi1", "123", "gio", "gio"),
                new User("ara3", "123", "rgi", "rgi")));

        assertTrue(storage.areFriends(new User("giorgi2", "123", "rgi", "rgi"),
                new User("giorgi1", "123", "gio", "gio")));
        assertTrue(storage.areFriends(new User("giorgi2", "123", "rgi", "rgi"),
                new User("JJ1", "123", "gio", "gio")));
        assertTrue(storage.areFriends(new User("ara3", "123", "rgi", "rgi"),
                new User("giorgi1", "123", "gio", "gio")));

        assertTrue(!storage.areFriends(new User("JJ1", "123", "gio", "gio"),
                new User("ara3", "123", "rgi", "rgi")));
    }

    public void testAreFriends2(){
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            User u = new User(String.valueOf(i), "pass", "name", "lastName");
            users.add(u);
        }

        for(int i = 0; i < users.size(); ++i){
            for(int j = i + 1; j < users.size(); ++j){
                assertTrue(storage.addPair(users.get(i), users.get(j)));
            }
        }

        for(int i = 0; i < users.size(); ++i){
            for(int j = i + 1; j < users.size(); ++j){
                assertTrue(storage.areFriends(users.get(i), users.get(j)));
                assertTrue(storage.areFriends(users.get(j), users.get(i)));
            }
        }
    }

    public void testGetFriends(){

    }

    public void testWithThreads() throws InterruptedException {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            User u = new User(String.valueOf(i), "pass", "name", "lastName");
            users.add(u);
        }
        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < users.size(); ++i){
            for(int j = i + 1; j < users.size(); ++j){
             Thread th = new Thread(new insertRunnable(users.get(i), users.get(j)));
                threads.add(th);
                th.start();
            }
        }

        for(int i = 0; i < threads.size(); ++i){
            threads.get(i).join();
        }
        threads = new ArrayList<>();

        for(int i = 0; i < users.size(); ++i){
            for(int j = i + 1; j < users.size(); ++j){
                Thread th = new Thread(new areFriendsRunnable(users.get(i), users.get(j)));
                threads.add(th);
                th.start();
            }
        }

        for(int i = 0; i < threads.size(); ++i){
            threads.get(i).join();
        }
        threads = new ArrayList<>();

        for(int i = 0; i < users.size(); ++i){
            for(int j = i + 1; j < users.size(); ++j){
                Thread th = new Thread(new removeRunnable(users.get(i), users.get(j)));
                threads.add(th);
                th.start();
            }
        }

        for(int i = 0; i < threads.size(); ++i){
            threads.get(i).join();
        }

    }

    class insertRunnable implements Runnable{
        User user1, user2;

        public insertRunnable(User user1, User user2){
            this.user1 = user1;
            this.user2 = user2;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long)(Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(storage.addPair(user1, user2));
        }
    }

    class removeRunnable implements Runnable{
        User user1, user2;

        public removeRunnable(User user1, User user2){
            this.user1 = user1;
            this.user2 = user2;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long)(Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(storage.removePair(user1, user2));
        }
    }

    class areFriendsRunnable implements Runnable{
        User user1, user2;

        public areFriendsRunnable(User user1, User user2){
            this.user1 = user1;
            this.user2 = user2;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long)(Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(storage.areFriends(user1, user2));
        }

    }

}
