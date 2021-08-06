package Tests;

import Database.BalanceDAO;
import Database.UserDAO;
import User.User;
import junit.framework.TestCase;

public class BalanceDAOTests extends TestCase {
//    User(String username, String password, String firstName, String lastName, double balance)
    User usr1 = new User("first", "abcd", "a", "b", 100);
    User usr2 = new User("scnd", "s", "b", "k", 200);
    User usr3 = new User("third", "c", "j", "i", 400);
    User user1 = new User("username1", "password1", "firstName1", "lastName1", 50);
    User user2 = new User("username2", "password2", "firstName2", "lastName2", 150);
    User user3 = new User("username3", "password3", "firstName3", "lastName3", 600);

    public void testPrepSQL(){
        UserDAO u = new UserDAO();
        BalanceDAO b = new BalanceDAO();
        u.addUser(usr1);
        u.addUser(usr2);
        u.addUser(usr3);
        u.addUser(user1);
        u.addUser(user2);
        u.addUser(user3);
        b.addBalance(usr1);
        b.addBalance(usr2);
        b.addBalance(usr3);
        b.addBalance(user1);
        b.addBalance(user2);
        b.addBalance(user3);
    }

    public void testGetBalance(){
        BalanceDAO u = new BalanceDAO();
        assertEquals(u.getBalance(usr1), 100.0);
        assertEquals(u.getBalance(usr2), 200.0);
        assertEquals(u.getBalance(usr3), 400.0);
        usr1.setBalance(1000.0);
        usr2.setBalance(1500.0);
        usr3.setBalance(2000.0);
        u.setBalance(usr1);
        u.setBalance(usr2);
        u.setBalance(usr3);
        assertEquals(u.getBalance(usr1), 1000.0);
        assertEquals(u.getBalance(usr2), 1500.0);
        assertEquals(u.getBalance(usr3), 2000.0);
    }

    public void testSetBalance(){
        BalanceDAO b = new BalanceDAO();
        usr1.setBalance(200.0);
        usr2.setBalance(150.0);
        usr3.setBalance(100.0);
        b.setBalance(usr1);
        b.setBalance(usr2);
        b.setBalance(usr3);
        assertEquals(b.getBalance(usr1), 200.0);
        assertEquals(b.getBalance(usr2), 150.0);
        assertEquals(b.getBalance(usr3), 100.0);
        usr1.setBalance(0.0);
        usr2.setBalance(0.0);
        usr3.setBalance(0.0);
        b.setBalance(usr1);
        b.setBalance(usr2);
        b.setBalance(usr3);
        assertEquals(b.getBalance(usr1), 0.0);
        assertEquals(b.getBalance(usr2), 0.0);
        assertEquals(b.getBalance(usr3), 0.0);
    }

    public void testThreadSafety() throws InterruptedException {
        BalanceDAO b = new BalanceDAO();
        Thread[] threads = new setBalanceThread[6];
        setBalanceThread u1 = new setBalanceThread(usr1);
        setBalanceThread u2 = new setBalanceThread(usr2);
        setBalanceThread u3 = new setBalanceThread(usr3);
        setBalanceThread u4 = new setBalanceThread(user1);
        setBalanceThread u5 = new setBalanceThread(user2);
        setBalanceThread u6 = new setBalanceThread(user3);
        threads[0] = u1;
        threads[1] = u2;
        threads[2] = u3;
        threads[3] = u4;
        threads[4] = u5;
        threads[5] = u6;
        u1.start();
        u2.start();
        u3.start();
        u4.start();
        u5.start();
        u6.start();
        for(int i = 0; i < 6; i++){
            threads[i].join();
        }
        assertEquals(b.getBalance(usr1), 180.0);
        assertEquals(b.getBalance(usr2), 180.0);
        assertEquals(b.getBalance(usr3), 180.0);
        assertEquals(b.getBalance(user1), 180.0);
        assertEquals(b.getBalance(user2), 180.0);
        assertEquals(b.getBalance(user3), 180.0);
    }

    private class setBalanceThread extends Thread{
        private User usr;

        public setBalanceThread(User usr){
            this.usr = usr;
            usr.setBalance(180);
        }

        @Override
        public void run() {
            BalanceDAO b = new BalanceDAO();
            b.setBalance(usr);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
