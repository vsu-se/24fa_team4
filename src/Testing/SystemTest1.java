package Testing;
import ebay.*;
import GUI.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

    public class SystemTest1 {
        private UserController userController;
        private ItemManager itemManager;
        private ItemController itemController;
        private UserHomePage userHomePage;

        @BeforeEach
        public void setUp() {
            userController = new UserController();
            itemManager = new ItemManager();
            itemController = new ItemController(itemManager);
            userHomePage = new UserHomePage("testUser", "testPassword", userController, itemManager, itemController);
            userController.registerUser("testUser1", "testPassword");
            userController.login("testUser", "testPassword");
        }

        @Test
        public void testUserStory13_condition1() { // verifies category list

            String[] expectedCategories = {"Electronics", "Fashion", "Home & Garden", "Sporting Goods", "Toys & Hobbies", "Other"};

            JList<String> categoryList = userHomePage.getCategoryList();
            ListModel<String> listModel = categoryList.getModel();

            assertEquals(expectedCategories.length, listModel.getSize());
            for (int i = 0; i < expectedCategories.length; i++) {
                assertEquals(expectedCategories[i], listModel.getElementAt(i));
            }
        }
        @Test
        public void testUserStory1_condition1() { //verfies item can be added through GUI
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
        public void testUserStory13_condition2() { // select category

            String selectedCategory = "Fashion";
            JList<String> categoryList = userHomePage.getCategoryList();
            ListModel<String> listModel = categoryList.getModel();

            for (int i = 0; i < listModel.getSize(); i++) {
                if (listModel.getElementAt(i).equals(selectedCategory)) {
                    categoryList.setSelectedIndex(i);
                    break;
                }
            }

            assertEquals(selectedCategory, categoryList.getSelectedValue());
        }

        @Test
        public void testUserStory5_condition1() { // show items user has bid on
            User currentUser = userController.getCurrentUser();
            Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, Instant.now().plusSeconds(8400));
            itemManager.addItem(item);
            currentUser.placeBid(item, 150.0);

            List<Item> itemsBidOn = currentUser.getBids().stream().map(Bid::getItem).collect(Collectors.toList());
            assertEquals(1, itemsBidOn.size());
            assertEquals("Vintage Watch", itemsBidOn.get(0).getItemName());
        }

        @Test
        public void testUserStory4_condition1() { // place bid on item
            // Arrange
            User currentUser = userController.getCurrentUser();
            String itemName = "Test Item";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);
            itemManager.clearItems();

            Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            itemManager.addItem(newItem);

            // Act
            currentUser.placeBid(newItem, 150.0);

            // Assert
            Item updatedItem = itemManager.getItemByName(itemName);
            assertNotNull(updatedItem);
            assertEquals(1, updatedItem.getBids().size());
            assertEquals(150.0, updatedItem.getBids().get(0).getBidAmount());
        }

        @Test
        public void testUserStory4_condition2() { // bid on item invalid amount
            DefaultTableModel model = (DefaultTableModel) userHomePage.getBuyTable().getModel();
            model.addRow(new Object[]{"Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg"});
            userHomePage.getBuyTable().setRowSelectionInterval(0, 0);
            userHomePage.getBidAmount().setText("invalid");

            Item item = new Item("Vintage Watch", "Test Description", 100.0, "http://example.com/image.jpg", true, "Electronics", 100.0, Instant.now().plusSeconds(8400));
            itemManager.addItem(item);

            userHomePage.handleBid();

            Item updatedItem = itemManager.getItemByName("Vintage Watch");
            assertNotNull(updatedItem);
            assertEquals(0, updatedItem.getBids().size());
        }
        @Test
        public void testUserStory4_condition3() { //checks if bid item works
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
        public void testUserStory3_condition1() { // active auctions list
            // Arrange
            User currentUser = userController.getCurrentUser();
            itemManager.clearItems();

            // Act
            List<Item> myAuctions = currentUser.getActiveAuctions();

            // Assert
            assertEquals(0, myAuctions.size());
        }

        @Test
        public void testUserStory3_condition2() { // show active auctions for multiple items
            // Arrange
            User currentUser = userController.getCurrentUser();
            itemManager.clearItems();

            // Add items directly to the itemManager without user interaction
            String itemName1 = "Test Item 1";
            String itemName2 = "Test Item 2";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);

            Item newItem1 = new Item(itemName1, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            Item newItem2 = new Item(itemName2, itemDescription, startPrice, imageUrl, true, "Fashion", startPrice, endTime);
            itemManager.addItem(newItem1);
            itemManager.addItem(newItem2);

            // Act
            List<Item> myAuctions = currentUser.getActiveAuctions();

            // Assert
            assertEquals(2, myAuctions.size());
            assertEquals(itemName1, myAuctions.get(0).getItemName());
            assertEquals(itemName2, myAuctions.get(1).getItemName());
        }



        @Test
        public void testUserStory5_condition2() { // show my bids
            User currentUser = userController.getCurrentUser();
            String itemName = "Test Item";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);
            itemManager.clearItems();

            Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            itemManager.addItem(newItem);

            // Act
            currentUser.placeBid(newItem, 150.0);

            // Assert
            List<Bid> myBids = currentUser.getBids();
            assertEquals(1, myBids.size());
            assertEquals(150.0, myBids.get(0).getBidAmount());
            assertEquals(itemName, myBids.get(0).getItem().getItemName());
        }

        @Test
        public void UserStory6and7_condition1() { //Save State and Restore State
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

        @Test
        public void UserStory8_condition1() { // register user :success
                // Arrange
                String username = "newUser";
                String password = "newPassword";

                // Act
                userController.registerUser(username, password);

                // Assert
                assertTrue(userController.login(username, password));
            }
        @Test
        public void UserStory8_condition2() { // register user :failure username exists
            // Arrange
            String username = "existingUser";
            String password = "password";
            userController.registerUser(username, password);

            // Act
            userController.registerUser(username, "newPassword");

            // Assert
            assertTrue(userController.login(username, password));
            assertFalse(userController.login(username, "newPassword"));
        }

        @Test
        public void UserStory9_condition1() {
            // Arrange
            String username = "testUser";
            String password = "testPassword";
            userController.registerUser(username, password);

            // Act
            boolean loginSuccessful = userController.login(username, password);

            // Assert
            assertTrue(loginSuccessful);
        }
        @Test
        public void UserStory9_condition2() {
            // Arrange
            String username = "testUser";
            String password = "testPassword";
            userController.registerUser(username, password);

            // Act
            boolean loginSuccessful = userController.login(username, "wrongPassword");

            // Assert
            assertFalse(loginSuccessful);
        }
        @Test
        public void UserStory10_condition1() { // show bid history
            String username = "testUser";
            String password = "testPassword";
            userController.registerUser(username, password);
            userController.login(username, password);
            User currentUser = userController.getCurrentUser();

            String itemName = "Test Item";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);
            Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            itemManager.addItem(newItem);

            currentUser.placeBid(newItem, 150.0);
            List<Bid> bidHistory = currentUser.getBids();

            assertEquals(1, bidHistory.size());
            assertEquals(150.0, bidHistory.get(0).getBidAmount());
            assertEquals(itemName, bidHistory.get(0).getItem().getItemName());
        }
        @Test
        public void testUserStory2_condition1() {
            // Arrange
            User currentUser = userController.getCurrentUser();
            itemManager.clearItems();

            // Add items directly to the itemManager and assign them to the current user
            String itemName1 = "User Item 1";
            String itemName2 = "User Item 2";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);

            Item newItem1 = new Item(itemName1, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            Item newItem2 = new Item(itemName2, itemDescription, startPrice, imageUrl, true, "Fashion", startPrice, endTime);
            itemManager.addItem(newItem1);
            itemManager.addItem(newItem2);
            currentUser.addBoughtItem(newItem1);
            currentUser.addBoughtItem(newItem2);

            List<Item> userAuctions = currentUser.getActiveAuctions();

            assertEquals(2, userAuctions.size());
            assertEquals(itemName1, userAuctions.get(0).getItemName());
            assertEquals(itemName2, userAuctions.get(1).getItemName());
        }
        @Test
        public void testUserStory11_1() {
            // Arrange
            User user = new User("testUser", "password");
            UserHomePage userHomePage = new UserHomePage(user.getUsername(), user.getPassword(), userController, itemManager, itemController);

            // Act
            String actualReport = userHomePage.getBuyerReportArea().getText();
            String expectedReport = "Generated Buyer Report ";

            // Assert
            assertEquals(expectedReport, actualReport, "The buyer report text does not match the expected value.");
        }

        @Test
        public void testUserStory12_2() {
            // Arrange
            User user = new User("testUser", "password");
            UserHomePage userHomePage = new UserHomePage(user.getUsername(), user.getPassword(), userController, itemManager, itemController);

            // Act
            String actualReport = userHomePage.getSellerReportArea().getText();
            String expectedReport = "Generated Seller Report ";

            // Assert
            assertEquals(expectedReport, actualReport, "The seller report text does not match the expected value.");
        }
    }