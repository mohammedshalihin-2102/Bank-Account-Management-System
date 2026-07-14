package service;

import util.DBConnection;
import java.sql.*;

public class AuthService {

    public String authenticate(String username, String password) {
        String role = "INVALID";

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
