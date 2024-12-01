package GUI;

import ebay.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserHomePageControllerTest {

    private UserHomePageController controller;
    private User sellerUser;
    private User buyerUser;
    private UserHomePage userHomePage;
    private ItemManager itemManager;

    @BeforeEach
    public void setUp() {
        sellerUser = new User("sellerUser", "password");
        buyerUser = new User("buyerUser", "password");
        userHomePage = new UserHomePage();
        itemManager = ItemManager.getInstance();
        itemManager.clearItems(); // Clear items to ensure a clean state for each test
        controller = new UserHomePageController(sellerUser, buyerUser, userHomePage, itemManager);
    }

    @Test
    public void testHandleSearch() {
        String searchQuery = "Vintage Camera";
        Item item = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", false, "Electronics", 100.0);
        itemManager.addItem(item);

        controller.handleSearch(searchQuery);

        // Assuming userHomePage.showSearchResults updates a list of search results
        List<Item> searchResults = userHomePage.searchResults;
        assertEquals(1, searchResults.size());
        assertEquals("Vintage Camera", searchResults.get(0).getItemName());
    }

    @Test
    public void testHandleBid() {
        Item selectedItem = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", true, "Electronics", 100.0);
        itemManager.addItem(selectedItem);
        userHomePage.setSelectedItem(selectedItem);

        controller.handleBid(50.0);

        String errorMessage = userHomePage.errorMessage;
        assertEquals("Failed to place bid. Please try again.", errorMessage);
    }

    @Test
    public void testHandleBidNoItemSelected() {
        userHomePage.setSelectedItem(null);

        controller.handleBid(150.0);

        String errorMessage = userHomePage.errorMessage;
        assertEquals("No item selected. Please select an item to bid on.", errorMessage);
    }

    @Test
    public void testHandleBidFailed() {
        Item selectedItem = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", true, "Electronics", 100.0);
        itemManager.addItem(selectedItem);
        userHomePage.setSelectedItem(selectedItem);

        controller.handleBid(50.0); // Bid lower than the starting price

        String errorMessage = userHomePage.errorMessage;
        assertEquals("Failed to place bid. Please try again.", errorMessage);
    }

    @Test
    public void testHandleAddItem() {
        String itemName = "Vintage Camera";
        String itemDescription = "A classic vintage camera.";
        double startPrice = 100.0;
        String imageUrl = "http://example.com/camera.jpg";

        userHomePage.txtItemName.setText(itemName);
        userHomePage.txtItemDescription.setText(itemDescription);
        userHomePage.txtStartPrice.setText(String.valueOf(startPrice));
        userHomePage.txtImageUrl.setText(imageUrl);
        userHomePage.isAuctionCheckBox.setSelected(true);
        userHomePage.itemTypeComboBox.setSelectedItem("Electronics");

        controller.handleAddItem(itemName, itemDescription, startPrice, imageUrl);

        List<Item> items = itemManager.getAllItems();
        assertEquals(1, items.size());
        assertEquals("Vintage Camera", items.get(0).getItemName());
        assertEquals("A classic vintage camera.", items.get(0).getDescription());
        assertEquals(100.0, items.get(0).getStartPrice());
        assertEquals("http://example.com/camera.jpg", items.get(0).getImageUrl());
        assertTrue(items.get(0).isAuction());
        assertEquals("Electronics", items.get(0).getItemType());
    }

    @Test
    public void testStartAuction() {
        Item item = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", true, "Electronics", 100.0);
        itemManager.addItem(item);
        long endTime = System.currentTimeMillis() + 10000;

        controller.startAuction(item, endTime);

        assertTrue(item.isAuction());
        assertEquals(endTime, item.getEndTime());
        List<Auction> activeAuctions = itemManager.getActiveAuctions();
        assertEquals(1, activeAuctions.size());
        assertEquals(item, activeAuctions.get(0).getItem());
    }

    @Test
    public void testShowConcludedAuctions() {
        Item item = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", true, "Electronics", 100.0);
        item.setEndTime(System.currentTimeMillis() - 10000); // Set end time in the past
        itemManager.addItem(item);

        controller.showConcludedAuctions();

        List<Item> concludedAuctions = userHomePage.getConcludedAuctions();
        assertEquals(1, concludedAuctions.size());
        assertEquals("Vintage Camera", concludedAuctions.get(0).getItemName());
    }
}