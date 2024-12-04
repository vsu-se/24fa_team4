package ebay;

import java.util.List;
import java.util.UUID;

public class ItemController {
    private ItemManager itemManager;

    public ItemController() {
        this.itemManager = ItemManager.getInstance();
    }

    // Add item
    public void addItem(String itemName, String description, double startPrice, String imageUrl, boolean isAuction, String itemType, double buyItNowPrice) {
        Item newItem = new Item(itemName, description, startPrice, imageUrl, isAuction, itemType, buyItNowPrice);
        itemManager.addItem(newItem);
    }

    // Remove item by name
    public void removeItemByName(String itemName) {
        itemManager.removeItem(itemName);
    }

    // Remove item by UUID
    public void removeItemById(UUID itemId) {
        itemManager.removeItem(itemId);
    }

    // Get all items
    public List<Item> getAllItems() {
        return itemManager.getAllItems();
    }

    // Get item by name
    public Item getItemByName(String itemName) {
        return itemManager.getItemByName(itemName);
    }

    // Get item by UUID
    public Item getItemByUUID(UUID itemId) {
        return itemManager.getItemByUUID(itemId);
    }

    // Start auction
    public void startAuction(String itemName, long endTime) {
        Item item = itemManager.getItemByName(itemName);
        if (item != null) {
            itemManager.startAuction(item, endTime);
        }
    }

    // Place bid
    public void placeBid(String itemName, Bid bid) {
        Item item = itemManager.getItemByName(itemName);
        if (item != null) {
            itemManager.placeBid(item, bid);
        }
    }

    // Buy it now
    public void buyItNow(String itemName, User buyer) {
        Item item = itemManager.getItemByName(itemName);
        if (item != null) {
            itemManager.buyItNow(item, buyer);
        }
    }

    // Get active auctions
    public List<Item> getActiveAuctions() {
        return itemManager.getActiveAuctions();
    }

    // Get concluded auctions
    public List<Item> getConcludedAuctions() {
        return itemManager.getConcludedAuctions();
    }

    // Search items
    public List<Item> searchItems(String searchQuery) {
        return itemManager.searchItems(searchQuery);
    }

    // Clear all items
    public void clearItems() {
        itemManager.clearItems();
    }

    // Populate default active auctions
    public void populateDefaultActiveAuctions() {
        itemManager.populateDefaultActiveAuctions();
    }
}