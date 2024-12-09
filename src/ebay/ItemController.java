
package ebay;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;


public class ItemController {
    private ItemManager itemManager;
    private List<Item> items;

    public ItemController() {
        items = new ArrayList<>();
        itemManager = new ItemManager();


    }

    // Add item
    public void addItem(String itemName, String description, double startPrice, String imageUrl, boolean isAuction, String itemType, double buyItNowPrice) {
        Item newItem = new Item(itemName, description, startPrice, imageUrl, isAuction, itemType, buyItNowPrice);
        itemManager.addItem(newItem);
    }

    public void filterItemsByCategory(String category) {
        List<Item> filteredItems = items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        updateUIWithFilteredItems(filteredItems);
    }

    private void updateUIWithFilteredItems(List<Item> filteredItems) {
        // Update the UI with the filtered items
        // This method should be implemented to refresh the UI with the filtered items
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
            boolean success = itemManager.placeBid(item, bid);
            if (success) {
                // Notify the user that the bid was placed successfully
            } else {

            }
        }
    }

    //Buy it now
    public void buyItNow(String itemName, User buyer) {
        Item item = itemManager.getItemByName(itemName);
        if (item != null) {
            itemManager.buyItNow(item, buyer);
        }
    }


    public List<Item> getActiveAuctions() {
        return itemManager.getActiveAuctions().stream()
                .sorted(Comparator.comparing(Item::getEndTime))
                .collect(Collectors.toList());
    }

    public void populateDefaultActiveAuctions() {
        itemManager.populateDefaultActiveAuctions();
    }


    public List<Item> getConcludedAuctions() {
        return itemManager.getConcludedAuctions();
    }

    public List<Item> searchItems(String searchQuery) {
        return itemManager.searchItems(searchQuery);
    }

    public void clearItems() {
        itemManager.clearItems();
    }

}