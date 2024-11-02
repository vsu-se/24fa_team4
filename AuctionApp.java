package GUI;

import ebay.Item;
import ebay.SystemAdmin;
import ebay.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AuctionApp extends JFrame {
    private JFrame frame; // Main application window
    private JTextField itemNameField; // TextField for item name input
    private JTextField descriptionField; // TextField for item description input
    private JTextField priceField; // TextField for buy it now price input
    private JTextField imageUrlField; // TextField for image URL input
    private JTextField itemTypeField; // TextField for item type input
    private JCheckBox isAuctionCheckBox; // CheckBox to indicate if the item is an auction
    private JList<String> auctionList; // List to display active auctions
    private DefaultListModel<String> auctionListModel; // Model for the auction list
    private JTextArea itemDetailsTextArea; // TextArea to display item details
    private JLabel itemImageLabel; // Label to display item image
    private JTextField categoryField; // TextField for adding new categories
    private DefaultListModel<String> categoryListModel; // Model for the category list
    private JList<String> categoryList; // List to display categories
    private JTextField sellerCommissionField; // TextField to set seller's commission
    private JTextField buyerPremiumField; // TextField to set buyer's premium
    private ArrayList<Item> items; // List to store items
    private ArrayList<User> users; //List of Users
    private ArrayList<SystemAdmin> systemAdmins; //List of System Administrators
    private User loggedInUser; // Currently logged in user
    private SystemAdmin loggedInAdmin; // Currently logged in admin
    private LoginScreen loginScreen; // Login screen instance
    private CreateAccount createAccount; //Create Account screen intance

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(AuctionApp::new);
    }

    public AuctionApp() {
        // Initialize the items list and models
        items = new ArrayList<>();
        auctionListModel = new DefaultListModel<>();
        categoryListModel = new DefaultListModel<>();
        showLoginScreen(); // Call the method to set up the login UI
    }

    private void showLoginScreen() {
        loginScreen = new LoginScreen(e -> {
            String username = loginScreen.getUsername();
            String password = loginScreen.getPassword();
            String adminPassword = loginScreen.getAdminPassword();

            // Sample login logic for admin and regular user
            if((username.isEmpty() || password.isEmpty())){
                JOptionPane.showMessageDialog(AuctionApp.this, "Please enter all the fields correctly.",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }

            else{
                if (username.contains("admin")) {
                    loggedInAdmin = new SystemAdmin(username, password);
                    loginScreen.close();
                    initialize();
                } else if (username.equals("admin") && adminPassword.equals("admin")) {
                    loggedInAdmin = new SystemAdmin(username, adminPassword);
                    loginScreen.close();
                    initialize();
                } else {
                    loggedInUser = new User(username, password);
                    loginScreen.close();
                    initialize();
                }
            }
        });
    }

    private void showCreateAccountScreen() {
        createAccount = new CreateAccount(e -> {
            String username = createAccount.getUsername();
            String password = createAccount.getPassword();
            String adminPassword = createAccount.getAdminPassword();

            // Sample login logic for admin and regular user
            if (username.contains("admin")) {
                SystemAdmin s = new SystemAdmin(username, password);
                if(systemAdmins.contains(s)){
                    JOptionPane.showMessageDialog(AuctionApp.this, "User already exists, please choose a different username.",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    systemAdmins.add(s);
                }

                createAccount.close();
                initialize();
            } else if (username.equals("admin") && adminPassword.equals("admin")) {
                SystemAdmin s = new SystemAdmin(username, adminPassword);
                createAccount.close();
                initialize();
            } else {
                User u = new User(username, password);
                createAccount.close();
                initialize();
            }
        });
    }

    private void initialize() {
        // Create and set up the main application frame
        frame = new JFrame("Bidsy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

        // Logo and Title
        JLabel titleLabel = new JLabel("Bidsy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBounds(20, 10, 200, 40);
        frame.add(titleLabel);

        // Add mascot image
        JLabel mascotLabel = new JLabel(new ImageIcon("GUI/bidsy_mascot.png")); // Change the path accordingly
        mascotLabel.setBounds(800, 5, 100, 300); // Adjust bounds as necessary
        frame.add(mascotLabel);

        // Below is the Tabs section at the top. (Active Auctions, Categories)
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 100, 200, 200); // Set position and size
        frame.add(tabbedPane); // Add tabbed pane to the frame

        // Create "Active Auctions" panel
        JPanel activeAuctionsPanel = new JPanel();
        activeAuctionsPanel.setLayout(new BorderLayout());

        // Create a list for active auctions
        auctionList = new JList<>(auctionListModel);
        activeAuctionsPanel.add(new JScrollPane(auctionList), BorderLayout.CENTER); // Add list to the panel

        // Add active auctions panel to the tabbed pane
        tabbedPane.addTab("Active Auctions", activeAuctionsPanel);

        // Create "Categories" panel
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BorderLayout());

        // Create a list for categories
        categoryList = new JList<>(categoryListModel);
        categoriesPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER); // Add list to the panel

        // Input field for new category
        categoryField = new JTextField();
        categoriesPanel.add(categoryField, BorderLayout.SOUTH); // Add input field to the bottom

        // Button to add category
        JButton addCategoryButton = new JButton("Add Category");
        categoriesPanel.add(addCategoryButton, BorderLayout.EAST); // Add button to the right

        // Add categories panel to the tabbed pane
        tabbedPane.addTab("Categories", categoriesPanel);

        // Item Details Area
        itemDetailsTextArea = new JTextArea();
        itemDetailsTextArea.setBounds(340, 130, 400, 150);
        frame.add(itemDetailsTextArea);

        // Label for item image
        itemImageLabel = new JLabel();
        itemImageLabel.setBounds(340, 290, 200, 200); // Adjust size as necessary
        frame.add(itemImageLabel);

        // Item Input Fields
        JLabel itemInputLabel = new JLabel("Add Item:");
        itemInputLabel.setBounds(20, 320, 100, 25);
        frame.add(itemInputLabel);

        // Input field for item name
        itemNameField = new JTextField();
        itemNameField.setBounds(20, 350, 150, 25);
        frame.add(itemNameField);
        JLabel itemNameLabel = new JLabel("Enter item name");
        itemNameLabel.setBounds(20, 375, 150, 20);
        frame.add(itemNameLabel);

        // Input field for item description
        descriptionField = new JTextField();
        descriptionField.setBounds(20, 400, 150, 25);
        frame.add(descriptionField);
        JLabel descriptionLabel = new JLabel("Enter item description");
        descriptionLabel.setBounds(20, 425, 150, 20);
        frame.add(descriptionLabel);

        // Input field for item price
        priceField = new JTextField();
        priceField.setBounds(20, 450, 150, 25);
        frame.add(priceField);
        JLabel priceLabel = new JLabel("Enter buy it now price");
        priceLabel.setBounds(20, 475, 150, 20);
        frame.add(priceLabel);

        // Input field for image URL
        imageUrlField = new JTextField();
        imageUrlField.setBounds(20, 490, 150, 25);
        frame.add(imageUrlField);
        JLabel imageUrlLabel = new JLabel("Enter image URL");
        imageUrlLabel.setBounds(20, 515, 150, 20);
        frame.add(imageUrlLabel);

        // Input field for item type
        itemTypeField = new JTextField();
        itemTypeField.setBounds(20, 525, 150, 25);
        frame.add(itemTypeField);
        JLabel itemTypeLabel = new JLabel("Enter item type");
        itemTypeLabel.setBounds(20, 550, 150, 20);
        frame.add(itemTypeLabel);

        // CheckBox to indicate auction item
        isAuctionCheckBox = new JCheckBox("Auction");
        isAuctionCheckBox.setBounds(20, 570, 150, 25);
        frame.add(isAuctionCheckBox);

        // Button to add the item
        JButton addButton = new JButton("Add Item");
        addButton.setBounds(180, 570, 150, 25);
        frame.add(addButton);

        // Seller's Commission and Buyer's Premium
        JLabel commissionLabel = new JLabel("Seller's Commission:");
        commissionLabel.setBounds(500, 385, 150, 25);
        frame.add(commissionLabel);

        // Input field for seller's commission
        sellerCommissionField = new JTextField();
        sellerCommissionField.setBounds(500, 420, 150, 25);
        frame.add(sellerCommissionField);

        // Button to set seller's commission
        JButton setCommissionButton = new JButton("Set Commission");
        setCommissionButton.setBounds(500, 455, 150, 25);
        frame.add(setCommissionButton);

        JLabel buyerPremiumLabel = new JLabel("Buyer's Premium:");
        buyerPremiumLabel.setBounds(500, 490, 150, 25);
        frame.add(buyerPremiumLabel);

        // Input field for buyer's premium
        buyerPremiumField = new JTextField();
        buyerPremiumField.setBounds(500, 520, 150, 25);
        frame.add(buyerPremiumField);

        // Button to set buyer's premium
        JButton setPremiumButton = new JButton("Set Premium");
        setPremiumButton.setBounds(500, 555, 150, 25);
        frame.add(setPremiumButton);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addItem()); // Add item when the button is clicked
        addCategoryButton.addActionListener(e -> addCategory()); // Add category when the button is clicked
        setCommissionButton.addActionListener(e -> setSellersCommission()); // Set seller's commission
        setPremiumButton.addActionListener(e -> setBuyerPremium()); // Set buyer's premium

        // Initialize UI components for admin features to be hidden initially
        toggleAdminFeatures(false); // Hide admin features initially

        // Make the frame visible
        frame.setVisible(true);
    }

    private void displayItemDetails(JList<String> auctionList) {
        // Display the details of the selected item in the auction list
        String selectedItemName = auctionList.getSelectedValue();
        if (selectedItemName != null) {
            Item selectedItem = items.stream()
                    .filter(item -> item.getItemName().equals(selectedItemName))
                    .findFirst()
                    .orElse(null);
            if (selectedItem != null) {
                // Set the description and image of the selected item
                itemDetailsTextArea.setText(selectedItem.getDescription());
                itemImageLabel.setIcon(new ImageIcon(selectedItem.getImageUrl()));
            }
        }
    }

    private void addItem() {
        // Add a new item to the auction
        String itemName = itemNameField.getText(); // Get item name input
        String description = descriptionField.getText(); // Get item description input
        double buyItNowPrice = Double.parseDouble(priceField.getText()); // Get buy it now price input
        String imageUrl = imageUrlField.getText(); // Get image URL input
        boolean isAuction = isAuctionCheckBox.isSelected(); // Check if it's an auction item
        String itemType = itemTypeField.getText(); // Get item type input

        // Create a new item object and add it to the lists
        Item newItem = new Item(itemName, description, buyItNowPrice, imageUrl, isAuction, itemType);
        items.add(newItem); // Add item to items list
        auctionListModel.addElement(itemName); // Add item name to auction list model
        clearItemFields(); // Clear the input fields
    }

    private void addCategory() {
        // Add a new category to the category list
        String category = categoryField.getText(); // Get category input
        if (!category.isEmpty()) {
            categoryListModel.addElement(category); // Add category to category list model
            categoryField.setText(""); // Clear the category input field
        }
    }

    private void setSellersCommission() {
        // Logic to set seller's commission
        String sellerCommission = sellerCommissionField.getText();
        
    }

    private void setBuyerPremium() {
        // Logic to set buyer's premium
    }

    private void toggleAdminFeatures(boolean isEnabled) {
        // Enable or disable admin-specific features
        sellerCommissionField.setEnabled(isEnabled); // Enable seller's commission field
        buyerPremiumField.setEnabled(isEnabled); // Enable buyer's premium field
        // Enable/disable other admin-specific features as necessary
    }

    private void clearItemFields() {
        // Clear all item input fields
        itemNameField.setText(""); // Clear item name field
        descriptionField.setText(""); // Clear description field
        priceField.setText(""); // Clear price field
        imageUrlField.setText(""); // Clear image URL field
        itemTypeField.setText(""); // Clear item type field
        isAuctionCheckBox.setSelected(false); // Uncheck auction checkbox
    }
}
