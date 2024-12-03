package ebay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item {
    private String itemName;
    private String description;
    private double buyItNowPrice;
    private double startPrice;
    private String imageUrl;
    private boolean isAuction;
    private String itemType;
    private final UUID itemId;
    private Bid highestBid;
    private String timeRemaining;
    private long endTime;
    private List<Bid> bids;

    // Constructor
    public Item(String itemName, String description, double buyItNowPrice, String imageUrl, boolean isAuction, String itemType, double startPrice) {
        this.itemName = itemName;
        this.description = description;
        this.buyItNowPrice = buyItNowPrice;
        this.startPrice = startPrice;
        this.imageUrl = imageUrl;
        this.isAuction = isAuction;
        this.itemType = itemType;
        this.itemId = UUID.randomUUID();
        this.bids = new ArrayList<>();
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getBuyItNowPrice() {
        return buyItNowPrice;
    }

    public double getStartPrice() {
        return startPrice; }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isAuction() {
        return isAuction;
    }

    public String getItemType() {
        return itemType;
    }

    public Bid getHighestBid() {return highestBid;}

    public String getTimeRemaining() {return timeRemaining;}

    public void placeBid(Bid bid) {
        bids.add(bid);
    }

    public List<Bid> getBids() {return bids;}


    // Setters for fields the seller can update
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBuyItNowPrice(double buyItNowPrice) {this.buyItNowPrice = buyItNowPrice; }

    public void setStartPrice(double startPrice) { this.startPrice = startPrice;}

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAuction(boolean isAuction) {
        this.isAuction = isAuction;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public UUID getItemId() {
        return itemId;
    }

    public long getEndTime() {
   return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }



}


