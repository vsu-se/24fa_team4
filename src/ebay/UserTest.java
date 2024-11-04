package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User users;
    private User sellerUser;
    private Item testItem;
    private Item buyNowItem;


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
                "Electronics");

        buyNowItem = new Item(
                "Retro Radio",
                "A retro-style radio with modern features.",
                150.00,
                "https://example.com/retro-radio.jpg",
                false, // This item is for "Buy It Now"
                "Electronics"
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
        assertFalse(users.isSeller());  // Use method instead of field access
    }

    @Test
    void testIsBidder() {
        assertTrue(users.isBidder());  // Use method instead of field access
    }

    @Test
    void testSetIsSeller() {
        // Initially not a seller
        assertFalse(users.isSeller());

        // Set user to be a seller
        users.setIsSeller(true);
        assertTrue(users.isSeller());
    }

    @Test
    void testSetIsBidder() {
        // Initially not a bidder
        assertTrue(users.isBidder());

        // Set user to be a bidder
        users.setIsBidder(false);
        assertFalse(users.isBidder());
    }

    @Test
    void testSellertoBidder(){
        // Initially a Seller
        assertTrue(sellerUser.isSeller());

        //set seller to be able to bid
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
        // Seller lists an auction item
        sellerUser.listItem(testItem);

        // Seller starts an auction for the item
        sellerUser.startAuction(testItem);

        // Check if the auction is successfully started
        assertEquals(1, ItemManager.getInstance().getActiveAuctions().size());
        assertTrue(ItemManager.getInstance().getActiveAuctions().get(0).isAuctionActive());
    }

    @Test
    void testBuyItNow() {
        // Seller lists the item for direct purchase
        sellerUser.listItem(buyNowItem);

        // Buyer purchases the item using "Buy It Now"
        users.buyItNow(buyNowItem);

        // Check if the item is successfully removed from ItemManager
        assertEquals(0, ItemManager.getInstance().getAllItems().size());
    }
}
