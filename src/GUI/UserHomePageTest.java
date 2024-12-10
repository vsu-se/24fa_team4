package GUI;

import ebay.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserHomePageTest {

    private UserController userController;
    private ItemManager itemManager;
    private ItemController itemController;
    private UserHomePage userHomePage;

    @BeforeEach
    public void setUp() {
        // Initialize controllers and managers
        userController = new UserController();
        itemManager = new ItemManager();
        itemController = new ItemController(itemManager);

        // Clear any existing items
        itemManager.clearItems();

        // Set up a test user
        User testUser = new User("testUser", "password");
        userController.setCurrentUser(testUser);

        // Create UserHomePage
        userHomePage = new UserHomePage("testUser", "password", userController, itemManager, itemController);
    }

    @Test
    public void testSetWelcomeLabel() {
        // Arrange
        User user = new User("testUser", "password");

        // Act
        userHomePage.setWelcomeLabel(user);

        // Assert
        JLabel lblUserName = userHomePage.getLblUserName();
        assertEquals("Username: testUser", lblUserName.getText());
    }

    @Test
    public void testHandleAddItem() {
        // Arrange
        String itemName = "Test Item";
        String itemDescription = "Test Description";
        double startPrice = 100.0;
        String imageUrl = "http://example.com/image.jpg";

        // Act
        userHomePage.getCategoryList().setSelectedValue("Electronics", true);
        userHomePage.handleAddItem(itemName, itemDescription, startPrice, imageUrl, 84000000);

        // Assert
        List<Item> items = itemManager.getItems();
        assertEquals(5, items.size());
        assertEquals(itemName, items.get(4).getItemName());
    }

    @Test
    public void testHandleBid() {
        // Arrange
        DefaultTableModel model = (DefaultTableModel) userHomePage.getBuyTable().getModel();
        model.addRow(new Object[]{"Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg"});
        userHomePage.getBuyTable().setRowSelectionInterval(0, 0);
        userHomePage.getBidAmount().setText("150.0");

        Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, 8400000);
        itemManager.addItem(item);

        // Act
        userHomePage.handleBid();

        // Assert
        Item updatedItem = itemManager.getItemByName("Vintage Watch");
        assertNotNull(updatedItem);
        assertEquals(1, updatedItem.getBids().size());
        assertEquals(150.0, updatedItem.getBids().get(0).getBidAmount());
    }

    @Test
    public void testGenerateBuyerReport() {
        // Arrange
        User user = userController.getCurrentUser();
        Item item = new Item("Test Item", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, 8400000);
        user.addBoughtItem(item);

        // Act
        userHomePage.generateBuyerReport();

        // Assert
        JTextArea buyerReportArea = userHomePage.getBuyerReportArea();
        String report = buyerReportArea.getText();
        assertTrue(report.contains("Buyer's Report for testUser"));
        assertTrue(report.contains("Test Item"));
    }

    @Test
    public void testSaveAndReloadUserData() {
        // Arrange
        userController.registerUser("newUser", "newPassword");

        // Act
        boolean loginSuccessfulBeforeReload = userController.login("newUser", "newPassword");
        userController.logout();

        // Simulate reinitializing the system
        userController = new UserController();
        boolean loginSuccessfulAfterReload = userController.login("newUser", "newPassword");

        // Assert
        assertTrue(loginSuccessfulBeforeReload);
        assertTrue(loginSuccessfulAfterReload);
    }
}
