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
    private JTextField txtImageUrl;
    private JTable myAuctionsTable;

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

        customizeComponents();

        add(mainPanel);
        setVisible(true);
    }

    private void customizeComponents() {
        searchTextField.setText("Search for an item?");
        bidsyTitle.setText("Bidsy");

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
                // Add a new item to the system
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
        controller.startAuction(item, endTime);
        showInfo("Auction started successfully!");
    }

}