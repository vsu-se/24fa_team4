package ebay;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private User currentUser;
    private Map<String, String> userDatabase;
    private double buyerPremium;
    private double sellerCommission;
    private static final String DATA_FOLDER = "src/ebay/datafiles";
    private static final String USER_DATABASE_FILE = DATA_FOLDER + File.separator + "user_data.txt";
    private ItemManager itemManager;

    public UserController() {
        this.itemManager = itemManager;
        userDatabase = new HashMap<>();
        // Add some default users for demonstration purposes
        userDatabase.put("validUsername", "validPassword");
        userDatabase.put("admin", "adminPassword");
        loadUserDatabase();
    }

    public boolean login(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            currentUser = new User(username, password, true, true);
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
            saveUserDatabase();
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public void deleteUser(String username) {
        if (currentUser != null && currentUser.isAdmin()) {
            userDatabase.remove(username);
            saveUserDatabase();
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

    public void setCurrentUser(User testUser) {
        System.out.println("Setting current user: " + testUser.getUsername());
        this.currentUser = testUser;
    }

    private void ensureDataFolderExists() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Data folder created.");
            } else {
                System.err.println("Failed to create data folder.");
            }
        }
    }

    private void saveUserDatabase() {
        ensureDataFolderExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATABASE_FILE))) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
                System.out.println("Saving to: " + new File(USER_DATABASE_FILE).getAbsolutePath());

            }
        } catch (IOException e) {
            System.err.println("Error saving user database: " + e.getMessage());
        }
    }
    public void clearUserDatabase() {
        userDatabase.clear();
        File file = new File("src/ebay/datafiles/user_data.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    private void loadUserDatabase() {
        File file = new File(USER_DATABASE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        userDatabase.put(parts[0], parts[1]);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading user database: " + e.getMessage());
            }
        } else {
            System.out.println("User database file not found.");
            saveUserDatabase();
        }
    }

    public double getSellerPremium(){
        return sellerCommission;
    }
}