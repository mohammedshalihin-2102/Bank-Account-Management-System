package service;

import dao.AccountDAO;
import model.Account;

import java.util.Scanner;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    AccountDAO dao = new AccountDAO();

    public void createAccount() {
        System.out.print("Enter Name: ");
        sc.nextLine(); 
        String name = sc.nextLine();
        System.out.print("Enter Account Type (Savings/Checking): ");
        String type = sc.nextLine();
        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        Account acc = new Account(name, type, balance);
        dao.createAccount(acc);
    }

    public void deposit() {
        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Amount to Deposit: ");
        double amount = sc.nextDouble();
        dao.deposit(id, amount);
    }

    public void withdraw() {
        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Amount to Withdraw: ");
        double amount = sc.nextDouble();
        dao.withdraw(id, amount);
    }

    public void transfer() {
        System.out.print("Enter Source Account ID: ");
        int fromId = sc.nextInt();
        System.out.print("Enter Destination Account ID: ");
        int toId = sc.nextInt();
        System.out.print("Enter Amount to Transfer: ");
        double amount = sc.nextDouble();
        dao.transfer(fromId, toId, amount);
    }

    public void viewAccount() {
        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();
        dao.viewAccount(id);
    }

    public void viewTransactions() {
        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();
        dao.viewTransactions(id);
    }
        public void transfer1() {
            System.out.print("Enter Source Account ID: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a valid Source Account ID: ");
                sc.next(); 
            }
            int fromId = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Destination Account ID: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a valid Destination Account ID: ");
                sc.next();
            }
            int toId = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Amount to Transfer: ");
            while (!sc.hasNextDouble()) {
                System.out.print("Invalid amount. Please enter a numeric value: ");
                sc.next();
            }
            double amount = sc.nextDouble();
            sc.nextLine(); 

            dao.transfer(fromId, toId, amount);
        

    }
}
