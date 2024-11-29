package GUI;

import ebay.*;

import javax.swing.*;
import java.util.List;

public class Controller {
    private User loggedInUser;
    private SystemAdmin loggedInAdmin;
    private String currentUser;
    private ItemManager itemManager;
    private LoginScreen loginScreen;
    private AuctionApp auctionApp;// Reference to the main application view

    private Thread auctionMonitorThread;

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
    public void addItem(String itemName, String description, double price, String imageUrl, boolean isAuction, String itemType, DefaultListModel<String> auctionListModel, double startPrice) {

        Item newItem = new Item(itemName, description, price, imageUrl, isAuction, itemType, startPrice);
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
            Bid highestBid = item.getHighestBid();
            if(highestBid != null && bidAmount <= highestBid.getBidAmount()) {
                Bid bid = new Bid(loggedInUser, bidAmount);
                item.placeBid(bid);
                JOptionPane.showMessageDialog(null, "Bid of" + bidAmount + "placed on item: " + itemName);
            } else {
                JOptionPane.showMessageDialog(null, "Bid of" + bidAmount + "is too low for item: " + itemName);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found: " + itemName);
        }
    }
    public void showConcludedAuctions(DefaultListModel<String> concludedAuctionsModel) {
        List<Item> concludedAuctions = itemManager.getConcludedAuctions();
        concludedAuctionsModel.clear();
        for (Item item : concludedAuctions) {
            concludedAuctionsModel.addElement(item.getItemName());
        }
    }

    public void showBid(String selectedItemName, double myBidAmount) {
        Item item = itemManager.getItemByName(selectedItemName);
        if (item != null) {
            Bid highestBid = item.getHighestBid();
            if (highestBid != null) {
                JOptionPane.showMessageDialog(null, "Highest bid for item: " + selectedItemName + " is: " + highestBid.getBidAmount());
            } else {
                JOptionPane.showMessageDialog(null, "No bids placed on item: " + selectedItemName);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found: " + selectedItemName);
        }
    }
}