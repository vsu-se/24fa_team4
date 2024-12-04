package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel ParentPanel;  // Main container with CardLayout
    private JPanel WelcomePanel; // First panel
    private JPanel AdminLogin;   // Second panel
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signInButton;
    private JButton adminLoginClickHereButton;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton signInButton1;
    private JButton newUserClickHereButton;
    private JPanel NewUserRegistrationPanel;
    private JTextField textField3;
    private JPasswordField passwordField3;
    private JButton createAccountButton;
    private JButton signInButton3;

    public Login() {
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

        newUserClickHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Switch to Registration Panel for new users.
                CardLayout layout2 = (CardLayout) ParentPanel.getLayout();
                layout2.show(ParentPanel, "NewUserRegistrationPanel");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}

