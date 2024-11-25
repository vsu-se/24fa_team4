package ebay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemManager {

    private static ItemManager instance;

    //This will hold all the items for our ebay project/ active auctions
    private final List<Item> items;
    private final List<Auction> activeAuctions;

    //Private Constructor for singleton pattern
    private ItemManager() {
        this.items = new ArrayList<>();
        this.activeAuctions = new ArrayList<>();
    }

    // Static method to provide a single instance of ItemManager
    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }


    // Add item to the list
    public void addItem(Item item) {
        items.add(item);
    }

    public synchronized void removeItem(String itemName) {
        items.removeIf(item -> item.getItemName().equals(itemName));
    }


    public List<Item> getAllItems() {
        return items;
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(UUID itemId) {
        items.removeIf(item -> item.getItemId().equals(itemId));
        System.out.println("Item removed with ID: " + itemId);
    }

    public Item getItemById(UUID itemId) {
        for (Item item : items) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null;  // Item not found
    }

    public Auction startAuction(Item item) {
        if (items.contains(item) && item.isAuction()) {
            Auction auction = new Auction(item);
            activeAuctions.add(auction);
            System.out.println("Auction started for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
            return auction;
        } else {
            System.out.println("Item is not available for auction or does not exist.");
        }
        return null;
    }

    public void addAuction(Auction auction) {
        activeAuctions.add(auction);
        System.out.println("Auction added for item: " + auction.getItem().getItemName());
    }

    public void buyItNow(Item item, User buyer) {
        if (items.contains(item) && !item.isAuction()) {
            items.remove(item);
            System.out.println(buyer.getUsername() + " has purchased the item: " + item.getItemName() + " (ID: " + item.getItemId() + ") for $" + item.getBuyItNowPrice());
        } else {
            System.out.println("Item is either unavailable or is being auctioned.");
        }
    }

    public List<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public void clearItems() {
        items.clear();
        activeAuctions.clear();
    }

    // New method to get an item by UUID
    public Item getItemByUUID(UUID itemID) {
        for (Item item : items) {
            if (item.getItemId().equals(itemID)) {
                return item;
            }
        }
        return null;
    }




    public List<Item> getConcludedAuctions() {
        List<Item> concludedAuctions = new ArrayList<>();
        for (Item item : items) {
            if (item.isAuction() && item.getEndTime() < System.currentTimeMillis()) {
                concludedAuctions.add(item);
            }
        }
        return concludedAuctions;
    }
}
