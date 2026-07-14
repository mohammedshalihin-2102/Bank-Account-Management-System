package main;

import service.AccountService;
import service.AdminService;
import service.AuthService;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AccountService accountService = new AccountService();
    static AdminService adminService = new AdminService();
    static AuthService authService = new AuthService();

    public static void main(String[] args) {

        System.out.println("--- Bank Account Management ---");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String role = authService.authenticate(username, password);

        if (role.equals("ADMIN")) {
            adminMenu();
        } else if (role.equals("CUSTOMER")) {
            customerMenu();
        } else {
            System.out.println("Invalid credentials. Exiting.");
        }
    }

    public static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        AdminService adminService = new AdminService();

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Customers");
            System.out.println("2. View Customer Transactions");
            System.out.println("3. Delete Customer");
            System.out.println("4. Logout");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> adminService.viewAllCustomers();
                case 2 -> {
                    System.out.print("Enter Account ID: ");
                    int accountId = sc.nextInt();
                    adminService.viewCustomerTransactions(accountId);
                }
                case 3 -> {
                    System.out.print("Enter Account ID to Delete: ");
                    int accountId = sc.nextInt();
                    adminService.deleteCustomer(accountId);
                }
                case 4 -> {
                    System.out.println("Logging out from Admin.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }


    public static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Panel ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Account");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> accountService.deposit();
                case 2 -> accountService.withdraw();
                case 3 -> accountService.transfer();
                case 4 -> accountService.viewAccount();
                case 5 -> accountService.viewTransactions();
                case 6 -> {
                    System.out.println("Exiting Customer Panel.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
