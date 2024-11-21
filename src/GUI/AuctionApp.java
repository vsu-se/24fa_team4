package GUI;

import ebay.Item;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AuctionApp {
    private JFrame frame;
    private JTextField itemNameField, descriptionField, priceField, imageUrlField, itemTypeField, bidAmountField;
    private JCheckBox isAuctionCheckBox;
    private JList<String> auctionList;
    private DefaultListModel<String> auctionListModel;
    private JTextArea itemDetailsTextArea;
    private JTextField categoryField;
    private DefaultListModel<String> categoryListModel;
    private JTabbedPane tabbedPane;
    private JButton addCategoryButton;
    private final Controller controller; // Controller instance passed in the constructor

    public AuctionApp(Controller controller) {
        this.controller = controller; // Use the provided Controller instance
        auctionListModel = new DefaultListModel<>();
        categoryListModel = new DefaultListModel<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Bidsy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background
        frame.setIconImage(new ImageIcon("GUI/bidsy_mascot2.png").getImage());

        setupUIComponents();

        frame.setVisible(true);
    }

    private void setupUIComponents() {
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(850, 10, 100, 30);
        logoutButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            controller.showLoginScreen();
        });
        frame.add(logoutButton);

        // Logo and Title
        JLabel titleLabel = new JLabel("Bidsy");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        titleLabel.setBounds(450, 10, 200, 40);
        frame.add(titleLabel);

        // Add mascot image
        JLabel mascotLabel = new JLabel(new ImageIcon("GUI/bidsy_mascot.png")); // Change the path accordingly
        mascotLabel.setBounds(100, 5, 50, 100); // Adjust bounds as necessary
        frame.add(mascotLabel);

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 120, 900, 400);
        frame.add(tabbedPane);

        // Active Auctions panel
        JPanel activeAuctionsPanel = new JPanel(new BorderLayout());
        auctionList = new JList<>(auctionListModel);
        activeAuctionsPanel.add(new JScrollPane(auctionList), BorderLayout.CENTER);
        tabbedPane.addTab("Active Auctions", activeAuctionsPanel);
        // Item Details Area (goes in Active Panel)
        itemDetailsTextArea = new JTextArea(8, 20); // specify the number of rows and columns
        itemDetailsTextArea.setLineWrap(true);
        itemDetailsTextArea.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // A thicker border with thickness of 2
        itemDetailsTextArea.setBorder(border);
        // Adding the text area to the right side of the panel
        JScrollPane itemDetailsScrollPane = new JScrollPane(itemDetailsTextArea);
        activeAuctionsPanel.add(itemDetailsScrollPane, BorderLayout.EAST);
        auctionList.addListSelectionListener(e -> displayItemDetails());

        // Categories panel
        JPanel categoriesPanel = new JPanel(new BorderLayout());
        //categoryList = new JList<>(categoryListModel);
        //categoriesPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        tabbedPane.addTab("Categories", categoriesPanel);

        // Create "Reports" panel with tabs for active and all auctions
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JTabbedPane reportsTabbedPane = new JTabbedPane();
        // All Auctions tab
        JPanel allReportsPanel = new JPanel(new BorderLayout());
        JList<String> allAuctionsList = new JList<>(auctionListModel); // Assuming same model for simplicity
        allReportsPanel.add(new JScrollPane(allAuctionsList), BorderLayout.CENTER);
        reportsTabbedPane.addTab("All Auctions", allReportsPanel);
        reportsPanel.add(reportsTabbedPane, BorderLayout.CENTER);
        tabbedPane.addTab("Reports", reportsPanel);

        // Initialize admin features if the current user is an admin
        if (controller.getCurrentUser().equals("admin")) {
            initializeAdminFeatures(categoriesPanel);
        }
