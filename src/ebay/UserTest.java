package ebay;

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
    private Item soldItem1;
    private Item soldItem2;
    private Item boughtItem1;
    private Item boughtItem2;

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
        // Check the output
        assertEquals("sellerUser has started an auction for item: Vintage Camera (ID: 1)", outContent.toString().trim());
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
    @Test
    void testBuyItNowWithUser() {
        // Seller lists the item for direct purchase
        sellerUser.listItem(buyNowItem);

        // Buyer purchases the item using "Buy It Now"
        users.buyItNow(buyNowItem, sellerUser);

        // Check if the item is successfully removed from ItemManager
        assertEquals(0, ItemManager.getInstance().getAllItems().size());
    }
    @Test
    void testShowSellerReport() {
        sellerUser.addSoldItem(soldItem1);
        sellerUser.addSoldItem(soldItem2);

        sellerUser.showSellerReport();

        String expectedOutput = "Seller's Report for sellerUser\n" +
                "--------------------------------------------------\n" +
                "Item Name | Price | Seller's Commission | Shipping\n" +
                "--------------------------------------------------\n" +
                "Vintage Camera | 250.00 | 50.00 | 10.00\n" +
                "Antique Vase | 150.00 | 30.00 | 10.00\n" +
                "--------------------------------------------------\n" +
                "Total Winning Bids: 400.00\n" +
                "Total Shipping Costs: 20.00\n" +
                "Total Seller's Commissions: 80.00\n" +
                "Total Profits: 300.00\n";

        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void testShowBuyerReport() {
        // Initialize bought items
        boughtItem1 = new Item(
                "Retro Radio",
                "A retro-style radio with modern features.",
                100.00,
                "https://example.com/retro-radio.jpg",
                false,
                "Electronics"
        );

        boughtItem2 = new Item(
                "Modern Lamp",
                "A modern lamp with a sleek design.",
                75.00,
                "https://example.com/modern-lamp.jpg",
                false,
                "Home Decor"
        );

        // Add bought items to the user
        users.addBoughtItem(boughtItem1);
        users.addBoughtItem(boughtItem2);

        // Capture the output
        System.setOut(new PrintStream(outContent));

        // Show buyer report
        users.showBuyerReport();

        // Expected output
        String expectedOutput = "Buyer's Report for testUser\n" +
                "--------------------------------------------------\n" +
                "Item Name | Price | Shipping\n" +
                "Retro Radio | 100.00 | 10.00\n" +
                "Modern Lamp | 75.00 | 10.00\n" +
                "--------------------------------------------------\n" +
                "Total Spent: 175.00\n" +
                "Total Shipping Costs: 20.00\n";

        // Assert the output
        assertEquals(expectedOutput, outContent.toString().trim());

        // Reset the output stream
        System.setOut(originalOut);
    }

}
