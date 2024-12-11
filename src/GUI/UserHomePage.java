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
    private JPanel reportsTab;
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
    private JLabel lblReports;
    private JLabel lblMyAuctions;
    private JPanel myBidsTab;
    private JLabel lblMyBids;
    private JPanel profilePanel;
    private JTextArea txtProfile;
    private JLabel toBuyLbl;
    private JLabel lblUserName;
    private JScrollPane myBidsScrollPane;
    private JTextArea reportsText;
    private JButton buyersReportBtn;
    private JButton sellersReportBtn;
    private JTextField txtImageUrl;
    private JTextField txtEndTime;
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
    private DefaultListModel<Item> listModel;

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

    public void setWelcomeLabel(User user) {
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
//        buyerReportArea = new JTextArea(10, 50);
//        buyerReportArea.setEditable(false);

        bidAmount = new JTextField(10);
        bidButton = new JButton("Place Bid");


        // Generate categories list
        String[] categories = {"Electronics", "Fashion", "Home & Garden", "Sporting Goods", "Toys & Hobbies", "Other"};
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfCategories.setViewportView(categoryList);

        // Generate active auctions list from ItemManager
        listModel = new DefaultListModel<>();  // Initialize the list model once
        auctionsList = new JList<>(listModel);
        auctionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Initialize the myBidsTable
        myBidsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Current Bid", "Image URL"}, 0));
        myBidsScrollPane = new JScrollPane(myBidsTable);
        setupTabs();

        // Initialize the active auctions list with items from ItemManager
        updateActiveAuctionsList();
    }

    private void setupTabs() {
        tabbedPane.removeAll();
        tabbedPane.addTab("Home", homeTab);
        tabbedPane.addTab("Buy", buyTab);
        tabbedPane.addTab("Sell", sellTab);
        tabbedPane.addTab("Reports", reportsTab);
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

    private void updateActiveAuctionsList() {
        // Get the current list of active auctions from the ItemManager
        List<Item> activeAuctions = itemManager.getActiveAuctions();

        // Get the DefaultListModel associated with your auctionsList
        DefaultListModel<Item> listModel = (DefaultListModel<Item>) auctionsList.getModel();

        // Clear the current model and add all active auctions from the ItemManager
        listModel.clear();
        for (Item item : activeAuctions) {
            listModel.addElement(item);
        }

        // Optionally, you can make sure the newly updated list is visible
        auctionsList.revalidate();
        auctionsList.repaint();
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
                long endTime = Long.parseLong(txtEndTime.getText());
                handleAddItem(itemName, itemDescription, startPrice, imageUrl,endTime);
            }
        });

        buyersReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportsText.setText("");
                showBuyerReport(userController.getCurrentUser());


            }
        });
        sellersReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportsText.setText("");
                showSellerReport(userController.getCurrentUser());
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Login());
            }
        });

