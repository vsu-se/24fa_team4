// File: src/GUI/UserHomePage.java
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
    private JList<String> categoriesList;
    private JTextField txtImageUrl;
    private JTable myAuctionsTable;
    private JComboBox<String> categoryComboBox;
    private JButton buyerReportBtn;
    private JTextArea buyerReportArea;

    private ItemController itemController;
    private String username;
    private String password;
    private UserController userController;

    public UserHomePage(String username, String password, UserController userController) {
        this.username = username;
        this.password = password;
        this.userController = userController;
        itemController = new ItemController();

        setTitle("Bidsy");
        setIconImage(new ImageIcon("src/GUI/bidsy_mascot2.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        customizeComponents();
        setUpEventListeners();

        add(mainPanel);
        setVisible(true);
    }

    private void customizeComponents() {
        searchTextField.setText("Search for an item?");
        bidsyTitle.setText("Bidsy");

        txtImageUrl = new JTextField();
        categoryComboBox = new JComboBox<>(new String[]{"Electronics", "Fashion", "Home & Garden", "Sporting Goods", "Toys & Hobbies", "Other"});
        buyerReportBtn = new JButton("Generate Buyer Report");
        buyerReportArea = new JTextArea(10, 50);
        buyerReportArea.setEditable(false);

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

        myAuctionsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL", "Is Auction", "Item Type"}, 0));
        JScrollPane scrollPane = new JScrollPane(myAuctionsTable);
        myAuctionsTab.setLayout(new BorderLayout());
        myAuctionsTab.add(scrollPane, BorderLayout.CENTER);

        //Sell tab loaded info:
            //category list -
        scrollPane.setViewportView(categoriesList);
        List<String> categories = ItemManager.getInstance().getTypes();
             //Create a DefaultListModel and add all categories
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String category : categories) {
            listModel.addElement(category);
        }

        // Set the model to the JList
        categoriesList.setModel(listModel);

    }
    private void setUpEventListeners() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchTextField.getText();
                itemController.searchItems(searchQuery);
            }
        });

        bidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = myAuctionsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String itemName = (String) myAuctionsTable.getValueAt(selectedRow, 0);
                    double bidAmountValue = Double.parseDouble(bidAmount.getText());
                    Bidder bidder = new User(username, password);
                    Bid bid = new Bid(bidder, bidAmountValue);
                    itemController.placeBid(itemName, bid);
                } else {
                    showError("Please select an item to place a bid.");
                }
            }
        });

        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = txtItemName.getText();
                String itemDescription = txtItemDescription.getText();
                double startPrice = Double.parseDouble(txtStartPrice.getText());
                String imageUrl = txtImageUrl.getText();
                String category = (String) categoryComboBox.getSelectedItem();

                Item item = new Item(itemName, itemDescription, startPrice, imageUrl, true, category, startPrice);
                itemController.addItem(itemName, itemDescription, startPrice, imageUrl, true, category, startPrice);
                addItemToMyAuctions(item);

                // Disable input fields and addItemBtn
                txtItemName.setEnabled(false);
                txtItemDescription.setEnabled(false);
                txtStartPrice.setEnabled(false);
                txtImageUrl.setEnabled(false);
                categoryComboBox.setEnabled(false);
                addItemBtn.setEnabled(false);
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
                // Dispose of the current UserHomePage frame
                dispose();

                // Redirect to the Login page
                SwingUtilities.invokeLater(() -> new Login());
            }
        });
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

    public JButton getSearchBtn() {
        return searchBtn;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JButton getBidButton() {
        return bidButton;
    }

    public JTextField getBidAmount() {
        return bidAmount;
    }

    public JTextField getItemName() {
        return txtItemName;
    }

    public JTextField getDescription() {
        return txtItemDescription;
    }

    public JTextField getStartPrice() {
        return txtStartPrice;
    }

    public JTextField getImageUrl() {
        return txtImageUrl;
    }

    public JCheckBox getIsAuction() {
        return new JCheckBox();
    }

    public JComboBox<String> getItemType() {
        return null;
    }

    public Item getSelectedItem() {
        // Get the selected item from the UI
        return null;
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public AbstractButton getAddItemBtn() {
        return addItemBtn;
    }

    public void showSearchResults(List<Item> searchResults) {
        // Display the search results in the UI
    }

    public void showConcludedAuctions(List<Item> concludedAuctions) {

    }

    public void addItemToMyAuctions(Item item) {
        // Add the item to the "My Auctions" tab
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

    public void switchToMyAuctionsTab() {
        tabbedPane.setSelectedComponent(myAuctionsTab);
    }

    public void startAuction(Item item, long endTime) {
        // Set auction details and start the auction
        item.setAuction(true);
        item.setEndTime(endTime);
        itemController.startAuction(item.getItemName(), endTime);
        showInfo("Auction started successfully!");
    }

    public static void main(String[] args) {
        // Example usage
        UserController userController = new UserController();
        new UserHomePage("username", "password", userController);
    }
}