package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen {
    private final JFrame frame;
    private final JTextField loginUsernameField;
    private final JPasswordField loginPasswordField;
    private final JTextField adminPasswordField;
    private final JButton loginButton;

    public LoginScreen(ActionListener loginListener) {
        frame = new JFrame("Welcome to Bidsy! Please Log In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Bidsy! Please Log In");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(50, 50, 400, 30);
        frame.add(welcomeLabel);

        // Username Field
        JLabel loginLabel = new JLabel("Username:");
        loginLabel.setBounds(50, 100, 100, 25);
        frame.add(loginLabel);

        loginUsernameField = new JTextField();
        loginUsernameField.setBounds(150, 100, 150, 25);
        frame.add(loginUsernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 25);
        frame.add(passwordLabel);

        loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(150, 130, 150, 25);
        frame.add(loginPasswordField);

        // Admin Password Field (optional for admin users)
        JLabel adminPasswordLabel = new JLabel("Admin Password:");
        adminPasswordLabel.setBounds(50, 160, 100, 25);
        frame.add(adminPasswordLabel);

        adminPasswordField = new JTextField();
        adminPasswordField.setBounds(150, 160, 150, 25);
        frame.add(adminPasswordField);

        // Login Button
        loginButton = new JButton("Log In");
        loginButton.setBounds(150, 200, 100, 25);
        loginButton.addActionListener(loginListener); // Pass controller listener here
        frame.add(loginButton);

        frame.setVisible(true);
    }

    // Getters for login information
    public String getUsername() {
        return loginUsernameField.getText();
    }

    public String getPassword() {
        return new String(loginPasswordField.getPassword());
    }

    public String getAdminPassword() {
        return adminPasswordField.getText();
    }

    // Method to close login screen
    public void close() {
        frame.dispose();
    }

    // Optional: add a display error message method to show login errors in the GUI
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}
