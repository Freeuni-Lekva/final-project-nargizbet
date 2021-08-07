package Tests;

import Database.BalanceDAO;
import Database.DataSource;
import Database.UserDAO;
import User.User;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAOTests extends TestCase {
    User tmp1 = new User("t123", "123", "t", "tt");
    User tmp2 = new User("t223", "223", "t", "tt");
    User tmp3 = new User("t323", "323", "t", "tt");
    User tmp4 = new User("t423", "423", "t", "tt");
    User tmp5 = new User("t523", "523", "t", "tt");
    User usr1 = new User("first", "abcd", "a", "b", 100);
    User usr2 = new User("scnd", "s", "b", "k", 200);
    User usr3 = new User("third", "c", "j", "i", 400);
    User user1 = new User("username1", "password1", "firstName1", "lastName1", 50);
    User user2 = new User("username2", "password2", "firstName2", "lastName2", 150);
    User user3 = new User("username3", "password3", "firstName3", "lastName3", 600);
    User user4 = new User("giorgi1", "123", "gio", "gio");
    User user5 = new User("giorgi2", "123", "rgi", "rgi");
    User notusr1 = new User("firs", "abcd", "a", "b", 100);
    User notusr2 = new User("scn", "s", "b", "k", 200);
    User notusr3 = new User("thrd", "c", "j", "i", 400);
    User notuser1 = new User("uername1", "password1", "firstName1", "lastName1", 50);
    User notuser2 = new User("usrname2", "password2", "firstName2", "lastName2", 150);
    User notuser3 = new User("usrname3", "password3", "firstName3", "lastName3", 600);
    User notuser4 = new User("gorgi1", "123", "gio", "gio");
    User notuser5 = new User("gorgi2", "123", "rgi", "rgi");

    @After
    public void tearDown() throws Exception {
        Connection con = DataSource.getCon();
        Statement statement = con.createStatement();
        statement.executeUpdate("DELETE FROM users;");
        con.close();
    }

    public void testGetUsersLike(){
        UserDAO u = new UserDAO();

        u.addUser(notuser1);
        u.addUser(notuser2);
        u.addUser(notuser3);

        u.addUser(user1);
        u.addUser(user2);
        u.addUser(user3);

        u.addUser(notuser4);
        u.addUser(notuser5);

        List<User> users = u.getUsersLike("name");
        users.stream().collect(Collectors.<User>toSet());

        assertEquals(6, users.size());

        assertTrue(users.contains(notuser1));
        assertTrue(users.contains(notuser2));
        assertTrue(users.contains(notuser3));

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));


        assertTrue(!users.contains(notuser4));
    }

    public void testAddUser(){
        UserDAO u = new UserDAO();
        u.addUser(usr1);
        u.addUser(usr2);
        u.addUser(usr3);
        u.addUser(user1);
        u.addUser(user2);
        u.addUser(user3);
        u.addUser(user4);
        u.addUser(user5);
        assertTrue(u.getUser(usr1.getUsername()) != null);
        assertTrue(u.getUser(usr2.getUsername()) != null);
        assertTrue(u.getUser(usr3.getUsername()) != null);
        assertTrue(u.getUser(user1.getUsername()) != null);
        assertTrue(u.getUser(user2.getUsername()) != null);
        assertTrue(u.getUser(user3.getUsername()) != null);
        assertTrue(u.getUser(user4.getUsername()) != null);
        assertTrue(u.getUser(user5.getUsername()) != null);
    }

    public void testAddUserThreadSafety() throws InterruptedException {
        User arr[] = {usr1, usr2, usr3, user1, user2, user3, user4, user5};
        Thread threads[] = new Thread[8];
        UserDAO u = new UserDAO();
        for(int i = 0; i < 8; i++){
            Thread curr = new addUserThread(arr[i]);
            threads[i] = curr;
            curr.start();
        }

        for(int i = 0; i < 8; i++){
            threads[i].join();
        }

        for(int i = 0; i < 8; i++){
            assertTrue(u.getUser(arr[i].getUsername()) != null);
        }
    }

    private class addUserThread extends Thread{
        private User usr;

        public addUserThread(User usr){
            this.usr = usr;
        }

        @Override
        public void run() {
            UserDAO u = new UserDAO();
            u.addUser(usr);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testUserRegistered(){
        UserDAO u = new UserDAO();
        User arr[] = {usr1, usr2, usr3, user1, user2, user3, user4, user5};
        for(int i = 0; i < 8; i++){
            u.addUser(arr[i]);
        }

        for(int i = 0; i < 8; i++){
            assertTrue(u.userRegistered(arr[i]));
        }

        User arr1[] = {notusr1, notusr2, notusr3, notuser1, notuser2, notuser3, notuser4, notuser5};
        for(int i = 0; i < 8; i++){
            assertFalse(u.userRegistered(arr1[i]));
        }
    }

    public void testUserRegisteredThreadSafety() throws InterruptedException {
        UserDAO u = new UserDAO();
        User arr[] = {usr1, usr2, usr3, user1, user2, user3, user4, user5,
                     notusr1, notusr2, notusr3, notuser1, notuser2, notuser3, notuser4, notuser5};
        Thread threads[] = new Thread[16];
        for(int i = 0; i < 16; i++){
            u.addUser(arr[i]);
        }
        for(int i = 0; i < 16; i++){
            Thread th = new UserRegisteredThread(arr[i]);
            threads[i] = th;
            th.start();
        }

        for(int i = 0; i < 16; i++){
            threads[i].join();
        }
    }

    private class UserRegisteredThread extends Thread{
        private User usr;

        public UserRegisteredThread(User usr){
            this.usr = usr;
        }

        @Override
        public void run() {
            UserDAO u = new UserDAO();
            assertTrue(u.userRegistered(usr));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testIsCorrectPassword(){
        UserDAO u = new UserDAO();
        u.addUser(usr1);
        u.addUser(usr2);
        u.addUser(usr3);
        u.addUser(user1);
        u.addUser(user2);
        u.addUser(user3);
        u.addUser(user4);
        u.addUser(user5);
        assertFalse(u.isCorrectPass(new User("first", "abd", "a", "b")));
        assertTrue(u.isCorrectPass(new User("first", "abcd", "a", "b")));
        assertFalse(u.isCorrectPass(new User("scnd", "se", "b", "k")));
        assertTrue(u.isCorrectPass(new User("scnd", "s", "b", "k")));
        assertFalse(u.isCorrectPass(new User("third", "ceqweq", "j", "i")));
        assertTrue(u.isCorrectPass(new User("third", "c", "j", "i")));
    }
    public void testBasicGetUser(){
        UserDAO u = new UserDAO();
        u.addUser(tmp1);
        u.addUser(tmp2);
        User user = u.getUser(tmp1.getUsername());
        assertTrue(user.getUsername().equals("t123"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr(("123").getBytes())));
        User user2 = u.getUser(tmp2.getUsername());
        assertTrue(user2.getUsername().equals("t223"));
        assertTrue(user2.getFirstName().equals("t"));
        assertTrue(user2.getLastName().equals("tt"));
        assertTrue(user2.getPassword().equals(u.hashStr("223".getBytes())));
    }
    public void testMultipleGetUser(){
        UserDAO u = new UserDAO();
        u.addUser(tmp1);
        u.addUser(tmp2);
        User user = u.getUser(tmp1.getUsername());
        assertTrue(user.getUsername().equals("t123"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr(("123").getBytes())));
        user = u.getUser(tmp2.getUsername());
        assertTrue(user.getUsername().equals("t223"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr("223".getBytes())));
        user = u.getUser(tmp1.getUsername());
        assertTrue(user.getUsername().equals("t123"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr(("123").getBytes())));
        user = u.getUser(tmp1.getUsername());
        assertTrue(user.getUsername().equals("t123"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr(("123").getBytes())));
        user = u.getUser(tmp2.getUsername());
        assertTrue(user.getUsername().equals("t223"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr("223".getBytes())));
        user = u.getUser(tmp2.getUsername());
        assertTrue(user.getUsername().equals("t223"));
        assertTrue(user.getFirstName().equals("t"));
        assertTrue(user.getLastName().equals("tt"));
        assertTrue(user.getPassword().equals(u.hashStr("223".getBytes())));
    }
    public void testFalseUsers(){
        UserDAO u = new UserDAO();
        assertTrue(u.getUser("(<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>)")==null);
    }
    public void testMultipleThreadGetUser() throws InterruptedException {
        UserDAO u = new UserDAO();
        String arr[] = {"username1", "username2", "username3", "username4", "username5", "username6", "username7", "username8",
                "username9", "username10", "username11", "username12", "username13", "username14", "username15", "username16"};
        Thread threads[] = new Thread[16];
        for(int i = 0; i < 16; i++){
            Thread th = new UserGetter(arr[i]);
            threads[i] = th;
            th.start();
        }

        for(int i = 0; i < 16; i++){
            threads[i].join();
        }

    }
    private class UserGetter extends Thread{
        private String username;
        public UserGetter(String u){
            username  = u;
        }
        @Override
        public void run() {
            UserDAO u = new UserDAO();
            u.addUser(new User(username, "123", "t", "tt"));
            User user = u.getUser(username);
            assertTrue(user.getUsername().equals(username));
            assertTrue(user.getPassword().equals(UserDAO.hashStr("123".getBytes(StandardCharsets.UTF_8))));
            assertTrue(user.getLastName().equals("tt"));
            assertTrue(user.getFirstName().equals("t"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
