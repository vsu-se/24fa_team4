package ebay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemManager {

    public static ItemManager instance;

    // This will hold all the items for our ebay project/active auctions
    private final List<Item> items;
    private final List<Item> activeAuctions;

    // Private Constructor for singleton pattern
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

    public void startAuction(Item item, long endTime) {
        if (items.contains(item) && item.isAuction()) {
<<<<<<< Updated upstream
            Auction auction = new Auction(item);
            activeAuctions.add(auction);
            System.out.println("Auction started for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
            return auction;
=======
            item.setAuctionActive(true);
            item.setEndTime(endTime);
            activeAuctions.add(item);
            System.out.println("Auction started for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
>>>>>>> Stashed changes
        } else {
            System.out.println("Item is not available for auction or does not exist.");
        }
    }

    public void placeBid(Item item, Bid bid) {
        if (item.isAuction() && item.isAuctionActive() && bid.getBidAmount() > item.getStartPrice()) {
            item.getBids().add(bid);
            item.setStartPrice(bid.getBidAmount());
        }
    }

    public void buyItNow(Item item, User buyer) {
        if (items.contains(item) && !item.isAuction()) {
            items.remove(item);
            System.out.println(buyer.getUsername() + " has purchased the item: " + item.getItemName() + " (ID: " + item.getItemId() + ") for $" + item.getBuyItNowPrice());
        } else {
            System.out.println("Item is either unavailable or is being auctioned.");
        }
    }

    public List<Item> getActiveAuctions() {
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

    public List<Item> searchItems(String searchQuery) {
        List<Item> searchResults = new ArrayList<>();
        for (Item item : items) {
            if (item.getItemName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(item);
            }
        }
        return searchResults;
    }
    //populate a default list in order
    // to have active auctions when first logging in
    public void populateDefaultActiveAuctions() {
        // Create some default items for demonstration purposes
        Item item1 = new Item(
                "Vintage Watch",
                "A beautiful vintage watch in excellent condition.",
                100.00,
                "image_url_vintage_watch.jpg",
                true, // isAuction
                "Accessories",
                0
        );
        item1.setEndTime(System.currentTimeMillis() + 86400000); // 1 day from now
        addItem(item1);
        startAuction(item1);

<<<<<<< Updated upstream
=======
    // Populate a default list in order to have active auctions when first logging in
    public void populateDefaultActiveAuctions() {
        // Create some default items for demonstration purposes
        Item item1 = new Item(
                "Vintage Watch",
                "A beautiful vintage watch in excellent condition.",
                100.00,
                "image_url_vintage_watch.jpg",
                true, // isAuction
                "Accessories",
                0
        );
        item1.setEndTime(System.currentTimeMillis() + 86400000); // 1 day from now
        addItem(item1);
        startAuction(item1, item1.getEndTime());

>>>>>>> Stashed changes
        Item item2 = new Item(
                "Gaming Laptop",
                "High-performance gaming laptop with 16GB RAM and RTX 3070.",
                1200.00,
                "image_url_gaming_laptop.jpg",
                true, // isAuction
                "Electronics",
                0
        );
        item2.setEndTime(System.currentTimeMillis() + 172800000); // 2 days from now
        addItem(item2);
<<<<<<< Updated upstream
        startAuction(item2);
=======
        startAuction(item2, item2.getEndTime());
>>>>>>> Stashed changes

        Item item3 = new Item(
                "Artisan Coffee Table",
                "Handcrafted wooden coffee table with a modern design.",
                300.00,
                "image_url_coffee_table.jpg",
                true, // isAuction
                "Furniture",
                0
        );
        item3.setEndTime(System.currentTimeMillis() + 259200000); // 3 days from now
        addItem(item3);
<<<<<<< Updated upstream
        startAuction(item3);
=======
        startAuction(item3, item3.getEndTime());
>>>>>>> Stashed changes

        Item item4 = new Item(
                "2020 Electric Sedan",
                "Eco-friendly electric car with 250 miles of range.",
                20000.00,
                "image_url_electric_sedan.jpg",
                true, // isAuction
                "Vehicles",
                0
        );
        item4.setEndTime(System.currentTimeMillis() + 432000000); // 5 days from now
        addItem(item4);
<<<<<<< Updated upstream
        startAuction(item4);

        System.out.println("Default active auctions populated.");
    }

}
=======
        startAuction(item4, item4.getEndTime());

        System.out.println("Default active auctions populated.");
    }
}
>>>>>>> Stashed changes
