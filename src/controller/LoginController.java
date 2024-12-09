// src/controller/LoginController.java
package controller;
import model.*;
import view.*;


public class LoginController {
    private UserManager userManager;
    private Login loginView;

    public LoginController(UserManager userManager, Login loginView) {
        this.userManager = userManager;
        this.loginView = loginView;

        // Add action listeners to the login view
        loginView.addAdminLoginListener(e -> switchToAdminLogin());
        loginView.addSignInListener(e -> handleLogin());
        loginView.addNewUserClickListener(e -> switchToNewUserRegistration());
        loginView.addCreateAccountListener(e -> handleRegister());
    }

    private void switchToAdminLogin() {
        loginView.switchToPanel("AdminLogin");
    }

    private void switchToNewUserRegistration() {
        loginView.switchToPanel("NewUserRegistrationPanel");
    }

    private void handleLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        User user = userManager.login(username, password);
        if (user != null) {
            new UserHomePage(userManager, user);
            loginView.dispose();
        } else {
            loginView.showError("Invalid username or password");
        }
    }

    private void handleRegister() {
        String username = loginView.getRegisterUsername();
        String password = loginView.getRegisterPassword();
        boolean success = userManager.register(username, password);
        if (success) {
            loginView.showInfo("Account created successfully!");
            loginView.switchToPanel("WelcomePanel");
        } else {
            loginView.showError("Failed to create account");
        }
    }
}