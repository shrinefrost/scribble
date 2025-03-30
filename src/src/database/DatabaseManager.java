package src.database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/ScribbleGame";
    private static final String USER = "root";  // Change this to your DB username
    private static final String PASSWORD = "Suraj2#pandey";  // Change this to your DB password

    private Connection conn;

    /** 🟢 Connect to Database */
    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 🟢 Register a New User */
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // You should hash the password in a real system
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error: Username already taken!");
            return false;
        }
    }

    /** 🟢 Validate Login Credentials */
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); 
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // ✅ Return true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 🟢 Get Leaderboard Data */
    public String getLeaderboard() {
        StringBuilder leaderboard = new StringBuilder();
        String sql = "SELECT username, score FROM users ORDER BY score DESC LIMIT 10";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                leaderboard.append(rs.getString("username"))
                           .append(": ")
                           .append(rs.getInt("score"))
                           .append(",");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard.toString();
    }

    /** 🟢 Update User Score */
    public void updateScore(String username, int points) {
        String sql = "UPDATE users SET score = score + ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, points);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