//if user clicks an item in itemdetailstext, bidding starts, text is added to txtbox, and
        // Add Item Panel
        JPanel addItemPanel = new JPanel();
        addItemPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout for better alignment
        addItemInputFields(addItemPanel);
        tabbedPane.addTab("Add Item", addItemPanel);

    setupBidComponents();
    }

    private void setupBidComponents() {
        bidAmountField = new JTextField();
        bidAmountField.setBounds(735, 575, 150, 25);
        frame.add(bidAmountField);

        JButton placeBidButton = new JButton("Place Bid");
        placeBidButton.setBounds(735, 600, 150, 30);
        placeBidButton.addActionListener(e -> placeBid()); // Delegate to placeBid method
        frame.add(placeBidButton);
    }

    private void addItemInputFields(JPanel addItemPanel) {
        // Item Input Fields
        JLabel itemInputLabel = new JLabel("Add Item:");
        addItemPanel.add(itemInputLabel);
        addItemPanel.add(new JLabel());

        // Input field for item name
        itemNameField = new JTextField();
        addItemPanel.add(new JLabel("Enter item name"));
        addItemPanel.add(itemNameField);

        // Input field for item description
        descriptionField = new JTextField();
        addItemPanel.add(new JLabel("Enter item description"));
        addItemPanel.add(descriptionField);

        // Input field for item price
        priceField = new JTextField();
        addItemPanel.add(new JLabel("Enter buy it now price"));
        addItemPanel.add(priceField);

        // Input field for image URL
        imageUrlField = new JTextField();
        addItemPanel.add(new JLabel("Enter image URL"));
        addItemPanel.add(imageUrlField);

        // Input field for item type
        itemTypeField = new JTextField();
        addItemPanel.add(new JLabel("Enter item type"));
        addItemPanel.add(itemTypeField);

        // CheckBox to indicate auction item
        isAuctionCheckBox = new JCheckBox("Auction");
        addItemPanel.add(new JLabel("Is this active?"));
        addItemPanel.add(isAuctionCheckBox);

        // Button to add the item
        JButton addButton = new JButton("Add Item");
        addItemPanel.add(addButton);
        addButton.addActionListener(e -> addItem());

    }

    private void initializeAdminFeatures(JPanel categoriesPanel) {
        JPanel holdersPanel = new JPanel();
        holdersPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Use GridLayout for alignment

        // Input field for new category
        categoryField = new JTextField();
        categoriesPanel.add(categoryField, BorderLayout.SOUTH); // Add input field to the bottom
        // Button to add category
        addCategoryButton = new JButton("Add Category");
        categoriesPanel.add(addCategoryButton, BorderLayout.EAST); // Add button to the right
        // Add categories panel to the tabbed pane
        tabbedPane.addTab("Categories", categoriesPanel);

        // Seller's Commission and Buyer's Premium
        JLabel commissionLabel = new JLabel("Seller's Commission:");
        holdersPanel.add(commissionLabel);

        // Input field for seller's commission
        JTextField sellerCommissionField = new JTextField();
        holdersPanel.add(sellerCommissionField);

        // Button to set seller's commission
        JButton setCommissionButton = new JButton("Set Commission");
        holdersPanel.add(setCommissionButton);

        JLabel buyerPremiumLabel = new JLabel("Buyer's Premium:");
        holdersPanel.add(buyerPremiumLabel);

        // Input field for buyer's premium
        JTextField buyerPremiumField = new JTextField();
        holdersPanel.add(buyerPremiumField);

        // Button to set buyer's premium
        JButton setPremiumButton = new JButton("Set Premium");
        holdersPanel.add(setPremiumButton);


        // Place the holdersPanel in the bottom right corner of the frame
        holdersPanel.setBounds(100, 600, 500, 100);
        frame.add(holdersPanel);
        String commission = sellerCommissionField.getText();
        String premium = buyerPremiumField.getText();
        setCommissionButton.addActionListener(e -> controller.setSellersCommission(commission)); // Set seller's commission
        setPremiumButton.addActionListener(e -> controller.setBuyerPremium(premium)); // Set buyer's premium
        addCategoryButton.addActionListener(e -> {
            String category = categoryField.getText();
            controller.addCategory(category, categoryListModel);
        });
    }


    private void addItem() {
        String itemName = itemNameField.getText();
        String description = descriptionField.getText();
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid price.");
            return;
        }
        String imageUrl = imageUrlField.getText();
        boolean isAuction = isAuctionCheckBox.isSelected();
        String itemType = itemTypeField.getText();

        // Add the item to the system
        controller.addItem(itemName, description, price, imageUrl, isAuction, itemType, auctionListModel);
        JOptionPane.showMessageDialog(frame, "Item added successfully: " + itemName);

        // Clear the text fields after adding the item
        itemNameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        imageUrlField.setText("");
        itemTypeField.setText("");
        isAuctionCheckBox.setSelected(false);
    }

    private void displayItemDetails() {
        String selectedItemName = auctionList.getSelectedValue();
        Item selectedItem = controller.getItemByName(selectedItemName); // Controller provides the item

        if (selectedItem != null) {
            itemDetailsTextArea.setText(
                    "Item Name: " + selectedItem.getItemName() + "\n" +
                            "Description: " + selectedItem.getDescription() + "\n" +
                            "Price: " + selectedItem.getBuyItNowPrice() + "\n" +
                            "Type: " + selectedItem.getItemType() + "\n" +
                            "Is Auction: " + selectedItem.isAuction()
            );
        }
        setupBidComponents();

    }

    private void placeBid() {
        String bidAmountText = bidAmountField.getText();
        double bidAmount;
        try {
            bidAmount = Double.parseDouble(bidAmountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid bid amount.");
            return;
        }
        String selectedItemName = auctionList.getSelectedValue();
        controller.placeBid(selectedItemName, bidAmount);
    }
    private void showBid() {
        String selectedItemName = auctionList.getSelectedValue();
        double myBidAmount;
        try {
            myBidAmount = Double.parseDouble(bidAmountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid bid amount.");
            return;
        }
        controller.showBid(selectedItemName, myBidAmount);
    }
}
