package model;

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
    private long endTime;
    private boolean auctionActive;
    private List<Bid> bids;

    public Item(String itemName, String description, double startPrice, String imageUrl, boolean isAuction, String itemType, double buyItNowPrice) {
        this.itemId = UUID.randomUUID();
        this.itemName = itemName;
        this.description = description;
        this.startPrice = startPrice;
        this.imageUrl = imageUrl;
        this.isAuction = isAuction;
        this.itemType = itemType;
        this.buyItNowPrice = buyItNowPrice;
        this.auctionActive = false;
        this.bids = new ArrayList<>();
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

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
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



    public void startAuction() {
        if (isAuction) {
            this.auctionActive = true;
            this.endTime = System.currentTimeMillis() + 86400000; // Default to 1 day
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

    public void setStartPrice(double bidAmount) {
        this.startPrice = bidAmount;
    }

    public String getCategory() {
        return itemType;
    }

    public String toString() {
        return itemName +", Starting: $" + startPrice;
    }
}