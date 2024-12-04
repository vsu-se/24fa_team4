
package GUI;

import ebay.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel ParentPanel;  // Main container with CardLayout
    private JPanel WelcomePanel; // First panel
    private JPanel AdminLogin;// Second panel
    private JPanel NewUserRegistrationPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signInButton;
    private JButton adminLoginClickHereButton;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton signInButton1;
    private JButton newUserClickHereButton;
    private JButton createAccountButton;
    private JTextField textField3;
    private JPasswordField passwordField3;
    private JButton signInButton3;

    private final UserController userController;

    public Login() {
        userController = new UserController();

        // Set up the ParentPanel with CardLayout
        ParentPanel.setLayout(new CardLayout());
        ParentPanel.add(WelcomePanel, "WelcomePanel");
        ParentPanel.add(AdminLogin, "AdminLogin");
        ParentPanel.add(NewUserRegistrationPanel, "NewUserRegistrationPanel");

        // Set up the JFrame
        setContentPane(ParentPanel);
        setTitle("Bidsy");
        setSize(750, 540);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the frame
        setVisible(true);

        // Add button listeners
        adminLoginClickHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to AdminLogin panel
                CardLayout layout = (CardLayout) ParentPanel.getLayout();
                layout.show(ParentPanel, "AdminLogin");
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = new String(passwordField1.getPassword());
                if (userController.login(username, password)) {
                    new UserHomePage(username, password, userController);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signInButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField2.getText();
                String password = new String(passwordField2.getPassword());
                if (userController.login(username, password)) {
                    new UserHomePage(username, password, userController);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        newUserClickHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField3.getText();
                String password = new String(passwordField3.getPassword());
                CardLayout layout = (CardLayout) ParentPanel.getLayout();
                layout.show(ParentPanel, "NewUserRegistrationPanel");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField3.getText();
                String password = new String(passwordField3.getPassword());
                userController.registerUser(username, password);
                JOptionPane.showMessageDialog(Login.this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                CardLayout layout = (CardLayout) ParentPanel.getLayout();
                layout.show(ParentPanel, "WelcomePanel");
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}