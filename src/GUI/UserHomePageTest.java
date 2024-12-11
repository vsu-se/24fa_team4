package GUI;

import ebay.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
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
        testUser.setAuthorizedToListItems(true);
        userController.setCurrentUser(testUser);

        // Create UserHomePage
        userHomePage = new UserHomePage("testUser", "password", userController, itemManager, itemController);
        DefaultTableModel buyTableModel = (DefaultTableModel) userHomePage.getBuyTable().getModel();
        buyTableModel.setRowCount(0);
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
        Instant endTime = Instant.now().plusSeconds(84000);
        itemManager.clearItems();

        userHomePage.getCategoryList().setSelectedValue("Electronics", true);
        userHomePage.handleAddItem(itemName, itemDescription, startPrice, imageUrl, endTime);

        List<Item> items = itemManager.getItems();
        assertEquals(1, items.size());
        assertEquals(itemName, items.get(0).getItemName());
    }

    @Test
    public void testHandleBid() {
        // Arrange
        DefaultTableModel model = (DefaultTableModel) userHomePage.getBuyTable().getModel();
        model.addRow(new Object[]{"Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg"});
        userHomePage.getBuyTable().setRowSelectionInterval(0, 0);
        userHomePage.getBidAmount().setText("150.0");

        Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, Instant.now().plusSeconds(8400));
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
    public void testHandleInvalidBidAmount() {
        // Arrange
        DefaultTableModel model = (DefaultTableModel) userHomePage.getBuyTable().getModel();
        model.addRow(new Object[]{"Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg"});
        userHomePage.getBuyTable().setRowSelectionInterval(0, 0);
        userHomePage.getBidAmount().setText("invalid");

        Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, Instant.now().plusSeconds(8400));
        itemManager.addItem(item);

        // Act
        userHomePage.handleBid();

        // Assert
        Item updatedItem = itemManager.getItemByName("Vintage Watch");
        assertNotNull(updatedItem);
        assertEquals(0, updatedItem.getBids().size());
    }

    @Test
    public void testHandleAddItemWithoutCategory() {
        // Arrange
        String itemName = "Test Item";
        String itemDescription = "Test Description";
        double startPrice = 100.0;
        String imageUrl = "http://example.com/image.jpg";
        Instant endTime = Instant.now().plusSeconds(84000);
        itemManager.clearItems();

        // Act
        userHomePage.handleAddItem(itemName, itemDescription, startPrice, imageUrl, endTime);

        // Assert
        List<Item> items = itemManager.getItems();
        assertEquals(0, items.size());
    }

    @Test
    public void testHandleSearch() {
        // Arrange
        String searchQuery = "testItem";
        Item item = new Item("testItem", "description", 10.0, "url", true, "category", 10.0, Instant.now());
        itemManager.addItem(item);

        // Act
        userHomePage.handleSearch(searchQuery);

        // Assert
        // Verify that the search results are displayed
        // This can be done by checking the state of the UI components or using a mock
        List<Item> searchResults = itemManager.searchItems(searchQuery);
        assertEquals(1, searchResults.size());
        assertEquals("testItem", searchResults.get(0).getItemName());
    }

    @Test
    public void testUserLogout() {
        // Act
        userHomePage.getLogoutButton().doClick();

        // Assert
        assertNull(userController.getCurrentUser());
    }

    @Test
    public void testShowBuyerReport() {
        // Arrange
        User user = userController.getCurrentUser();
        Item item = new Item("Test Item", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, Instant.now().plusSeconds(8400));
        user.addBoughtItem(item);

        // Act
        userHomePage.showBuyerReport(user);

        // Assert
        JTextArea buyerReportArea = userHomePage.getBuyerReportArea();
        String report = buyerReportArea.getText();
        System.out.println(report);
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