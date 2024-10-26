package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AuctionTest {
    private Item item;
    private Auction auction;
    private User bidder1;
    private User bidder2;

    @BeforeEach
    public void setUp() {
        item = new Item("Vintage Camera", "A vintage camera from the 1950s", 250.00, "https://example.com/vintage-camera.jpg", true, "Electronics");
        auction = new Auction(item);
        bidder1 = new User("john_doe", "password123", false, true);
        bidder2 = new User("jane_doe", "password456", false, true);
    }

    @Test
    public void testAuctionStartsSuccessfully() {
        assertTrue(auction.isAuctionActive(), "Auction should be active after starting");
    }

    @Test
    public void testPlaceBidSuccessfully() {
        auction.placeBid(bidder1, 300.00);
        assertNotNull(auction.getHighestBid(), "There should be a highest bid after placing a bid");
        assertEquals(300.00, auction.getHighestBid().getBidAmount(), 0.01, "Highest bid amount should be 300.00");
        assertEquals(bidder1, auction.getHighestBid().getBidder(), "Highest bidder should be john_doe");
    }

    @Test
    public void testPlaceLowerBid() {
        auction.placeBid(bidder1, 300.00);
        auction.placeBid(bidder2, 200.00);
        assertEquals(300.00, auction.getHighestBid().getBidAmount(), 0.01, "Highest bid should still be 300.00 as the second bid was lower");
        assertEquals(bidder1, auction.getHighestBid().getBidder(), "Highest bidder should still be john_doe");
    }

    @Test
    public void testPlaceHigherBid() {
        auction.placeBid(bidder1, 300.00);
        auction.placeBid(bidder2, 350.00);
        assertEquals(350.00, auction.getHighestBid().getBidAmount(), 0.01, "Highest bid should be updated to 350.00");
        assertEquals(bidder2, auction.getHighestBid().getBidder(), "Highest bidder should be jane_doe");
    }

    @Test
    public void testEndAuctionWithoutBids() {
        auction.endAuction();
        assertFalse(auction.isAuctionActive(), "Auction should be inactive after ending");
        assertNull(auction.getHighestBid(), "There should be no highest bid if no bids were placed");
    }

    @Test
    public void testEndAuctionWithBids() {
        auction.placeBid(bidder1, 300.00);
        auction.endAuction();
        assertFalse(auction.isAuctionActive(), "Auction should be inactive after ending");
        assertNotNull(auction.getHighestBid(), "There should be a highest bid after ending the auction with bids");
        assertEquals(300.00, auction.getHighestBid().getBidAmount(), 0.01, "Winning bid should be 300.00");
        assertEquals(bidder1, auction.getHighestBid().getBidder(), "Winning bidder should be john_doe");
    }

}

