package Testing;
import ebay.*;
import GUI.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
import java.util.List;

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
        }

        @Test
        public void testUserStory1_condition1() {

            String[] expectedCategories = {"Electronics", "Fashion", "Home & Garden", "Sporting Goods", "Toys & Hobbies", "Other"};

            JList<String> categoryList = userHomePage.getCategoryList();
            ListModel<String> listModel = categoryList.getModel();

            assertEquals(expectedCategories.length, listModel.getSize());
            for (int i = 0; i < expectedCategories.length; i++) {
                assertEquals(expectedCategories[i], listModel.getElementAt(i));
            }
        }

        @Test
        public void testUserStory1_condition2() {

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

        public void testUserStory2_conditon1() {
            // Arrange
            User currentUser = userController.getCurrentUser();
            String itemName = "Test Item";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);
            itemManager.clearItems();

            Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            currentUser.listItem(newItem);
            itemManager.addItem(newItem);

            // Act
            List<Item> myAuctions = currentUser.getListedItems();

            // Assert
            assertEquals(1, myAuctions.size());
            assertEquals(itemName, myAuctions.get(0).getItemName());
        }


        @Test
        public void testUserStory2_2() {
            // Arrange
            User currentUser = userController.getCurrentUser();
            itemManager.clearItems();

            // Act
            List<Item> myAuctions = currentUser.getListedItems();

            // Assert
            assertEquals(1, myAuctions.size()); // This assertion is expected to fail
        }

        public void testShowMyAuctionsWithDifferentCategories() { // sh0w active auctions
            // Arrange
            User currentUser = userController.getCurrentUser();
            String itemName1 = "Test Item 1";
            String itemName2 = "Test Item 2";
            String itemDescription = "Test Description";
            double startPrice = 100.0;
            String imageUrl = "http://example.com/image.jpg";
            Instant endTime = Instant.now().plusSeconds(84000);
            itemManager.clearItems();

            Item newItem1 = new Item(itemName1, itemDescription, startPrice, imageUrl, true, "Electronics", startPrice, endTime);
            Item newItem2 = new Item(itemName2, itemDescription, startPrice, imageUrl, true, "Fashion", startPrice, endTime);
            currentUser.listItem(newItem1);
            currentUser.listItem(newItem2);
            itemManager.addItem(newItem1);
            itemManager.addItem(newItem2);

            // Act
            List<Item> myAuctions = currentUser.getListedItems();

            // Assert
            assertEquals(2, myAuctions.size());
            assertEquals(itemName1, myAuctions.get(0).getItemName());
            assertEquals(itemName2, myAuctions.get(1).getItemName());
        }

        @Test
        public void testUserStory5_condition1() {
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
        public void testUserStory6_condition1{
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
        }

}
