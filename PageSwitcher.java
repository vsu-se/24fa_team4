package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageSwitcher extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public PageSwitcher() {
        setTitle("Page Switcher");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        JPanel homePanel = createHomePanel();
        JPanel nextPagePanel = createNextPagePanel();

        // Add panels to CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(nextPagePanel, "NextPage");

        // Add main panel to frame
        add(mainPanel);

        setLocationRelativeTo(null); // Center the window
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Home Page", SwingConstants.CENTER);
        JButton nextButton = new JButton("Go to Next Page");

        // ActionListener for the button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the next page
                cardLayout.show(mainPanel, "NextPage");
            }
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNextPagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Next Page", SwingConstants.CENTER);
        JButton backButton = new JButton("Back to Home");

        // ActionListener for the button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to the home page
                cardLayout.show(mainPanel, "Home");
            }
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PageSwitcher gui = new PageSwitcher();
            gui.setVisible(true);
        });
    }
}

