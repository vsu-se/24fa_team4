package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel ParentPanel;  // Main container with CardLayout
    private JPanel WelcomePanel; // First panel
    private JPanel AdminLogin; // Second panel
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
    }

    public void addAdminLoginListener(ActionListener listener) {
        adminLoginClickHereButton.addActionListener(listener);
    }

    public void addSignInListener(ActionListener listener) {
        signInButton.addActionListener(listener);
        signInButton1.addActionListener(listener);
    }

    public void addNewUserClickListener(ActionListener listener) {
        newUserClickHereButton.addActionListener(listener);
    }

    public void addCreateAccountListener(ActionListener listener) {
        createAccountButton.addActionListener(listener);
    }

    public String getUsername() {
        return textField1.getText();
    }

    public String getPassword() {
        return new String(passwordField1.getPassword());
    }

    public String getRegisterUsername() {
        return textField3.getText();
    }

    public String getRegisterPassword() {
        return new String(passwordField3.getPassword());
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchToPanel(String panelName) {
        CardLayout layout = (CardLayout) ParentPanel.getLayout();
        layout.show(ParentPanel, panelName);
    }
}