package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ScribbleGame";
    private static final String DB_USER = "root";  // Change if needed
    private static final String DB_PASS = "Suraj2#pandey";      // Change if needed

    public LoginScreen() {
        setTitle("Scribble Game - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(new JLabel());  // Empty cell for spacing
        add(loginButton);
        add(registerButton);

        /** 🟢 Login Button Click */
        loginButton.addActionListener(e -> handleLogin());

        /** 🟢 Register Button Click */
        registerButton.addActionListener(e -> handleRegister());

        setVisible(true);
    }

    /** ✅ Handles Login */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (validateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "✅ Login Successful!");
            this.dispose();
            new GameWindow(username, "localhost"); // Connect to game
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid Credentials. Try again!");
        }
    }

    /** ✅ Handles Registration */
    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "✅ Registration Successful! Please login.");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Username already exists.");
        }
    }

    /** ✅ Validate User Login */
    private boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If a user exists, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** ✅ Register New User */
    private boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /** 🔹 Main Method to Start the Login Screen */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
