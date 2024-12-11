package ebay;

import java.io.*;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemManager {

    public static ItemManager instance;

    private final List<Item> items; // All items
    private final List<Item> activeAuctions; // Active auctions
    private final List<String> categoryTypes; // Item categories
    private static final String ITEM_DATA_FILE = "src/ebay/datafiles/auctions_data.txt";

    public ItemManager() {
        this.items = new ArrayList<>();
        this.activeAuctions = new ArrayList<>();
        this.categoryTypes = new ArrayList<>();
        loadItems(); // Load items from file on initialization
    }

    // Static method to provide a single instance of ItemManager
    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    public void addItem(Item item) {
        items.add(item);
        if (item.isAuction()) {
            activeAuctions.add(item);
        }
        // Ensure category type is in the list
        if (!categoryTypes.contains(item.getItemType())) {
            categoryTypes.add(item.getItemType());
        }
        saveItems(); // Save state after adding an item
    }

    public List<String> getTypes() {
        return categoryTypes;
    }

    public synchronized void removeItem(String itemName) {
        items.removeIf(item -> item.getItemName().equals(itemName));
        activeAuctions.removeIf(item -> item.getItemName().equals(itemName));
        saveItems(); // Save state after removing an item
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
        activeAuctions.removeIf(item -> item.getItemId().equals(itemId));
        saveItems(); // Save state after removing an item by ID
        System.out.println("Item removed with ID: " + itemId);
    }

    public Item getItemById(UUID itemId) {
        for (Item item : items) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public void startAuction(Item item, Instant endTime) {
        if (items.contains(item) && item.isAuction()) {
            item.setAuctionActive(true);
            item.setEndTime(endTime);
            activeAuctions.add(item);
            saveItems(); // Save state after starting an auction
            System.out.println("Auction started for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
        } else {
            System.out.println("Item is not available for auction or does not exist.");
        }
    }

    public boolean placeBid(Item item, Bid bid) {
        boolean result = item.addBid(bid);
        if (result) {
            saveItems(); // Save state after placing a bid
        }
        return result;
    }

    public void buyItNow(Item item, User buyer) {
        if (items.contains(item) && !item.isAuction()) {
            items.remove(item);
            saveItems(); // Save state after buying an item
            System.out.println(buyer.getUsername() + " has purchased the item: " + item.getItemName() + " (ID: " + item.getItemId() + ") for $" + item.getBuyItNowPrice());
        } else {
            System.out.println("Item is either unavailable or is being auctioned.");
        }
    }

    public List<Item> getActiveAuctions() {
        return activeAuctions;
    }

    public void clearItems() {
        getAllItems().clear();
        activeAuctions.clear();
        saveItems(); // Save state after clearing items
    }

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
            if (item.isAuction() && item.getEndTime().isBefore(Instant.now())) {
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

    public void populateDefaultActiveAuctions() {
        addDefaultItemIfNotExists("Vintage Watch", "A beautiful vintage watch in excellent condition.", 100.0, "image_url_vintage_watch.jpg", true, "Accessories", 0.0, Instant.now().plusSeconds(50000));
        addDefaultItemIfNotExists("Gaming Laptop", "High-performance gaming laptop with 16GB RAM and RTX 3070.", 1200.0, "image_url_gaming_laptop.jpg", true, "Electronics", 0.0, Instant.now().plusSeconds(60000));
        addDefaultItemIfNotExists("Artisan Coffee Table", "Handcrafted wooden coffee table with a modern design.", 300.0, "image_url_coffee_table.jpg", true, "Furniture", 0.0, Instant.now().plusSeconds(80000));
        addDefaultItemIfNotExists("2020 Electric Sedan", "Eco-friendly electric car with 250 miles of range.", 20000.0, "image_url_electric_sedan.jpg", true, "Vehicles", 0.0, Instant.now().plusSeconds(100000));
    }

    // Helper method to add an item if it does not already exist
    private void addDefaultItemIfNotExists(String itemName, String description, double startPrice, String imageUrl, boolean isAuction, String itemType, double buyItNowPrice, Instant endTime) {
        if (getItemByName(itemName) == null) {
            Item newItem = new Item(itemName, description, startPrice, imageUrl, isAuction, itemType, buyItNowPrice, endTime);
            addItem(newItem);
            startAuction(newItem, endTime); // Default auction end time: 1 day
        }
    }

    public List<Item> getItems() {
        return items;
    }

    // Save items to a text file
    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEM_DATA_FILE))) {
            for (Item item : items) {
                writer.write(serializeItem(item));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }

    // Load items from a text file
    private void loadItems() {
        File file = new File(ITEM_DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing item data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Item item = deserializeItem(line);
                items.add(item);
                if (item.isAuction() && item.isAuctionActive()) {
                    activeAuctions.add(item);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
    }

    // Serialize an Item object to a string
    private String serializeItem(Item item) {
        return item.getItemId() + "," +
                item.getItemName() + "," +
                item.getDescription() + "," +
                item.getStartPrice() + "," +
                item.getImageUrl() + "," +
                item.isAuction() + "," +
                item.getItemType() + "," +
                item.getBuyItNowPrice() + "," +
                (item.getEndTime() != null ? item.getEndTime().toEpochMilli() : "null") + "," +
                item.isAuctionActive();
    }

    // Deserialize a string to an Item object
    private Item deserializeItem(String data) {
        String[] parts = data.split(",");
        if (parts.length != 10) {
            throw new IllegalArgumentException("Invalid item data format.");
        }

        UUID itemId = UUID.fromString(parts[0]);
        String itemName = parts[1];
        String description = parts[2];
        double startPrice = Double.parseDouble(parts[3]);
        String imageUrl = parts[4];
        boolean isAuction = Boolean.parseBoolean(parts[5]);
        String itemType = parts[6];
        double buyItNowPrice = Double.parseDouble(parts[7]);
        Instant endTime = "null".equals(parts[8]) ? null : Instant.ofEpochMilli(Long.parseLong(parts[8]));
        boolean auctionActive = Boolean.parseBoolean(parts[9]);

        Item item = new Item(itemName, description, startPrice, imageUrl, isAuction, itemType, buyItNowPrice, endTime);
        item.setItemId(itemId);
        item.setEndTime(endTime);
        item.setAuctionActive(auctionActive);

        return item;
    }
}