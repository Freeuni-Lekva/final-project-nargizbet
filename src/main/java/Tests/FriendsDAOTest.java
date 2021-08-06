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
        assertTrue(storage.addPair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
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

        assertTrue(!storage.areFriends(new User("JJ1", "123", "gio", "gio"),
                new User("ara3", "123", "rgi", "rgi")));
    }

}
