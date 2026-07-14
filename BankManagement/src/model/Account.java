package model;

public class Account {
    private String name;
    private String type;
    private double balance;

    public Account(String name, String type, double balance) {
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public double getBalance() { return balance; }
}
