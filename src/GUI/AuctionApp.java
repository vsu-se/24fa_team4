package GUI;

import ebay.Item;
import ebay.SystemAdmin;
import ebay.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class AuctionApp {
    private JFrame frame;
    private JTextField itemNameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField imageUrlField;
    private JTextField itemTypeField;
    private JCheckBox isAuctionCheckBox;
    private JList<String> auctionList;
    private DefaultListModel<String> auctionListModel;
    private JTextArea itemDetailsTextArea;
    private JLabel itemImageLabel;
    private JTextField categoryField;
    private DefaultListModel<String> categoryListModel;
    private JList<String> categoryList;
    private JButton addCategoryButton;
    private JTextField sellerCommissionField;
    private JTextField buyerPremiumField;
    private JButton setCommissionButton;
    private JButton setPremiumButton;
    private ArrayList<Item> items;
    private User loggedInUser;
    private SystemAdmin loggedInAdmin;
    private LoginScreen loginScreen;
    private User currentUser;
    private JTabbedPane tabbedPane;
    private JLabel mascotLabel; // For displaying the mascot image


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AuctionApp::new);
    }

    public AuctionApp() {
        items = new ArrayList<>();
        auctionListModel = new DefaultListModel<>();
        categoryListModel = new DefaultListModel<>();
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginScreen = new LoginScreen(e -> {
            String username = loginScreen.getUsername();
            String password = loginScreen.getPassword();
            String adminPassword = loginScreen.getAdminPassword();

            if (adminPassword.equals("admin")) {
                currentUser = new SystemAdmin(username, adminPassword);
            } else {
                currentUser = new User(username, password);
            }
            loginScreen.close();
            initialize();
        });
    }

    private void initialize() {
        frame = new JFrame("Bidsy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for overall frame layout

        //TOP SECTION
        // Top Panel with the label
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 100)); // Set the desired height of the top panel
        JLabel topLabel = new JLabel("Bidsy", SwingConstants.CENTER);
        topLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(topLabel, BorderLayout.CENTER);
        frame.add(headerPanel, BorderLayout.NORTH); // Add this at the north to make it the top bar

//        //BOTTOM SECTION
//        //Adding Item section on bottom left
//        JPanel bottomPanel = setupItemDetailsArea();
//        frame.add(bottomPanel, BorderLayout.EAST);
//
//        //MIDDLE SECTION
//        // TabbedPane setup (tabPanel)
//        tabbedPane = new JTabbedPane();
//        tabbedPane.setPreferredSize(new Dimension(400, 100)); // Control the size of the tabbed pane
//        Border coloredBorder = BorderFactory.createLineBorder(Color.BLUE, 5); // Create a blue line border with a thickness of 5
//        tabbedPane.setBorder(coloredBorder);
//        // frame.add(tabbedPane, BorderLayout.WEST);
//
//        setupActiveAuctionsTab();
//        toggleAdminFeatures(currentUser instanceof SystemAdmin);

        frame.setVisible(true);
    }


    private void setupActiveAuctionsTab() {
        JPanel activeAuctionsPanel = new JPanel(new BorderLayout());
        auctionList = new JList<>(auctionListModel);
        activeAuctionsPanel.add(new JScrollPane(auctionList), BorderLayout.CENTER);
        tabbedPane.addTab("Active Auctions", activeAuctionsPanel);
    }

    private void toggleAdminFeatures(boolean isAdmin) {
        if (isAdmin) {
            setupCategoriesTab();
            //setupAdminControls();
        } else {
            int index = tabbedPane.indexOfTab("Categories");
            if (index != -1) {
                tabbedPane.removeTabAt(index);
            }
            hideAdminControls();
        }
    }

    private void setupCategoriesTab() {
        if (tabbedPane.indexOfTab("Categories") == -1) {
            JPanel categoriesPanel = new JPanel(new BorderLayout());
            categoryList = new JList<>(categoryListModel);
            categoriesPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

            categoryField = new JTextField();
            categoriesPanel.add(categoryField, BorderLayout.SOUTH);

            addCategoryButton = new JButton("Add Category");
            addCategoryButton.addActionListener(e -> addCategory());
            categoriesPanel.add(addCategoryButton, BorderLayout.EAST);

            tabbedPane.addTab("Categories", categoriesPanel);
        }
    }

    private JPanel setupItemDetailsArea() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        itemDetailsTextArea = new JTextArea();
        itemDetailsTextArea.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemDetailsTextArea);

        itemImageLabel = new JLabel();
        itemImageLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemImageLabel);

        JLabel itemInputLabel = new JLabel("Add Item:");
        itemInputLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemInputLabel);

        itemNameField = new JTextField();
        itemNameField.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemNameField);

        JLabel itemNameLabel = new JLabel("Enter item name");
        itemNameLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemNameLabel);

        descriptionField = new JTextField();
        descriptionField.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(descriptionField);

        JLabel descriptionLabel = new JLabel("Enter item description");
        descriptionLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(descriptionLabel);

        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(priceField);

        JLabel priceLabel = new JLabel("Enter buy it now price");
        priceLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(priceLabel);

        imageUrlField = new JTextField();
        imageUrlField.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(imageUrlField);

        JLabel imageUrlLabel = new JLabel("Enter image URL");
        imageUrlLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(imageUrlLabel);

        itemTypeField = new JTextField();
        itemTypeField.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemTypeField);

        JLabel itemTypeLabel = new JLabel("Enter item type");
        itemTypeLabel.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(itemTypeLabel);

        isAuctionCheckBox = new JCheckBox("Auction");
        isAuctionCheckBox.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(isAuctionCheckBox);

        JButton addButton = new JButton("Add Item");
        addButton.setPreferredSize(new Dimension(100, 25));
        bottomPanel.add(addButton);

        return bottomPanel;
    }

    private void setupAdminControls() {
        // Seller's Commission and Buyer's Premium
        JLabel commissionLabel = new JLabel("Seller's Commission:");
        commissionLabel.setBounds(500, 385, 150, 25);
        frame.add(commissionLabel);

        sellerCommissionField = new JTextField();
        sellerCommissionField.setBounds(500, 420, 150, 25);
        frame.add(sellerCommissionField);

        setCommissionButton = new JButton("Set Commission");
        setCommissionButton.setBounds(500, 455, 150, 25);
        frame.add(setCommissionButton);

        JLabel buyerPremiumLabel = new JLabel("Buyer's Premium:");
        buyerPremiumLabel.setBounds(500, 490, 150, 25);
        frame.add(buyerPremiumLabel);

        buyerPremiumField = new JTextField();
        buyerPremiumField.setBounds(500, 520, 150, 25);
        frame.add(buyerPremiumField);

        setPremiumButton = new JButton("Set Premium");
        setPremiumButton.setBounds(500, 555, 150, 25);
        frame.add(setPremiumButton);
    }

    private void hideAdminControls() {
        // Hide admin controls
        if (setCommissionButton != null) {
            frame.remove(setCommissionButton);
            frame.remove(sellerCommissionField);
            frame.remove(setPremiumButton);
            frame.remove(buyerPremiumField);
        }
    }

    private void addCategory() {
        String category = categoryField.getText();
        if (!category.isEmpty()) {
            categoryListModel.addElement(category);
            categoryField.setText("");
        }
    }
}
