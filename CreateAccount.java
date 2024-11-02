package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends JFrame {

    private JLabel createActMsg;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField sysAdminField;
    private JButton signUp;
    private JButton back;
    private JLabel username;
    private JLabel password;
    private JLabel sysAdmin;
    private JPanel createAccount;
    private JFrame frame;

    public CreateAccount(ActionListener actionListener) {
        setTitle("Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(createAccount);

        // Back button action: go back to a previous window or close
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the current window
                // Optionally, navigate to the previous window if needed
            }
        });

        // Sign-up button action: validate input and display message
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String sysAdmin = sysAdminField.getText().trim();

                // Basic validation
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            CreateAccount.this,
                            "All fields must be filled!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Success message
                    JOptionPane.showMessageDialog(
                            CreateAccount.this,
                            "Account created successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // Reset fields
                    usernameField.setText("");
                    passwordField.setText("");
                    sysAdminField.setText("");
                }
            }
        });
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getAdminPassword() {
        return sysAdminField.getText();
    }

    public void close() {
        frame.dispose();
    }
}
