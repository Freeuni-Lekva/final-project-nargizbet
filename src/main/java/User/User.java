package User;

import Database.DataSource;
import Database.FriendsDAO;

public class User implements Comparable<User>{

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double balance;

    private FriendsDAO FDAO = new FriendsDAO();

    private final static double startingAmount = 1000;

    public User(String username, String password, String firstName, String lastName) {
        this(username, password, firstName, lastName, startingAmount);
    }

    public User(String username, String password, String firstName, String lastName, double balance) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public synchronized void setBalance(double balance) {
        this.balance = balance;
    }

    public static double getStartingAmount() {
        return startingAmount;
    }

    public boolean isFriendsWith(User user2) {
        return FDAO.areFriends(this, user2);
    }

    @Override
    public boolean equals(Object user2) {
        if (user2 == null) return false;
        return this.getUsername().equals(((User)user2).getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public int compareTo(User user2) {
        if (user2 == null) return 1;
        return getUsername().compareTo(user2.getUsername());
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public void transfer(User user2, double amount) {
        User user1 = this;
        if (compareTo(user2) == 1) swap(user1, user2);
        synchronized (user1) {
            synchronized (user2) {
                user1.withdraw(amount);
                user2.deposit(amount);
            }
        }
    }

    private void swap(User user1, User user2) {
        User temp = user1;
        user1 = user2;
        user2 = temp;
    }

}
