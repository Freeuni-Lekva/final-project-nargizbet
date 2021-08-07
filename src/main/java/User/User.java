package User;

import Database.FriendsDAO;

import java.sql.Blob;
import java.sql.Date;

public class User implements Comparable<User>{

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double balance;
    private Blob profilePicture;
    private Date memberSince;

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

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public Date getMemberSince() {
        return memberSince;
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

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
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
        if (this == user2) return;
        if (compareTo(user2) == 1) {
            synchronized (this) {
                synchronized (user2) {
                    withdraw(amount);
                    user2.deposit(amount);
                }
            }
        } else {
            synchronized (user2) {
                synchronized (this) {
                    withdraw(amount);
                    user2.deposit(amount);
                }
            }
        }
    }

}