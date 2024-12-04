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

    public Login() {
        // Set up the ParentPanel with CardLayout
        ParentPanel.setLayout(new CardLayout());
        ParentPanel.add(WelcomePanel, "WelcomePanel");
        ParentPanel.add(AdminLogin, "AdminLogin");

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}

