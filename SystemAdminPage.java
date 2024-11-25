package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemAdminPage extends UserHomePage {

    private JPanel mainPanel;
    private JLabel buyersP;
    private JTextField buyersTxt;
    private JLabel sellersC;
    private JTextField sellersTxt;
    private JPanel userList;
    private JButton rmvBttn;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel tabsPanel;
    private JTabbedPane tabbedPane;
    private JPanel homeTab;
    private JPanel buyTab;
    private JPanel sellTab;
    private JPanel categoriesTab;
    private JPanel myAuctionsTab;
    private JButton save;
    private JTextField searchTextField;
    private JButton logoutButton;
    private JLabel bidsyTitle;
    private JLabel buyersPremium;
    private JLabel sellersCommision;

    public SystemAdminPage() {
        setTitle("Bidsy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);


        add(mainPanel);
        setVisible(true);

        rmvBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setUPTabs() {
        tabbedPane.removeAll();
        tabbedPane.addTab("Home", homeTab);
        tabbedPane.addTab("Buy", buyTab);
        tabbedPane.addTab("Sell", sellTab);
        tabbedPane.addTab("Categories", categoriesTab);
        tabbedPane.addTab("My Auctions", myAuctionsTab);
        tabbedPane.add("User List", userList);
    }

    public void setBuyersP(JLabel buyersP) {
        String b = buyersTxt.getText();
        buyersPremium.setText(b);
    }

    public void setSellersC() {
        String s = sellersTxt.getText();
        sellersCommision.setText(s);

    }
}
