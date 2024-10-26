package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item("Vintage Camera", "A vintage camera from the 1950s", 250.00, "https://example.com/vintage-camera.jpg", true, "Electronics");
    }

    @Test
    public void testGetItemName() {
        assertEquals("Vintage Camera", item.getItemName(), "Item name should be 'Vintage Camera'");
    }

    @Test
    public void testGetDescription() {
        assertEquals("A vintage camera from the 1950s", item.getDescription(), "Item description should be 'A vintage camera from the 1950s'");
    }

    @Test
    public void testGetBuyItNowPrice() {
        assertEquals(250.00, item.getBuyItNowPrice(), 0.01, "Item price should be 250.00");
    }

    @Test
    public void testGetImageUrl() {
        assertEquals("https://example.com/vintage-camera.jpg", item.getImageUrl(), "Item image URL should be 'https://example.com/vintage-camera.jpg'");
    }

    @Test
    public void testIsAuction() {
        assertTrue(item.isAuction(), "Item should be marked as available for auction");
    }

    @Test
    public void testGetItemType() {
        assertEquals("Electronics", item.getItemType(), "Item type should be 'Electronics'");
    }

    @Test
    public void testSetItemName() {
        item.setItemName("Retro Camera");
        assertEquals("Retro Camera", item.getItemName(), "Item name should be updated to 'Retro Camera'");
    }

    @Test
    public void testSetDescription() {
        item.setDescription("A retro-style camera from the 1960s");
        assertEquals("A retro-style camera from the 1960s", item.getDescription(), "Item description should be updated to 'A retro-style camera from the 1960s'");
    }

    @Test
    public void testSetBuyItNowPrice() {
        item.setBuyItNowPrice(300.00);
        assertEquals(300.00, item.getBuyItNowPrice(), 0.01, "Item price should be updated to 300.00");
    }

    @Test
    public void testSetImageUrl() {
        item.setImageUrl("https://example.com/retro-camera.jpg");
        assertEquals("https://example.com/retro-camera.jpg", item.getImageUrl(), "Item image URL should be updated to 'https://example.com/retro-camera.jpg'");
    }

    @Test
    public void testSetAuction() {
        item.setAuction(false);
        assertFalse(item.isAuction(), "Item should be marked as not available for auction");
    }

    @Test
    public void testSetItemType() {
        item.setItemType("Photography");
        assertEquals("Photography", item.getItemType(), "Item type should be updated to 'Photography'");
    }
}

