package Tests;

import Database.BalanceDAO;
import Database.DataSource;
import Database.UserDAO;
import User.User;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import java.sql.Connection;
import java.sql.Statement;

public class UserDAOTests extends TestCase {
    User usr1 = new User("first", "abcd", "a", "b", 100);
    User usr2 = new User("scnd", "s", "b", "k", 200);
    User usr3 = new User("third", "c", "j", "i", 400);
    User user1 = new User("username1", "password1", "firstName1", "lastName1", 50);
    User user2 = new User("username2", "password2", "firstName2", "lastName2", 150);
    User user3 = new User("username3", "password3", "firstName3", "lastName3", 600);
    User user4 = new User("giorgi1", "123", "gio", "gio");
    User user5 = new User("giorgi2", "123", "rgi", "rgi");


    @After
    public void tearDown() throws Exception {
        Connection con = DataSource.getCon();
        Statement statement = con.createStatement();
        statement.executeUpdate("DELETE FROM users;");
        con.close();
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

        User notusr1 = new User("firs", "abcd", "a", "b", 100);
        User notusr2 = new User("scn", "s", "b", "k", 200);
        User notusr3 = new User("thrd", "c", "j", "i", 400);
        User notuser1 = new User("uername1", "password1", "firstName1", "lastName1", 50);
        User notuser2 = new User("usrname2", "password2", "firstName2", "lastName2", 150);
        User notuser3 = new User("usrname3", "password3", "firstName3", "lastName3", 600);
        User notuser4 = new User("gorgi1", "123", "gio", "gio");
        User notuser5 = new User("gorgi2", "123", "rgi", "rgi");
        User arr1[] = {notusr1, notusr2, notusr3, notuser1, notuser2, notuser3, notuser4, notuser5};
        for(int i = 0; i < 8; i++){
            assertFalse(u.userRegistered(arr1[i]));
        }
    }

    public void testUserRegisteredThreadSafety(){
        User notusr1 = new User("firs", "abcd", "a", "b", 100);
        User notusr2 = new User("scn", "s", "b", "k", 200);
        User notusr3 = new User("thrd", "c", "j", "i", 400);
        User notuser1 = new User("uername1", "password1", "firstName1", "lastName1", 50);
        User notuser2 = new User("usrname2", "password2", "firstName2", "lastName2", 150);
        User notuser3 = new User("usrname3", "password3", "firstName3", "lastName3", 600);
        User notuser4 = new User("gorgi1", "123", "gio", "gio");
        User notuser5 = new User("gorgi2", "123", "rgi", "rgi");
        User arr[] = {usr1, usr2, usr3, user1, user2, user3, user4, user5,
                     notusr1, notusr2, notusr3, notuser1, notuser2, notuser3, notuser4, notuser5};

    }

    private class UserRegisteredThread extends Thread{
        private User usr;

        public UserRegisteredThread(User usr){
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
}
