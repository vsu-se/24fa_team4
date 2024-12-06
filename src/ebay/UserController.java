package ebay;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    private User currentUser;
    private Map<String, String> userDatabase;
    private double buyerPremium;
    private double sellerCommission;

    public UserController() {
        userDatabase = new HashMap<>();
        // Add some default users for demonstration purposes
        userDatabase.put("validUsername", "validPassword");
        userDatabase.put("admin", "adminPassword");
    }

    public boolean login(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            currentUser = new User(username, password);
            return true;

        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public void registerUser(String username, String password) {
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, password);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public void deleteUser(String username) {
        if (currentUser != null && currentUser.isAdmin()) {
            userDatabase.remove(username);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Current user is not authorized to delete users.");
        }
    }

    public void viewAllUsers() {
        if (currentUser != null && currentUser.isAdmin()) {
            System.out.println("All registered users:");
            for (String username : userDatabase.keySet()) {
                System.out.println(username);
            }
        } else {
            System.out.println("Current user is not authorized to view all users.");
        }
    }
    public void setSellerCommission(double sellerCommission) {
        this.sellerCommission = sellerCommission;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    public void setBuyerPremium(double buyerPremium){
        this.buyerPremium = buyerPremium;
    }

    public double getBuyerPremium() {
        return buyerPremium;
    }

}