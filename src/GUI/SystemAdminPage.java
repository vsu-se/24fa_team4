package GUI;

import ebay.*;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SystemAdminPage extends UserHomePage {

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
    private JTextField buyersTxt;
    private JTextField sellersTxt;
    private JLabel buyerslbl;
    private JLabel sellerslbl;
    private JLabel buyersPremiumlbl;
    private JLabel sellerCommissionlbl;
    private JPanel usersList;
    private JTextField txtImageUrl;
    private JComboBox<String> categoryComboBox;
    private JTable myAuctionsTable;

    private UserController userController;
    private String username;
    private String password;
    private ItemController itemController;
    private ItemManager itemManager;

    public SystemAdminPage(String username, String password, UserController userController, ItemManager itemManager, ItemController itemController) {
        super(username, password, userController, itemManager, itemController);
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


        User currentUser = userController.getCurrentUser();
        setWelcomeLabel(currentUser);
        saveBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


}