package GUI;

import ebay.*;

public class SampleAuctions {

    public static void main(String[] args) {
        ItemManager itemManager = ItemManager.getInstance();

        // Create sample items
        Item item1 = new Item("Vintage Camera", "A classic vintage camera.", 100.0, "http://example.com/camera.jpg", true, "Electronics", 100.0);
        Item item2 = new Item("Antique Vase", "A beautiful antique vase.", 200.0, "http://example.com/vase.jpg", true, "Home Decor", 200.0);
        Item item3 = new Item("Rare Book", "A rare first edition book.", 150.0, "http://example.com/book.jpg", true, "Books", 150.0);

        // Set auction end times
        long endTime1 = System.currentTimeMillis() + 86400000; // 1 day from now
        long endTime2 = System.currentTimeMillis() + 172800000; // 2 days from now
        long endTime3 = System.currentTimeMillis() + 259200000; // 3 days from now

        // Start auctions
        item1.setAuction(true);
        item1.setEndTime(endTime1);
        itemManager.startAuction(item1);

        item2.setAuction(true);
        item2.setEndTime(endTime2);
        itemManager.startAuction(item2);

        item3.setAuction(true);
        item3.setEndTime(endTime3);
        itemManager.startAuction(item3);

        // Add items to the item manager
        itemManager.addItem(item1);
        itemManager.addItem(item2);
        itemManager.addItem(item3);

        System.out.println("Sample auctions created successfully!");
    }
}
