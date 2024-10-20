package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen {
    private JFrame frame;
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JTextField adminPasswordField;
    private JButton loginButton;

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

        // Login Section
        JLabel loginLabel = new JLabel("Log In:");
        loginLabel.setBounds(50, 100, 100, 25);
        frame.add(loginLabel);

        loginUsernameField = new JTextField();
        loginUsernameField.setBounds(150, 100, 150, 25);
        frame.add(loginUsernameField);

        loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(150, 130, 150, 25);
        frame.add(loginPasswordField);

        JLabel adminPasswordLabel = new JLabel("Admin Password:");
        adminPasswordLabel.setBounds(50, 160, 100, 25);
        frame.add(adminPasswordLabel);

        adminPasswordField = new JTextField();
        adminPasswordField.setBounds(150, 160, 150, 25);
        frame.add(adminPasswordField);

        loginButton = new JButton("Log In");
        loginButton.setBounds(150, 200, 100, 25);
        loginButton.addActionListener(loginListener);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    public String getUsername() {
        return loginUsernameField.getText();
    }

    public String getPassword() {
        return new String(loginPasswordField.getPassword());
    }

    public String getAdminPassword() {
        return adminPasswordField.getText();
    }

    public void close() {
        frame.dispose();
    }
}
