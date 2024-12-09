// Purpose: Main class to run the program.
package view;

import controller.LoginController;
import model.UserManager;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManager userManager = new UserManager();
            Login loginView = new Login();
            new LoginController(userManager, loginView);
        });
    }
}
