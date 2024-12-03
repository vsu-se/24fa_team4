package GUI;

import ebay.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
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
    public JTextField searchTextField;
    private JLabel bidsyTitle;
    private JPanel tabsPanel;
    private JScrollPane activeAuctions;
    public JTextField bidAmount;
    private JButton bidButton;
    private JLabel txtWelcome;
    private JLabel txtAuction;
    private JLabel lblItemCategory;
    private JScrollPane listOfCategories;
    private JButton logoutButton;
    private JTextArea txaItemInfo;
    private JLabel lblBidAmount;
    private JLabel lblItemName;
    public JTextField txtItemName;
    private JLabel lblItemDescription;
    public JTextField txtItemDescription;
    private JLabel lblStartPrice;
    public JTextField txtStartPrice;
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
    public JTextArea txtProfile;
    public JTextField txtImageUrl;
    private JTable myAuctionsTable;
    public String errorMessage;
    private List<Item> concludedAuctions;
    public List<Item> searchResults;
    public JCheckBox isAuctionCheckBox;
    public JComboBox<String> itemTypeComboBox;

    private UserHomePageController controller;

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to set look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(UserHomePage::new);
    }

    public UserHomePage() {
        setTitle("Bidsy");
        setIconImage(new ImageIcon("src/GUI/bidsy_mascot2.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeComponents();
        customizeComponents();

        add(mainPanel);
        setVisible(true);
    }

    private void customizeComponents() {
        searchTextField.setText("Search for an item?");
        bidsyTitle.setText("Bidsy");

        setupTabs();
    }
    private void initializeComponents() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        homeTab = new JPanel();
        buyTab = new JPanel();
        sellTab = new JPanel();
        categoriesTab = new JPanel();
        myAuctionsTab = new JPanel();
        searchTextField = new JTextField();
        bidsyTitle = new JLabel();
        tabsPanel = new JPanel();
        activeAuctions = new JScrollPane();
        bidAmount = new JTextField();
        bidButton = new JButton();
        txtWelcome = new JLabel();
        txtAuction = new JLabel();
        lblItemCategory = new JLabel();
        listOfCategories = new JScrollPane();
        logoutButton = new JButton();
        txaItemInfo = new JTextArea();
        lblBidAmount = new JLabel();
        lblItemName = new JLabel();
        txtItemName = new JTextField();
        lblItemDescription = new JLabel();
        txtItemDescription = new JTextField();
        lblStartPrice = new JLabel();
        txtStartPrice = new JTextField();
        addItemPanel = new JPanel();
        addItemBtn = new JButton();
        searchBtn = new JButton();
        lblCustomerService = new JLabel();
        lblCategories = new JLabel();
        scrollPaneCategories = new JScrollPane();
        lblMyAuctions = new JLabel();
        myBidsTab = new JPanel();
        lblMyBids = new JLabel();
        profilePanel = new JPanel();
        txtProfile = new JTextArea();
        txtImageUrl = new JTextField();
        myAuctionsTable = new JTable(new DefaultTableModel(new Object[]{"Item Name", "Description", "Price", "Image URL", "Is Auction", "Item Type"}, 0));
        isAuctionCheckBox = new JCheckBox();
        itemTypeComboBox = new JComboBox<>(new String[]{"Electronics", "Furniture", "Clothing", "Books", "Toys"}); // Initialize itemTypeComboBox with categories
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
    }

    private void setUpEventListeners() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchTextField.getText();
                controller.handleSearch(searchQuery);

            }
        });

        bidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bid = Double.parseDouble(bidAmount.getText());
                controller.handleBid(bid);

            }
        });

        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = txtItemName.getText();
                String itemDescription = txtItemDescription.getText();
                double startPrice = Double.parseDouble(txtStartPrice.getText());
                String imageUrl = txtImageUrl.getText();

                controller.handleAddItem(itemName, itemDescription, startPrice, imageUrl);

            }
        });
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
        return isAuctionCheckBox;
    }

    public JComboBox<String> getItemTypeComboBox() {
        return itemTypeComboBox;
    }

    public JComboBox<String> getItemType() {
        return null;
    }

    public Item getSelectedItem() {
        int selectedRow = myAuctionsTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
            String itemName = (String) model.getValueAt(selectedRow, 0);
            String itemDescription = (String) model.getValueAt(selectedRow, 1);
            double startPrice = (double) model.getValueAt(selectedRow, 2);
            String imageUrl = (String) model.getValueAt(selectedRow, 3);
            boolean isAuction = (boolean) model.getValueAt(selectedRow, 4);
            String itemType = (String) model.getValueAt(selectedRow, 5);
            return new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, itemType, startPrice);
        }
        return null;
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public AbstractButton getAddItemBtn() {
        return addItemBtn;
    }

    public void setSearchResults(List<Item> searchResults) {
        this.searchResults = searchResults;
    }
    public void showSearchResults(List<Item> searchResults) {
        DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
        model.setRowCount(0);
        for (Item item : searchResults) {
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
    }
    public void setConcludedAuctions(List<Item> concludedAuctions) {
        this.concludedAuctions = concludedAuctions;
    }

    public List<Item> getConcludedAuctions() {
        return concludedAuctions;
    }

    public void showConcludedAuctions (List<Item> concludedAuctions) {
        DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
        model.setRowCount(0);
        for (Item item : concludedAuctions) {
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
    public void switchToMyAuctionsTab() {
        tabbedPane.setSelectedComponent(myAuctionsTab);
    }


    public void setSelectedItem(Item selectedItem) {
        // Select the item in the table
        DefaultTableModel model = (DefaultTableModel) myAuctionsTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(selectedItem.getItemName())) {
                myAuctionsTable.setRowSelectionInterval(i, i);
                break;
            }
        }

    }
}