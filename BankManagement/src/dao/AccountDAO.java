package dao;

import model.Account;
import util.DBConnection;

import java.sql.*;

public class AccountDAO {

    public void createAccount(Account acc) {
        String sql = "INSERT INTO accounts (name, account_type, balance, created_at, status) VALUES (?, ?, ?, CURRENT_DATE, 'ACTIVE')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, acc.getName());
            ps.setString(2, acc.getType());
            ps.setDouble(3, acc.getBalance());
            ps.executeUpdate();

            System.out.println("Account Created Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deposit(int accId, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ? AND status = 'ACTIVE'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, accId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Amount Deposited Successfully.");
                viewAccount(accId);
            } else {
                System.out.println("Account not found or closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdraw(int accId, double amount) {
        String checkSql = "SELECT balance FROM accounts WHERE account_id = ? AND status = 'ACTIVE'";
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, accId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setDouble(1, amount);
                        updatePs.setInt(2, accId);
                        updatePs.executeUpdate();
                        System.out.println("Amount Withdrawn Successfully.");
                        viewAccount(accId);
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found or closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transfer(int fromId, int toId, double amount) {
        String withdrawSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ? AND status = 'ACTIVE'";
        String depositSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ? AND status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement withdrawPs = conn.prepareStatement(withdrawSql);
                 PreparedStatement depositPs = conn.prepareStatement(depositSql)) {

                withdrawPs.setDouble(1, amount);
                withdrawPs.setInt(2, fromId);
                withdrawPs.setDouble(3, amount);

                int withdrawResult = withdrawPs.executeUpdate();

                if (withdrawResult > 0) {
                    depositPs.setDouble(1, amount);
                    depositPs.setInt(2, toId);

                    int depositResult = depositPs.executeUpdate();

                    if (depositResult > 0) {
                        conn.commit();
                        System.out.println("Transfer Successful.");
                        viewAccount(fromId);
                        viewAccount(toId);
                    } else {
                        conn.rollback();
                        System.out.println("Transfer Failed: Destination account not found or closed.");
                    }
                } else {
                    conn.rollback();
                    System.out.println("Transfer Failed: Insufficient balance or source account issue.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAccount(int accId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.printf("\nAccount ID: %d\nName: %s\nType: %s\nBalance: %.2f\nStatus: %s\nCreated: %s\n",
                        rs.getInt("account_id"),
                        rs.getString("name"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("status"),
                        rs.getDate("created_at"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllAccounts() {
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- All Accounts ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Type: %s | Balance: %.2f | Status: %s | Created: %s\n",
                        rs.getInt("account_id"),
                        rs.getString("name"),
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("status"),
                        rs.getDate("created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(int accId) {
        String sql = "UPDATE accounts SET status = 'CLOSED' WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Account Closed Successfully.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewTransactions(int accId) {
        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Type: %s | Amount: %.2f | Date: %s | Description: %s\n",
                        rs.getInt("transaction_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
