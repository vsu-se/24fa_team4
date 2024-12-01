package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

class ItemManagerTest {

    private ItemManager itemManager;
    private Item item1;
    private Item item2;
    private Bidder bidder1;
    private Bidder bidder2;

    @BeforeEach
    void setUp() {
        // Clear the ItemManager before each test
        itemManager = ItemManager.getInstance();
        itemManager.clearItems();

        // Create some items for testing
        item1 = new Item(
                "Vintage Camera",
                "A vintage camera from the 1950s in excellent condition.",
                250.00,
                "https://example.com/vintage-camera.jpg",
                true, // Auction item
                "Electronics",
                250.00
        );

        item2 = new Item(
                "Retro Radio",
                "A retro-style radio with modern features.",
                150.00,
                "https://example.com/retro-radio.jpg",
                false, // Buy It Now item
                "Electronics",
                150.00
        );
    }

    @Test
    void testAddItem() {
        // Add an item to the ItemManager
        itemManager.addItem(item1);
        assertEquals(1, itemManager.getAllItems().size());
        assertEquals("Vintage Camera", itemManager.getAllItems().get(0).getItemName());
    }

    @Test
    void testRemoveItem() {
        // Add an item and then remove it
        itemManager.addItem(item1);
        assertEquals(1, itemManager.getAllItems().size());

        itemManager.removeItem(item1.getItemName());
        assertEquals(0, itemManager.getAllItems().size());
    }

    @Test
    void testGetAllItems() {
        // Add multiple items and verify they are all present
        itemManager.addItem(item1);
        itemManager.addItem(item2);

        assertEquals(2, itemManager.getAllItems().size());
        assertEquals("Vintage Camera", itemManager.getAllItems().get(0).getItemName());
        assertEquals("Retro Radio", itemManager.getAllItems().get(1).getItemName());
    }

    @Test
    void testGetItemByName() {
        // Add items and check if we can get them by name
        itemManager.addItem(item1);
        itemManager.addItem(item2);

        Item foundItem = itemManager.getItemByName("Vintage Camera");
        assertNotNull(foundItem);
        assertEquals("Vintage Camera", foundItem.getItemName());

        Item notFoundItem = itemManager.getItemByName("Non-existent Item");
        assertNull(notFoundItem);
    }

    @Test
    void testAddAuction() {
        // Start an auction for item1
        itemManager.addItem(item1);
        Auction auction = new Auction(item1);
        itemManager.startAuction(item1);

        assertEquals(1, itemManager.getActiveAuctions().size());
        assertEquals("Vintage Camera", itemManager.getActiveAuctions().get(0).getItem().getItemName());
    }

    @Test
    void testClearItems() {
        // Add items and then clear them
        itemManager.addItem(item1);
        itemManager.addItem(item2);
        assertEquals(2, itemManager.getAllItems().size());

        itemManager.clearItems();
        assertEquals(0, itemManager.getAllItems().size());
        assertEquals(0, itemManager.getActiveAuctions().size());
    }

    @Test
    void testGetItemByUUID() {
        // Add items and check if we can get them by UUID
        itemManager.addItem(item1);
        itemManager.addItem(item2);

        UUID item1UUID = item1.getItemId();
        Item foundItem = itemManager.getItemByUUID(item1UUID);
        assertNotNull(foundItem);
        assertEquals("Vintage Camera", foundItem.getItemName());

        UUID randomUUID = UUID.randomUUID();
        Item notFoundItem = itemManager.getItemByUUID(randomUUID);
        assertNull(notFoundItem);
    }


}