//        auctionsList.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                Item selectedItem = auctionsList.getSelectedValue();
//                if (selectedItem != null) {
//                    txaItemInfo.setText(String.format("Item Name: %s\n" +
//                                    "Description: %s\n" +
//                                    "Start Price: $%.2f\n" +
//                                    "Buy It Now Price: $%.2f\n" +
//                                    "Category: %s\n" +
//                                    "Auction Active: %b\n",
//                            selectedItem.getItemName(), selectedItem.getDescription(),
//                            selectedItem.getStartPrice(), selectedItem.getBuyItNowPrice(),
//                            selectedItem.getItemType(), selectedItem.isAuction()));
//                }
//            }
//        });
    }

    private void handleSearch(String searchQuery) {
        List<Item> searchResults = itemManager.searchItems(searchQuery);
        showSearchResults(searchResults);
    }

     void handleBid() {
        int selectedRow = buyTable.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) buyTable.getValueAt(selectedRow, 0); // Get the selected item name
            System.out.println("Selected item name: " + itemName);
            Item item = itemManager.getItemByName(itemName); // Fetch the item from ItemManager
            if (item != null) {
                try {
                    double bidAmountValue = Double.parseDouble(bidAmount.getText());
                    System.out.println("Bid amount: " + bidAmountValue);

                    // Ensure the bid amount is greater than the current bid
                    if (bidAmountValue <= item.getCurrentbid()) {
                        showError("Bid amount must be greater than the current bid.");
                        return;
                    }

                    // Add the bid to the item
                    Bid newBid = new Bid(userController.getCurrentUser(), bidAmountValue);
                    item.addBid(newBid);
                    System.out.println("Bid added successfully");

                    DefaultTableModel buyTableModel = (DefaultTableModel) buyTable.getModel();
                    buyTableModel.removeRow(selectedRow);

                    // Update the My Bids Table
                    updateMyBidsTable(item);

                    // Optional: Highlight the My Bids Tab
                    tabbedPane.setSelectedComponent(myBidsTab);

                } catch (NumberFormatException e) {
                    showError("Invalid bid amount. Please enter a valid number.");
                }
            } else {
                System.out.println("Item not found");
            }
        } else {
            System.out.println("No row selected");
        }
    }


    public void handleAddItem(String itemName, String itemDescription, double startPrice, String imageUrl,long endTime) {
        boolean isAuction = true;
        String category = categoryList.getSelectedValue();

        if (category == null) {
            showError("Please select an item category.");
            return;
        }

        // Create a new Item with the provided details
        Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, category, startPrice, endTime);

        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            currentUser.listItem(newItem);  // Add the item to the user's listings
            showInfo("Item added successfully!");

            // Add the item to the backend (ItemManager)
            itemManager.addItem(newItem);

            // Update the active auctions list in the view (JList)
            updateActiveAuctionsList();  // This will refresh the JList with the updated list of active auctions

            // Add the item to the "My Auctions" list (assuming you have this method to update that view)
            addItemToMyAuctions(newItem);  // Add to My Auctions list
            addItemToBuyTab(newItem); // Add to Buy Tab
            // Switch to the "My Auctions" tab
            switchToMyAuctionsTab();

            // Clear the input fields after adding the item
            txtItemName.setText("");  // Clear the item name field
            txtItemDescription.setText("");  // Clear the item description field
            txtStartPrice.setText("");  // Clear the start price field
            txtImageUrl.setText("");  // Clear the image URL field
        } else {
            showError("No user is currently logged in.");
        }
    }
    private void addItemToBuyTab(Item newItem) {
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        model.addRow(new Object[]{newItem.getItemName(),newItem.getDescription(),newItem.getStartPrice(),newItem.getImageUrl()});
    }

    public void showSellerReport(User user) {
        double totalWinningBids = 0;
        double totalShippingCosts = 0;
        double totalSellerCommission = 0;

        JTextArea sellerReportArea = new JTextArea();
        reportsText.append("Seller's Report for " + user.getUsername() + "\n");
        reportsText.append("--------------------------------------------------\n");
        reportsText.append("Item Name | Price | Seller's Commission | Shipping\n");
        reportsText.append("--------------------------------------------------\n");

        for (Item item : user.getSoldItems()) {
            if (item != null) {
                double price = item.getBuyItNowPrice();
                double sellersCommission = price * 0.20;
                double shippingCost = 10.0;

                totalWinningBids += price;
                totalSellerCommission += sellersCommission;
                totalShippingCosts += shippingCost;

                reportsText.append(String.format("%s | %.2f | %.2f | %.2f%n", item.getItemName(), price, sellersCommission, shippingCost));
            }
        }

        double totalProfits = totalWinningBids - totalSellerCommission - totalShippingCosts;

        reportsText.append("--------------------------------------------------\n");
        reportsText.append(String.format("Total Winning Bids: %.2f%n", totalWinningBids));
        reportsText.append(String.format("Total Shipping Costs: %.2f%n", totalShippingCosts));
        reportsText.append(String.format("Total Seller's Commissions: %.2f%n", totalSellerCommission));
        reportsText.append(String.format("Total Profits: %.2f%n", totalProfits));

     //   JOptionPane.showMessageDialog(this, new JScrollPane(reportsText), "Seller Report", JOptionPane.INFORMATION_MESSAGE);
    }

        public void showBuyerReport (User user){
            double totalSpent = 0;
            double totalShippingCosts = 0;

            JTextArea buyerReportArea = new JTextArea();
            reportsText.append("Buyer's Report for " + user.getUsername() + "\n");
            reportsText.append("--------------------------------------------------\n");
            reportsText.append("Item Name | Price | Shipping\n");
            reportsText.append("--------------------------------------------------\n");

            for (Item item : user.getBoughtItems()) {
                if (item != null) {
                    double price = item.getBuyItNowPrice();
                    double shippingCost = 10.0;

                    totalSpent += price;
                    totalShippingCosts += shippingCost;

                    reportsText.append(String.format("%s | %.2f | %.2f%n", item.getItemName(), price, shippingCost));
                }
            }

            reportsText.append("--------------------------------------------------\n");
            reportsText.append(String.format("Total Spent: %.2f%n", totalSpent));
            reportsText.append(String.format("Total Shipping Costs: %.2f%n", totalShippingCosts));

           // JOptionPane.showMessageDialog(this, new JScrollPane(reportsText), "Buyer Report", JOptionPane.INFORMATION_MESSAGE);
        }

    private void populateBuyTab() {
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        itemController.populateDefaultActiveAuctions();
        List<Item> preMadeItems = itemController.getAllItems();
        for (Item item : preMadeItems) {
            model.addRow(new Object[]{item.getItemName(), item.getDescription(), item.getStartPrice(), item.getImageUrl()});
        }
    }

    public void showConcludedAuctions() {
        List<Item> concludedAuctions = itemManager.getConcludedAuctions();
        showConcludedAuctions(concludedAuctions);
    }

    private void updateMyBidsTable(Item item) {
        DefaultTableModel myBidsModel = (DefaultTableModel) myBidsTable.getModel();
        boolean itemExists = false;

        // Check if the item already exists in the My Bids Table
        for (int i = 0; i < myBidsModel.getRowCount(); i++) {
            String existingItemName = (String) myBidsModel.getValueAt(i, 0);
            if (existingItemName.equals(item.getItemName())) {
                // Update the bid amount for the existing item
                myBidsModel.setValueAt(item.getCurrentbid(), i, 2);
                itemExists = true;
                break;
            }
        }

        // If the item doesn't exist, add it to the table
        if (!itemExists) {
            myBidsModel.addRow(new Object[]{
                    item.getItemName(),
                    item.getDescription(),
                    item.getCurrentbid(),
                    item.getImageUrl()
            });
        }
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


    public JLabel getLblUserName() {
        return lblUserName;
    }

    public JList<String> getCategoryList() {
        return categoryList;
    }

    public JTable getBuyTable() {
        return buyTable;
    }
}