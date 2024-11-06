package GUI;

import ebay.Item;

import javax.swing.*;
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
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 120, 400, 200);
        frame.add(tabbedPane);

        // Active Auctions panel
        JPanel activeAuctionsPanel = new JPanel(new BorderLayout());
        auctionList = new JList<>(auctionListModel);
        activeAuctionsPanel.add(new JScrollPane(auctionList), BorderLayout.CENTER);
        tabbedPane.addTab("Active Auctions", activeAuctionsPanel);

        // Categories panel
        JPanel categoriesPanel = new JPanel(new BorderLayout());
      //  categoryList = new JList<>(categoryListModel);
        //categoriesPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        tabbedPane.addTab("Categories", categoriesPanel);

        // Item Details Area
        itemDetailsTextArea = new JTextArea();
        itemDetailsTextArea.setBounds(550, 130, 300, 150);
        frame.add(itemDetailsTextArea);

        // Auction list
        auctionList = new JList<>(auctionListModel);
        auctionList.addListSelectionListener(e -> displayItemDetails());
        frame.add(new JScrollPane(auctionList)); // Adjust layout as needed

        // Item Details Area
        itemDetailsTextArea = new JTextArea();
        frame.add(itemDetailsTextArea);

        setupBidComponents();
        addItemInputFields();
    }

    private void setupBidComponents() {
        bidAmountField = new JTextField();
        bidAmountField.setBounds(550, 300, 150, 25);
        frame.add(bidAmountField);

        JButton placeBidButton = new JButton("Place Bid");
        placeBidButton.setBounds(550, 330, 150, 30);
        placeBidButton.addActionListener(e -> placeBid()); // Delegate to placeBid method
        frame.add(placeBidButton);
    }

    private void addItemInputFields() {
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
        imageUrlField.setBounds(20, 500, 150, 25);
        frame.add(imageUrlField);
        JLabel imageUrlLabel = new JLabel("Enter image URL");
        imageUrlLabel.setBounds(20, 525, 150, 20);
        frame.add(imageUrlLabel);

        // Input field for item type
        itemTypeField = new JTextField();
        itemTypeField.setBounds(20, 550, 150, 25);
        frame.add(itemTypeField);
        JLabel itemTypeLabel = new JLabel("Enter item type");
        itemTypeLabel.setBounds(20, 575, 150, 20);
        frame.add(itemTypeLabel);

        // CheckBox to indicate auction item
        isAuctionCheckBox = new JCheckBox("Auction");
        isAuctionCheckBox.setBounds(20, 600, 150, 25);
        frame.add(isAuctionCheckBox);

        // Button to add the item
        JButton addButton = new JButton("Add Item");
        addButton.setBounds(180, 625, 150, 25);
        frame.add(addButton);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addItem());
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

        controller.addItem(itemName, description, price, imageUrl, isAuction, itemType, auctionListModel);
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
        // Here you could call a controller method to place a bid on the selected item
    }
}
