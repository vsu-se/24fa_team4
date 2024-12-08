package GUI;

import ebay.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserHomePage extends JFrame {

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTabbedPane tabbedPane;
    private JPanel homeTab;
    private JPanel buyTab;
    private JPanel sellTab;
    private JPanel categoriesTab;
    private JPanel myAuctionsTab;
    private JTextField searchTextField;
    private JLabel bidsyTitle;
    private JPanel tabsPanel;
    private JScrollPane activeAuctions;
    private JTextField bidAmount;
    private JButton bidButton;
    private JLabel txtWelcome;
    private JLabel txtAuction;
    private JLabel lblItemCategory;
    private JScrollPane listOfCategories;
    private JButton logoutButton;
    private JTextArea txaItemInfo;
    private JLabel lblBidAmount;
    private JLabel lblItemName;
    private JTextField txtItemName;
    private JLabel lblItemDescription;
    private JTextField txtItemDescription;
    private JLabel lblStartPrice;
    private JTextField txtStartPrice;
    private JPanel addItemPanel;
    private JButton addItemBtn;
    private JButton searchBtn;
    private JLabel lblCustomerService;
    private JLabel lblCategories;
    private JScrollPane scrollPaneCategories;
    private JLabel lblMyAuctions;
    private JPanel myBidsTab;
    private JLabel lblMyBids;
    private JPanel profilePanel;
    private JTextArea txtProfile;
    private JLabel toBuyLbl;
    private JLabel lblUserName;
    private JTextField txtImageUrl;
    private JTable myAuctionsTable;
    private JTable buyTable;
    private JList<String> categoryList;
    private JList<Item> auctionsList;
    private JButton buyerReportBtn;
    private JTextArea buyerReportArea;
    private ItemController itemController;
    private String username;
    private String password;
    private UserController userController;
    private ItemManager itemManager;
    private JTable myBidsTable;
    private JButton placeBidButton;
    private User sellerUser;
    private User buyerUser;

    public UserHomePage(String username, String password, UserController userController, ItemManager itemManager, ItemController itemController) {
        this.username = username;
        this.password = password;
        this.userController = userController;
        this.itemManager = itemManager;
        this.itemController = itemController;

        setTitle("Bidsy");
        setIconImage(new ImageIcon("src/GUI/bidsy_mascot2.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        add(mainPanel);
        customizeComponents();

        User currentUser = userController.getCurrentUser();
        setWelcomeLabel(currentUser);

        setVisible(true);
        populateBuyTab();
        setUpEventListeners();
    }

    private void setWelcomeLabel(User user) {
        if (user != null) {
            lblUserName.setText("Username: " + user.getUsername());
        } else {
            lblUserName.setText("Status: Guest");
        }
    }

    private void customizeComponents() {
        searchTextField.setText("Search for an item?");
        bidsyTitle.setText("Bidsy");

        txtImageUrl = new JTextField();
        buyerReportBtn = new JButton("Generate Buyer Report");
        buyerReportArea = new JTextArea(10, 50);
        buyerReportArea.setEditable(false);

        String[] categories = {"Electronics", "Fashion", "Home & Garden", "Sporting Goods", "Toys & Hobbies", "Other"};
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfCategories.setViewportView(categoryList);

        List<Item> items = itemManager.getActiveAuctions();
        DefaultListModel<Item> listModel = new DefaultListModel<>();
        for (Item item : items) {
            listModel.addElement(item);
        }
        auctionsList = new JList<>(listModel);
        auctionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTabs();
    }

    private void setupTabs() {
        tabbedPane.removeAll();
        tabbedPane.addTab("Home", homeTab);
        tabbedPane.addTab("Buy", buyTab);
        tabbedPane.addTab("Sell", sellTab);
        tabbedPane.addTab("Categories", categoriesTab);
        tabbedPane.addTab("My Auctions", myAuctionsTab);
        tabbedPane.addTab("My Bids", myBidsTab);

        myAuctionsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL", "Is Auction", "Categories"}, 0));
        JScrollPane scrollPane = new JScrollPane(myAuctionsTable);
        myAuctionsTab.setLayout(new BorderLayout());
        myAuctionsTab.add(scrollPane, BorderLayout.CENTER);

        buyTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL,Category"}, 0));
        JScrollPane buyScrollPane = new JScrollPane(buyTable);
        buyTab.setLayout(new BorderLayout());
        buyTab.add(buyScrollPane, BorderLayout.CENTER);

        bidAmount = new JTextField(10);
        bidButton = new JButton("Place Bid");
        JPanel buyTabBottomPanel = new JPanel();
        buyTabBottomPanel.add(new JLabel("Bid Amount:"));
        buyTabBottomPanel.add(bidAmount);
        buyTabBottomPanel.add(bidButton);
        buyTab.add(buyTabBottomPanel, BorderLayout.SOUTH);

        myBidsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Bid Amount", "Image URL"}, 0));
        JScrollPane myBidsScrollPane = new JScrollPane(myBidsTable);
        myBidsTab.setLayout(new BorderLayout());
        myBidsTab.add(myBidsScrollPane, BorderLayout.CENTER);
    }

    private void setUpEventListeners() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchTextField.getText();
                handleSearch(searchQuery);
            }
        });

        bidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBid();
            }
        });

        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = txtItemName.getText();
                String itemDescription = txtItemDescription.getText();
                double startPrice = Double.parseDouble(txtStartPrice.getText());
                String imageUrl = txtImageUrl.getText();
                handleAddItem(itemName, itemDescription, startPrice, imageUrl);
            }
        });

        buyerReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateBuyerReport();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Login());
            }
        });

        auctionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item selectedItem = auctionsList.getSelectedValue();
                if (selectedItem != null) {
                    txaItemInfo.setText(String.format("Item Name: %s\n" +
                                    "Description: %s\n" +
                                    "Start Price: $%.2f\n" +
                                    "Buy It Now Price: $%.2f\n" +
                                    "Category: %s\n" +
                                    "Auction Active: %b\n",
                            selectedItem.getItemName(), selectedItem.getDescription(),
                            selectedItem.getStartPrice(), selectedItem.getBuyItNowPrice(),
                            selectedItem.getItemType(), selectedItem.isAuction()));
                }
            }
        });
    }

    private void handleSearch(String searchQuery) {
        List<Item> searchResults = itemManager.searchItems(searchQuery);
        showSearchResults(searchResults);
    }

    private void handleBid() {
        try {
            double bid = Double.parseDouble(bidAmount.getText());
            Item selectedItem = getSelectedItem();
            if (selectedItem != null) {
                User currentUser = userController.getCurrentUser();
                Bid newBid = new Bid(currentUser, bid);
                boolean bidSuccess = selectedItem.placeBid(newBid);
                if (bidSuccess) {
                    showInfo("Bid placed successfully!");
                    addBidToMyBids(newBid, selectedItem);
                } else {
                    showError("Failed to place bid. Please try again.");
                }
            } else {
                showError("No item selected. Please select an item to bid on.");
            }
        } catch (NumberFormatException e) {
            showError("Invalid bid amount. Please enter a valid number.");
        }
    }

    private void handleAddItem(String itemName, String itemDescription, double startPrice, String imageUrl) {
        boolean isAuction = getIsAuction().isSelected();
        String category = categoryList.getSelectedValue();

        if (category == null) {
            showError("Please select an item category.");
            return;
        }

        Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, category, startPrice);
        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            currentUser.listItem(newItem);
            showInfo("Item added successfully!");
            addItemToMyAuctions(newItem);
            switchToMyAuctionsTab();
        } else {
            showError("No user is currently logged in.");
        }
    }

    private void generateBuyerReport() {
        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            StringBuilder report = new StringBuilder();
            report.append("Buyer's Report for ").append(currentUser.getUsername()).append("\n");
            report.append("--------------------------------------------------\n");
            report.append("Item Name | Price | Shipping\n");
            report.append("--------------------------------------------------\n");

            double totalSpent = 0;
            double totalShippingCosts = 0;

            for (Item item : currentUser.getBoughtItems()) {
                if (item != null) {
                    double price = item.getBuyItNowPrice();
                    double shippingCost = 10.0;

                    totalSpent += price;
                    totalShippingCosts += shippingCost;

                    report.append(String.format("%s | %.2f | %.2f%n", item.getItemName(), price, shippingCost));
                }
            }

            report.append("--------------------------------------------------\n");
            report.append(String.format("Total Spent: %.2f%n", totalSpent));
            report.append(String.format("Total Shipping Costs: %.2f%n", totalShippingCosts));

            buyerReportArea.setText(report.toString());
        } else {
            showError("No user is currently logged in.");
        }
    }

    private void populateBuyTab() {
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        List<Item> preMadeItems = itemController.getPreMadeItems();
        for (Item item : preMadeItems) {
            model.addRow(new Object[]{item.getItemName(), item.getDescription(), item.getStartPrice(), item.getImageUrl()});
        }
    }

    public void showConcludedAuctions() {
        List<Item> concludedAuctions = itemManager.getConcludedAuctions();
        showConcludedAuctions(concludedAuctions);
    }

    // Getters for components
    public JButton getSearchBtn() { return searchBtn; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JButton getBidButton() { return bidButton; }
    public JTextField getBidAmount() { return bidAmount; }
    public JTextField getItemName() { return txtItemName; }
    public JTextField getDescription() { return txtItemDescription; }
    public JTextField getImageUrl() { return txtImageUrl; }
    public JButton getAddItemBtn() { return addItemBtn; }
    public JButton getLogoutButton() { return logoutButton; }
    public JButton getBuyerReportBtn() { return buyerReportBtn; }
    public JTextArea getBuyerReportArea() { return buyerReportArea; }



    public JCheckBox getIsAuction() {
        return new JCheckBox();
    }

    public Item getSelectedItem() {
        return auctionsList.getSelectedValue();
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSearchResults(List<Item> searchResults) {
        // Display the search results in the UI
    }

    public void showConcludedAuctions(List<Item> concludedAuctions) {
        // Display the concluded auctions in the UI
    }

    public void addItemToMyAuctions(Item item) {
        DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
        model.addRow(new Object[]{
                item.getItemName(),
                item.getDescription(),
                item.getBuyItNowPrice(),
                item.getImageUrl(),
                item.isAuction(),
                item.getItemType(),
                item.getStartPrice()
        });
    }

    private void addBidToMyBids(Bid bid, Item item) {
        DefaultTableModel model = (DefaultTableModel) myBidsTable.getModel();
        model.addRow(new Object[]{
                item.getItemName(),
                item.getDescription(),
                bid.getBidAmount(),
                item.getImageUrl()
        });
    }

    public void switchToMyAuctionsTab() {
        tabbedPane.setSelectedComponent(myAuctionsTab);
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        ItemManager itemManager = new ItemManager();
        ItemController itemController = new ItemController();

        new UserHomePage("username", "password", userController, itemManager, itemController);
    }
}