package GUI;

import ebay.*;

import javax.swing.*;

public class Controller {
    private User loggedInUser;
    private SystemAdmin loggedInAdmin;
    private String currentUser;
    private ItemManager itemManager;
    private LoginScreen loginScreen;
    private AuctionApp auctionApp; // Reference to the main application view

    public static void main(String[] args) {
        new Controller(); // Starts the application by initializing the Controller
    }

    public Controller() {
        itemManager = ItemManager.getInstance(); // Initialize ItemManager or any other resources here
        showLoginScreen(); // Show login screen at the start
    }

    public void showLoginScreen() {
        // Display the login screen, passing handleLogin as the ActionListener for the login button
        loginScreen = new LoginScreen(e -> handleLogin());
    }

    private void handleLogin() {
        // Retrieve user input from login screen
        String username = loginScreen.getUsername();
        String password = loginScreen.getPassword();
        String adminPassword = loginScreen.getAdminPassword();

        // Attempt to log in
        boolean loginSuccess = login(username, password, adminPassword);

        if (loginSuccess) {
            loginScreen.close(); // Close the login window
            auctionApp = new AuctionApp(this); // Pass the controller to AuctionApp to continue application flow
        } else {
            loginScreen.showError("Invalid login credentials. Please try again.");
        }
    }

    // Login Logic
    public boolean login(String username, String password, String adminPassword) {
        if ("admin".equals(adminPassword)) {
            loggedInAdmin = new SystemAdmin(username, password);
            currentUser = "admin";
            return true;
        } else {
            loggedInUser = new User(username, password);
            currentUser = "user";
            return true;
        }
    }

    // Methods to retrieve the current user
    public String getCurrentUser() {
        return currentUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public SystemAdmin getLoggedInAdmin() {
        return loggedInAdmin;
    }

    // Item management methods
    public void addItem(String itemName, String description, double price, String imageUrl, boolean isAuction, String itemType, DefaultListModel<String> auctionListModel) {
        Item newItem = new Item(itemName, description, price, imageUrl, isAuction, itemType);
        itemManager.addItem(newItem); // Add item to ItemManager
        auctionListModel.addElement(itemName); // Update UI with new item

    }

    public Item getItemByName(String name) {
        return itemManager.getItemByName(name); // Retrieve item by name from ItemManager
    }

    public void setSellersCommission(String commission) {
        // Logic to set seller's commission
        JOptionPane.showMessageDialog(null, "Seller's commission set");
    }

    public void setBuyerPremium(String premium) {
        // Logic to set buyer's premium
        JOptionPane.showMessageDialog(null, "Buyer's premium set");
    }

    public void addCategory(String category, DefaultListModel<String> categoryListModel) {
        if (!category.isEmpty()) {
            categoryListModel.addElement(category);
        }
    }
   public void placeBid(String itemName, double bidAmount) {
        Item item = itemManager.getItemByName(itemName);
        if (item != null) {
            Bid bid = new Bid(loggedInUser, bidAmount);
            if (ItemManager.placeBid(itemName,bid)) {
                JOptionPane.showMessageDialog(null,"Bid of" + bidAmount + "placed on item: " + itemName);
            } else {
                JOptionPane.showMessageDialog(null,"Bid of" + bidAmount + "is too low for item: " + itemName);
            }
        } else {
            JOptionPane.showMessageDialog(null,"Item not found: " + itemName);
        }
    }
    public void showBid(String itemName, double myBidAmount) {
    Item item = itemManager.getItemByName(itemName);
    if (item != null) {
        StringBuilder message = new StringBuilder();
        message.append("Current high bid: ").append(item.getHighestBid().getBidAmount()).append("\n");
        message.append("Time Remaining: ").append(item.getTimeRemaining()).append("\n");
        message.append("Your bid: ").append(myBidAmount).append("\n");
        if (item.getBuyItNowPrice() > 0) {
            message.append("Buy it now price: ").append(item.getBuyItNowPrice()).append("\n");
        }
        JOptionPane.showMessageDialog(null, "The current bid for item: " + message.toString());
    } else {
        JOptionPane.showMessageDialog(null, "Item not found: " + itemName);
        }
    }
}

