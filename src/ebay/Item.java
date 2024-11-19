package ebay;

import java.util.UUID;

public class Item {
    private String itemName;
    private String description;
    private double buyItNowPrice;
    private String imageUrl;
    private boolean isAuction;
    private String itemType;
    private final UUID itemId;
    private Bid highestBid;
    private String timeRemaining;

    // Constructor
    public Item(String itemName, String description, double buyItNowPrice, String imageUrl, boolean isAuction, String itemType) {
        this.itemName = itemName;
        this.description = description;
        this.buyItNowPrice = buyItNowPrice;
        this.imageUrl = imageUrl;
        this.isAuction = isAuction;
        this.itemType = itemType;
        this.itemId = UUID.randomUUID();

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

    public void placeBid(Bid bid) {this.highestBid = bid;}


    // Setters for fields the seller can update
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBuyItNowPrice(double buyItNowPrice) {
        this.buyItNowPrice = buyItNowPrice;
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

    public UUID getItemId() {
        return itemId;
    }
}


