package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User users;
    private User sellerUser;
    private Item testItem;
    private Item buyNowItem;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Clear ItemManager before each test to avoid side effects
        ItemManager.getInstance().clearItems();

        users = new User("testUser", "testPassword", false, true);
        sellerUser = new User("sellerUser", "password", true, false);
        testItem = new Item(
                "Vintage Camera",
                "A vintage camera from the 1950s in excellent condition.",
                250.00,
                "https://example.com/vintage-camera.jpg",
                true, // This is an auction item
                "Electronics",
                250.0);

        buyNowItem = new Item(
                "Retro Radio",
                "A retro-style radio with modern features.",
                150.00,
                "https://example.com/retro-radio.jpg",
                false, // This item is for "Buy It Now"
                "Electronics",
                150.0
        );
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", users.getUsername());
    }

    @Test
    void testSetUsername() {
        users.setUsername("newUser");
        assertEquals("newUser", users.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("testPassword", users.getPassword());
    }

    @Test
    void testSetPassword() {
        users.setPassword("newPassword");
        assertEquals("newPassword", users.getPassword());
    }

    @Test
    void testIsSeller() {
        assertFalse(users.isSeller());
    }

    @Test
    void testIsBidder() {
        assertTrue(users.isBidder());
    }

    @Test
    void testSetIsSeller() {
        assertFalse(users.isSeller());
        users.setIsSeller(true);
        assertTrue(users.isSeller());
    }

    @Test
    void testSetIsBidder() {
        assertTrue(users.isBidder());
        users.setIsBidder(false);
        assertFalse(users.isBidder());
    }

    @Test
    void testSellertoBidder() {
        assertTrue(sellerUser.isSeller());
        sellerUser.setIsBidder(true);
        assertTrue(sellerUser.isBidder());
    }

    @Test
    void testSellerListItem() {
        sellerUser.listItem(testItem);
        assertEquals(1, ItemManager.getInstance().getAllItems().size());
        assertEquals("Vintage Camera", ItemManager.getInstance().getAllItems().get(0).getItemName());
        assertEquals("A vintage camera from the 1950s in excellent condition.", ItemManager.getInstance().getAllItems().get(0).getDescription());
    }

    @Test
    void testStartAuction() {
        System.setOut(new PrintStream(outContent));
        sellerUser.listItem(testItem);
        sellerUser.startAuction(testItem);
        assertEquals(1, ItemManager.getInstance().getActiveAuctions().size());
        assertTrue(ItemManager.getInstance().getActiveAuctions().get(0).isAuctionActive());

        String expectedOutput = "sellerUser has listed an item: Vintage Camera\n" +
                "sellerUser has started an auction for item: Vintage Camera (ID: " + testItem.getItemId() + ")";
        System.setOut(originalOut);
        String actualOutput = outContent.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testBuyItNow() {
        sellerUser.listItem(buyNowItem);
        users.buyItNow(buyNowItem);
        assertEquals(0, ItemManager.getInstance().getAllItems().size());
    }

    @Test
    void testBuyItNowWithUser() {
        sellerUser.listItem(buyNowItem);
        users.buyItNow(buyNowItem);
        assertEquals(0, ItemManager.getInstance().getAllItems().size());
    }
}