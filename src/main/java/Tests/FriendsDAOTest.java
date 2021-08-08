package Tests;


import Database.DataSource;
import Database.FriendsDAO;
import Database.UserDAO;
import User.User;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendsDAOTest extends TestCase {

    FriendsDAO storage;
    UserDAO userDAO;

    @Before
    protected void setUp() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(DataSource.getCon());
        Reader reader = new BufferedReader(new FileReader("DatabaseTables.sql"));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
        storage = new FriendsDAO(new UserDAO());
        userDAO = new UserDAO();
    }

    @After
    protected void tearDown() throws Exception {
        Statement statement = DataSource.getCon().createStatement();
        statement.executeUpdate("DELETE FROM friends;");
        statement.executeUpdate("DELETE FROM friend_requests;");
        statement.executeUpdate("DELETE FROM users;");
    }



    public void testInsert(){
        userDAO.addUser(new User("user", "pass", "name", "last"));
        userDAO.addUser(new User("user2", "pass", "name", "last"));
        assertTrue(storage.addPair(new User("user", "pass", "name", "last"),
                new User("user2", "pass", "name", "last")));
    }

    public void testRemovePair(){
        userDAO.addUser(new User("giorgi1", "123", "gio", "gio"));
        userDAO.addUser(new User("giorgi2", "123", "rgi", "rgi"));
        assertTrue(storage.addPair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
        assertTrue(storage.removePair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));

        //removing non-existing pair
        assertTrue(!storage.removePair(new User("giorgi1", "123", "gio", "gio"),
                new User("giorgi2", "123", "rgi", "rgi")));
    }

    public void testAreFriends(){
        userDAO.addUser(new User("giorgi1", "123", "gio", "gio"));
        userDAO.addUser(new User("JJ1", "123", "gio", "gio"));
        userDAO.addUser(new User("giorgi2", "123", "rgi", "rgi"));
        userDAO.addUser(new User("ara3", "123", "rgi", "rgi"));

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
            userDAO.addUser(u);
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
    	Set<User> users = new HashSet<>();

        User u = new User("0", "pass", "name", "lastName");
        userDAO.addUser(u);
        users.add(u);
        
        for (int i = 1; i < 10; ++i) {
            User f = new User(String.valueOf(i), "pass", "name", "lastName");
            userDAO.addUser(f);
            users.add(f);
        }

        for (User friend : users) {
            assertTrue(storage.addPair(u, friend));
        }
        Set<User> friends = storage.getFriends(u);

        friends.stream().collect(Collectors.<User>toSet());

        boolean containsAll = true;
        for(User user : users)
            if(!friends.contains(user)) containsAll = false;

        assertTrue(containsAll);
    }

    public void testWithThreads() throws InterruptedException {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            User u = new User(String.valueOf(i), "pass", "name", "lastName");
            userDAO.addUser(u);
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

    class areFriendsRunnable implements Runnable {
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
    
    public void testAddFriendsRequest() {
    	User user1 = new User("1", "psw", "name", "surname");
    	User user2 = new User("2", "psw", "name", "surname");
    	userDAO.addUser(user1);
    	userDAO.addUser(user2);
    	assertTrue(storage.addFriendRequest(user1, user2));
    }
    
    public void testIsFriendRequest() {
    	User user1 = new User("1", "psw", "name", "surname");
    	User user2 = new User("2", "psw", "name", "surname");
    	userDAO.addUser(user1);
    	userDAO.addUser(user2);
    	assertTrue(storage.addFriendRequest(user1, user2));
    	
    	assertTrue(storage.isFriendRequest(user1, user2));
    	assertFalse(storage.isFriendRequest(user2, user1));
    }
    
    public void testRemoveFriendRequest() {
    	User user1 = new User("1", "psw", "name", "surname");
    	User user2 = new User("2", "psw", "name", "surname");
    	userDAO.addUser(user1);
    	userDAO.addUser(user2);
    	assertTrue(storage.addFriendRequest(user1, user2));
    	assertTrue(storage.isFriendRequest(user1, user2));
    	
    	assertFalse(storage.removeFriendRequest(user2, user1));
    	assertTrue(storage.removeFriendRequest(user1, user2));
    	
    	assertFalse(storage.isFriendRequest(user1, user2));
    }
    
    public void testFriendRequestsSent() {
    	User user1 = new User("1", "psw", "name", "surname");
    	User user2 = new User("2", "psw", "name", "surname");
    	User user3 = new User("3", "psw", "name", "surname");
    	User user4 = new User("4", "psw", "name", "surname");
    	User user5 = new User("5", "psw", "name", "surname");
    	userDAO.addUser(user1);
    	userDAO.addUser(user2);
    	userDAO.addUser(user3);
    	userDAO.addUser(user4);
    	userDAO.addUser(user5);
    	
    	assertTrue(storage.addFriendRequest(user1, user2));
    	assertTrue(storage.addFriendRequest(user1, user3));
    	assertTrue(storage.addFriendRequest(user1, user4));
    	assertTrue(storage.addFriendRequest(user1, user5));
    	
    	Set<User> set = new HashSet<>();
    	set.add(user2);
    	set.add(user3);
    	set.add(user4);
    	set.add(user5);
    	
    	Set<User> requestsSent = storage.FriendRequestsSent(user1);
    	
    	for (User user : set) 
    		assertTrue(requestsSent.contains(user));
    }
    
    public void testFriendsRequestsRecieved() {
    	User user1 = new User("1", "psw", "name", "surname");
    	User user2 = new User("2", "psw", "name", "surname");
    	User user3 = new User("3", "psw", "name", "surname");
    	User user4 = new User("4", "psw", "name", "surname");
    	User user5 = new User("5", "psw", "name", "surname");
    	userDAO.addUser(user1);
    	userDAO.addUser(user2);
    	userDAO.addUser(user3);
    	userDAO.addUser(user4);
    	userDAO.addUser(user5);
    	
    	assertTrue(storage.addFriendRequest(user2, user1));
    	assertTrue(storage.addFriendRequest(user3, user1));
    	assertTrue(storage.addFriendRequest(user4, user1));
    	assertTrue(storage.addFriendRequest(user5, user1));
    	
    	Set<User> set = new HashSet<>();
    	set.add(user2);
    	set.add(user3);
    	set.add(user4);
    	set.add(user5);
    	
    	Set<User> requestsRecieved = storage.FriendRequestsRecieved(user1);
    	
    	for (User user : set) 
    		assertTrue(requestsRecieved.contains(user));
    }
    
    public void threadSafeRequestsTest() {
    	User[] users = new User[8];
    	users[0] = new User("0", "psw", "name", "surname");
    	users[1] = new User("1", "psw", "name", "surname");
    	users[2] = new User("2", "psw", "name", "surname");
    	users[3] = new User("3", "psw", "name", "surname");
    	users[4] = new User("4", "psw", "name", "surname");
    	users[5] = new User("5", "psw", "name", "surname");
    	users[6] = new User("6", "psw", "name", "surname");
    	users[7] = new User("7", "psw", "name", "surname");
    	
    	for (int i = 0; i < 8; i++)
			userDAO.addUser(users[i]);
    	
    	Thread[] threads = new Worker[8];
    	for (int i = 0; i < 8; i++)
    		threads[i] = new Worker(users[i], users);
    	
    	for (int i = 0; i < 8; i++)
    		threads[i].run();
    	
    	for (int i = 0; i < 8; i++) {
    		Set<User> requestsRecieved = storage.FriendRequestsRecieved(users[i]);
    		for (int j = 0; j < 8; j++) {
    			if (i == j) continue;
    			
    			assertTrue(requestsRecieved.contains(users[j]));
    		}
    	}
    		
    }
    
    private class Worker extends Thread {
    	private User user;
    	private User[] users;
    	
    	public Worker(User user, User[] users) {
    		this.user = user;
    		this.users = users;
    	}
    	
    	@Override
    	public void run() {
    		for (int i = 0; i < 8; i++) {
    			if (users[i].equals(user) || storage.isFriendRequest(user, users[i])) 
    				continue;
    			
    			storage.addFriendRequest(user, users[i]);
    		}
    		
    		Set<User> requestsSent = storage.FriendRequestsSent(user);
    		for (int i = 0; i < 8; i++) {
    			if (users[i] == user) continue;
    			
    			assertTrue(requestsSent.contains(users[i]));
    		}
    	}
    	
    }

}
