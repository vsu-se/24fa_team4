package GUI;

import ebay.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SystemAdminPage extends UserHomePage{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton logoutButton;
    private JPanel tabsPanel;
    private JTabbedPane tabbedPane;
    private JPanel homeTab;
    private JLabel txtWelcome;
    private JPanel profilePanel;
    private JTextArea txtProfile;
    private JPanel buyTab;
    private JScrollPane activeAuctions;
    private JTextArea txaItemInfo;
    private JTextField bidAmount;
    private JButton bidButton;
    private JLabel lblBidAmount;
    private JPanel sellTab;
    private JLabel txtAuction;
    private JPanel addItemPanel;
    private JLabel lblItemName;
    private JLabel lblItemDescription;
    private JLabel lblStartPrice;
    private JScrollPane listOfCategories;
    private JLabel lblItemCategory;
    private JTextField txtItemDescription;
    private JTextField txtItemName;
    private JTextField txtStartPrice;
    private JButton addItemBtn;
    private JPanel categoriesTab;
    private JLabel lblCategories;
    private JScrollPane scrollPaneCategories;
    private JPanel myAuctionsTab;
    private JLabel lblMyAuctions;
    private JPanel myBidsTab;
    private JLabel lblMyBids;
    private JPanel bottomPanel;
    private JLabel lblCustomerService;
    private JLabel bidsyTitle;
    private JPanel concludedAuctions;
    private JButton saveBttn;
    private JButton addCategory;
    private JButton searchBtn;
    private JButton endBtn;
    private JTextField searchTextField;
    private JTextField nameTextField;
    private JLabel categoryName;
    private JPanel userlList;

    public SystemAdminPage() {
        super();
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

        addCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                JLabel categoryLabel = new JLabel(name);
            }
        });

        endBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get Auction then remove it from
            }
        });

        saveBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.saveToFile("gui_state.txt");
                    JOptionPane.showMessageDialog(null, "State saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to save state: " + ex.getMessage());
                }
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
