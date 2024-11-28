package GUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
    }

   /* private void setUpEventListeners() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchTextField.getText();
                controller.handleSearch(searchQuery);
                // Search for items based on the search query
            }
        });

        bidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bid = Double.parseDouble(bidAmount.getText());
                controller.handleBid(bid);
                // Place a bid on the selected item
            }
        });

        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = txtItemName.getText();
                String itemDescription = txtItemDescription.getText();
                double startPrice = Double.parseDouble(txtStartPrice.getText());
                controller.handleAddItem(itemName, itemDescription, startPrice);
                // Add a new item to the system
            }
        });
    } */
}