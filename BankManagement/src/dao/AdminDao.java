package dao;

import util.DBConnection;
import java.sql.*;

public class AdminDao {

    public void fetchAllCustomers() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM accounts")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Account ID: " + rs.getInt("account_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Type: " + rs.getString("account_type"));
                System.out.println("Balance: " + rs.getDouble("balance"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchTransactions(int accountId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM transactions WHERE account_id = ?")) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("transaction_id"));
                System.out.println("Type: " + rs.getString("transaction_type"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("Date: " + rs.getTimestamp("transaction_date"));
                System.out.println("--------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(int accountId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM accounts WHERE account_id = ?")) {

            stmt.setInt(1, accountId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Account not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
