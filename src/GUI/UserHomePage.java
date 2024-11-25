package GUI;

import javax.swing.*;

public class UserHomePage extends JFrame {

    // Fields referenced in the Designer
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
    private JButton logoutButton;
    private JLabel bidsyTitle;
    private JPanel tabsPanel;
    private JScrollPane activeAuctions;
    private JTextField bidAmount;
    private JButton bidButton;
    private JLabel txtWelcome;
    private JLabel txtAuction;
    private JLabel txtItemCategory;
    private JScrollPane listOfCategories;

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
    }
}
