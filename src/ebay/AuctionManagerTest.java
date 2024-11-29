package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuctionManagerTest {
    private AuctionManager auctionManager;
    private Auction auction1;
    private Auction auction2;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        auctionManager = new AuctionManager();
        item1 = new Item("Vintage Camera", "A vintage camera from the 1950s", 250.00, "https://example.com/vintage-camera.jpg", true, "Electronics",250.);
        item2 = new Item("Antique Vase", "An antique vase from the 1800s", 150.00, "https://example.com/antique-vase.jpg", true, "Home Decor",150.0);
        auction1 = new Auction(item1);
        auction2 = new Auction(item2);
    }

    @Test
    void testAddAuction() {
        auctionManager.addAuction(auction1);
        List<Auction> activeAuctions = auctionManager.getActiveAuctions();
        assertTrue(activeAuctions.contains(auction1), "Auction 1 should be in the list of active auctions");
    }

    @Test
    void testGetActiveAuctions() {
        auctionManager.addAuction(auction1);
        auctionManager.addAuction(auction2);
        List<Auction> activeAuctions = auctionManager.getActiveAuctions();
        assertEquals(2, activeAuctions.size(), "There should be 2 active auctions");
        assertTrue(activeAuctions.contains(auction1), "Auction 1 should be in the list of active auctions");
        assertTrue(activeAuctions.contains(auction2), "Auction 2 should be in the list of active auctions");
    }

    @Test
    void testGetActiveAuctionsWhenNoneActive() {
        auction1.endAuction();
        auction2.endAuction();
        auctionManager.addAuction(auction1);
        auctionManager.addAuction(auction2);
        List<Auction> activeAuctions = auctionManager.getActiveAuctions();
        assertTrue(activeAuctions.isEmpty(), "There should be no active auctions");
    }
    /*@Test
    void testHandleAddItem() {
        auctionManager.handleAddItem(item1);
        List<Auction> activeAuctions = auctionManager.getActiveAuctions();
        assertEquals(1, activeAuctions.size(), "There should be 1 active auction after adding an item");
        assertTrue(activeAuctions.get(0).getItem().equals(item1), "The active auction should be for item1");
    } */
}