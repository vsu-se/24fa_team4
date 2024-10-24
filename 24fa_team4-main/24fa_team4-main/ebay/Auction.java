package ebay;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Auction {
    private Item item;
    private Bid highestBid;
    private boolean isAuctionActive;

    public Auction(Item item) {
        this.item = item;
        this.isAuctionActive = true;
        startAuctionTimer();
    }
    // Getter for the item being auctioned
    public Item getItem() {
        return item;
    }

    // Method to place a bid
    public void placeBid(Bidder bidder, double bidAmount) {
        if (!isAuctionActive) {
            System.out.println("Auction for item " + item.getItemName() + " has ended. No more bids can be placed.");
            return;
        }

        if (highestBid == null || bidAmount > highestBid.getBidAmount()) {
            highestBid = new Bid((User) bidder, bidAmount);
            System.out.println("New highest bid by " + ((User) bidder).getUsername() + ": $" + bidAmount);
        } else {
            System.out.println("Bid must be higher than the current highest bid.");
        }
    }

    // Method to start the auction timer
    private void startAuctionTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the end of the auction after 2 minutes
        scheduler.schedule(() -> {
            endAuction();
            scheduler.shutdown();
        }, 2, TimeUnit.MINUTES);
    }

    // Method to end the auction
    private void endAuction() {
        isAuctionActive = false;
        if (highestBid != null) {
            System.out.println("Auction ended for item: " + item.getItemName());
            System.out.println("Winning bid: $" + highestBid.getBidAmount() + " by " + ((User) highestBid.getBidder()).getUsername());
        } else {
            System.out.println("Auction ended for item: " + item.getItemName() + " with no bids.");
        }
    }

    // Getter for the highest bid
    public Bid getHighestBid() {
        return highestBid;
    }

    // Method to check if the auction is still active
    public boolean isAuctionActive() {
        return isAuctionActive;
    }
}



