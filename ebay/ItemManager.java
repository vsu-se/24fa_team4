package ebay;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    //This will hold all the items for our ebay project
    private final List<Item> items;

    public ItemManager() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public synchronized void removeItem(String itemName) {
        items.removeIf(item -> item.getItemName().equals(itemName));
    }


    public List<Item> getAllItems(){
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





}
