package ebay;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item {
    private UUID itemId;
    private String itemName;
    private String description;
    private double startPrice;
    private String imageUrl;
    private boolean isAuction;
    private String itemType;
    private double buyItNowPrice;
    private Instant endTime;
    private boolean auctionActive;
    private List<Bid> bids;

    public Item(String itemName, String description, double startPrice, String imageUrl, boolean isAuction, String itemType, double buyItNowPrice, Instant endTime) {
        this.itemId = UUID.randomUUID();
        this.itemName = itemName;
        this.description = description;
        this.startPrice = startPrice;
        this.imageUrl = imageUrl;
        this.isAuction = isAuction;
        this.itemType = itemType;
        this.buyItNowPrice = buyItNowPrice;
        this.auctionActive = true;
        this.bids = new ArrayList<>();
        this.endTime = endTime;
    }

    // Getters and setters...
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAuction(boolean isAuction) {
        this.isAuction = isAuction;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setBuyItNowPrice(double buyItNowPrice) {
        this.buyItNowPrice = buyItNowPrice;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isAuction() {
        return isAuction;
    }

    public String getItemType() {
        return itemType;
    }

    public double getBuyItNowPrice() {
        return buyItNowPrice;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public boolean isAuctionActive() {
        return auctionActive;
    }

    public void setAuctionActive(boolean auctionActive) {
        this.auctionActive = auctionActive;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public boolean addBid(Bid bid) {
        return bids.add(bid);
    }

    public void startAuction(Clock clock) {
        if (isAuction) {
            this.auctionActive = true;
            this.endTime = clock.instant().plusSeconds(86400); // Default to 1 day
        }
    }

    public boolean placeBid(Bid bid) {
        if (isAuction && auctionActive && bid.getBidAmount() > startPrice) {
            this.bids.add(bid);
            this.startPrice = bid.getBidAmount();
            return true;
        }
        return false;
    }

    public void buyItNow(User buyer) {
        if (!isAuction) {
            this.auctionActive = false;
            System.out.println(buyer.getUsername() + " has purchased the item: " + itemName + " (ID: " + itemId + ") for $" + buyItNowPrice);
        }
    }

    public double getHighestBid() {
        if (bids.isEmpty()) {
            return startPrice; // If no bids, return the starting price
        }
        double highestBid = startPrice;
        for (Bid bid : bids) {
            if (bid.getBidAmount() > highestBid) {
                highestBid = bid.getBidAmount();
            }
        }
        return highestBid;
    }

    public void setStartPrice(double bidAmount) {
        this.startPrice = bidAmount;
    }

    public String getCategory() {
        return itemType;
    }

    public String toString() {
        return itemName + ", Starting: $" + startPrice;
    }

    public double getBidAmount(double bidAmount) {
        return bidAmount;
    }

    public double getCurrentbid() {
        return bids.isEmpty() ? startPrice : getHighestBid();
    }

    public void setItemId(UUID id) {
        itemId = id;
    }
}