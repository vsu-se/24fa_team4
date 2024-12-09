package GUI;

import ebay.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserHomePageTest {

    private UserController userController;
    private ItemManager itemManager;
    private ItemController itemController;
    private UserHomePage userHomePage;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        itemManager = new ItemManager();
        itemController = new ItemController(itemManager);

        itemManager.clearItems();

//        User testUser = new User("testUser", "password");
//        userController.setCurrentUser(testUser);

        userHomePage = new UserHomePage("testUser", "password", userController, itemManager, itemController);
    }

    @Test
    public void testSetWelcomeLabel() {
        User user = new User("testUser", "password");
        userHomePage.setWelcomeLabel(user);
        JLabel lblUserName = userHomePage.getLblUserName();
        assertEquals("Username: testUser", lblUserName.getText());
    }

    @Test
    public void testHandleAddItem() {
        String itemName = "Test Item";
        String itemDescription = "Test Description";
        double startPrice = 100.0;
        String imageUrl = "http://example.com/image.jpg";

        userHomePage.getCategoryList().setSelectedValue("Electronics", true);

        userHomePage.handleAddItem(itemName, itemDescription, startPrice, imageUrl);

        List<Item> items = itemManager.getItems();
        assertEquals(5, items.size());
        assertEquals(itemName, items.get(4).getItemName());
    }

    @Test
    public void testHandleBid() {
        // Ensure the buyTable is populated with the necessary items
        DefaultTableModel model = (DefaultTableModel) userHomePage.getBuyTable().getModel();
        model.addRow(new Object[]{"Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg"});

        // Select the item in the buyTable
        userHomePage.getBuyTable().setRowSelectionInterval(0, 0);
        userHomePage.getBidAmount().setText("150.0");

        // Ensure the item is added to the ItemManager
        Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0);
        itemManager.addItem(item);

        // Handle the bid
        userHomePage.handleBid();

        // Verify the bid was added correctly
        Item updatedItem = itemManager.getItemByName("Vintage Watch");
        assertNotNull(updatedItem);
        assertEquals(1, updatedItem.getBids().size());
        assertEquals(150.0, updatedItem.getBids().get(0).getBidAmount());
    }

    @Test
    public void testGenerateBuyerReport() {
        User user = userController.getCurrentUser();
        Item item = new Item("Test Item", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0);
        user.addBoughtItem(item);

        userHomePage.generateBuyerReport();

        JTextArea buyerReportArea = userHomePage.getBuyerReportArea();
        String report = buyerReportArea.getText();
        assertTrue(report.contains("Buyer's Report for testUser"));
        assertTrue(report.contains("Test Item"));
    }
}