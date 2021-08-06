package Tests;

import Database.DataSource;
import Database.FriendsDAO;
import Database.UserDAO;
import User.User;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.CountDownLatch;

public class UserTest extends TestCase {

    public void testExistingAccountGetters() {
        User user = new User("username", "password", "firstName", "lastName");
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
    }

    public void testNewAccountGetters() {
        User user = new User("username", "password", "firstName", "lastName", 1.1);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals(1.1, user.getBalance());
    }

    public void testExistingAccountSetters() {
        User user = new User("username", "password", "firstName", "lastName");
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals("newFirstName", user.getFirstName());
        assertEquals("newLastName", user.getLastName());
    }

    public void testNewAccountSetters() {
        User user = new User("username", "password", "firstName", "lastName", 1.1);
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        user.setBalance(2.2);
        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals("newFirstName", user.getFirstName());
        assertEquals("newLastName", user.getLastName());
        assertEquals(2.2, user.getBalance());
    }

    public void testIsFriendsWith() {
        UserDAO UDAO = new UserDAO();
        FriendsDAO FDAO = new FriendsDAO();
        ScriptRunner sr = new ScriptRunner(DataSource.getCon());
        Reader r = null;
        try {
            r = new BufferedReader(new FileReader("DatabaseTables.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sr.setEscapeProcessing(false);
        sr.runScript(r);
        User user1 = new User("username1", "password1", "firstName1", "lastName1");
        User user2 = new User("username2", "password2", "firstName2", "lastName2");
        User user3 = new User("username3", "password3", "firstName3", "lastName3");
        UDAO.addUser(user1);
        UDAO.addUser(user2);
        UDAO.addUser(user3);
        FDAO.addPair(user1, user2);
        assertTrue(user1.isFriendsWith(user2));
        assertTrue(user2.isFriendsWith(user1));
        assertFalse(user1.isFriendsWith(user3));
        assertFalse(user3.isFriendsWith(user1));
        assertFalse(user2.isFriendsWith(user3));
        assertFalse(user3.isFriendsWith(user2));
    }

    public void testEquals() {
        User user1 = new User("username1", null, null, null);
        User user2 = null;
        assertFalse(user1.equals(user2));
        user2 = new User("username2", null, null, null);
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
        user2 = new User("username1", null, null, null);
        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
    }

    public void testCompareTo() {
        User user1 = new User("username1", null, null, null);
        User user2 = null;
        assertEquals(1, user1.compareTo(user2));
        user2 = new User("username2", null, null, null);
        assertEquals(-1, user1.compareTo(user2));
        assertEquals(1, user2.compareTo(user1));
        user2 = new User("username1", null, null, null);
        assertEquals(0, user1.compareTo(user2));
        assertEquals(0, user2.compareTo(user1));
    }

    public void testDeposit() {
        User user = new User("username", "password", "firstName", "lastName");
        CountDownLatch cdl = new CountDownLatch(1000);
        for (int k = 1; k <= 1000; k++) {
            new DepositWorker(user, 7, cdl).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(8000.0, user.getBalance());
    }

    public void testWithdraw() {
        User user = new User("username", "password", "firstName", "lastName", 8000);
        CountDownLatch cdl = new CountDownLatch(1000);
        for (int k = 1; k <= 1000; k++) {
            new WithdrawWorker(user, 7, cdl).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1000.0, user.getBalance());
    }

    public void testTransfer() {
        User user1 = new User("username1", null, null, null, 8000);
        User user2 = new User("username2", null, null, null, 8000);
        CountDownLatch cdl = new CountDownLatch(2000);
        for (int k = 1; k <= 1000; k++) {
            new TransferWorker(user1, user2,7, cdl).start();
            new TransferWorker(user2, user1,7, cdl).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(8000.0, user1.getBalance());
        assertEquals(8000.0, user2.getBalance());
    }


    private class DepositWorker extends Thread {

        private User user;
        double amount;
        private CountDownLatch cdl;

        public DepositWorker(User user, double amount, CountDownLatch cdl) {
            this.user = user;
            this.amount = amount;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            user.deposit(amount);
            cdl.countDown();
        }

    }

    private class WithdrawWorker extends Thread {

        private User user;
        double amount;
        private CountDownLatch cdl;

        public WithdrawWorker(User user, double amount, CountDownLatch cdl) {
            this.user = user;
            this.amount = amount;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            user.withdraw(amount);
            cdl.countDown();
        }

    }

    private class TransferWorker extends Thread {

        private User user1;
        private User user2;
        double amount;
        private CountDownLatch cdl;

        public TransferWorker(User user1, User user2, double amount, CountDownLatch cdl) {
            this.user1 = user1;
            this.user2 = user2;
            this.amount = amount;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            user1.transfer(user2, amount);
            cdl.countDown();
        }

    }

}