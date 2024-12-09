// src/view/UserHomePage.java
package view;

import model.*;
import controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserHomePage extends JFrame {

    private UserManager userManager;
    private User user;

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
    private JTable myBidsTable;
    private DefaultListModel<Item> listModel;

    public UserHomePage(UserManager userManager, User user) {
        this.userManager = userManager;
        this.user = user;
        setTitle("Bidsy");
        setIconImage(new ImageIcon("src/GUI/bidsy_mascot2.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        add(mainPanel);
        customizeComponents();
        setVisible(true);
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

        listModel = new DefaultListModel<>();
        auctionsList = new JList<>(listModel);
        auctionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTabs();

        buyTable.setFocusable(true);
        buyTable.setEnabled(true);
        buyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupTabs() {
        tabbedPane.removeAll();
        tabbedPane.addTab("Home", homeTab);
        tabbedPane.addTab("Buy", buyTab);
        tabbedPane.addTab("Sell", sellTab);
        tabbedPane.addTab("Categories", categoriesTab);
        tabbedPane.addTab("My Auctions", myAuctionsTab);
        tabbedPane.addTab("My Bids", myBidsTab);

        myAuctionsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL", "Is Auction", "Categories"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JScrollPane scrollPane = new JScrollPane(myAuctionsTable);
        myAuctionsTab.setLayout(new BorderLayout());
        myAuctionsTab.add(scrollPane, BorderLayout.CENTER);

        buyTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL,Category"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JScrollPane buyScrollPane = new JScrollPane(buyTable);
        buyTab.setLayout(new BorderLayout());
        buyTab.add(buyScrollPane, BorderLayout.CENTER);

        JPanel buyTabBottomPanel = new JPanel();
        buyTabBottomPanel.add(new JLabel("Bid Amount:"));
        buyTabBottomPanel.add(bidAmount);
        buyTabBottomPanel.add(bidButton);
        buyTab.add(buyTabBottomPanel, BorderLayout.SOUTH);

        DefaultTableModel myAuctionsModel = new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL", "Is Auction", "Categories"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myAuctionsTable.setModel(myAuctionsModel);

        myBidsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Bid Amount", "Image URL"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane myBidsScrollPane = new JScrollPane(myBidsTable);
        myBidsTab.setLayout(new BorderLayout());
        myBidsTab.add(myBidsScrollPane, BorderLayout.CENTER);
    }

    public void updateActiveAuctionsList(List<Item> activeAuctions) {
        listModel.clear();
        for (Item item : activeAuctions) {
            listModel.addElement(item);
        }
        auctionsList.revalidate();
        auctionsList.repaint();
    }

    public void populateBuyTab(List<Item> preMadeItems) {
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        for (Item item : preMadeItems) {
            model.addRow(new Object[]{item.getItemName(), item.getDescription(), item.getStartPrice(), item.getImageUrl()});
        }
        buyTable.revalidate();
        buyTable.repaint();
    }

    public void setWelcomeLabel(String username) {
        lblUserName.setText("Username: " + username);
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

    public void addBidToMyBids(Bid bid, Item item) {
        DefaultTableModel model = (DefaultTableModel) myBidsTable.getModel();
        model.addRow(new Object[]{
                item.getItemName(),
                item.getDescription(),
                bid.getBidAmount(),
                item.getImageUrl(),
                item.getStartPrice()
        });
    }

    public void addItemToBuyTab(Item item) {
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        model.addRow(new Object[]{
                item.getItemName(),
                item.getDescription(),
                item.getStartPrice(),
                item.getImageUrl()
        });
    }

    public void switchToMyAuctionsTab() {
        tabbedPane.setSelectedComponent(myAuctionsTab);
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
    public JTextArea getTxaItemInfo() { return txaItemInfo; }
    public JTable getBuyTable() { return buyTable; }
    public JList<String> getCategoryList() { return categoryList; }
    public JTextField getStartPrice() { return txtStartPrice; }
}