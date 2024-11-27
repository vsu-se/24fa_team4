package ebay;


import java.util.ArrayList;
import java.util.List;

public class User implements Seller, Bidder {
    private String username;
    private String password;
    private boolean isSeller;
    private boolean isBidder;
    private List<Item> soldItems;
    private List<Item> boughtItems;


    public User(String username, String password, boolean isSeller, boolean isBidder) {
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        this.isBidder = isBidder;
        this.soldItems = new ArrayList<>();
        this.boughtItems = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public boolean isBidder() {
        return isBidder;
    }

    public void setIsSeller(boolean isSeller) {
        this.isSeller = isSeller;
    }

    public void setIsBidder(boolean isBidder) {
        this.isBidder = isBidder;
    }

    // Implementation of listItem method from Seller interface
    @Override
    public void listItem(Item item) {
        if (isSeller) {
            ItemManager.getInstance().addItem(item);  // Use the singleton instance of ItemManager
            System.out.println(username + " has listed an item: " + item.getItemName());
        } else {
            System.out.println(username + " is not authorized to list items.");
        }
    }

    // Implementation of placeBid method from Bidder interface
    @Override
    public void placeBid(Item item, double bidAmount) {
        System.out.println(username + " has placed a bid of $" + bidAmount + " on item: " + item.getItemName());
    }


    @Override
    public void startAuction(Item item) {
        if (isSeller && item.isAuction()) {
            Auction auction = new Auction(item);
            ItemManager.getInstance().addAuction(auction);
            System.out.println(username + " has started an auction for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
        } else {
            System.out.println(username + " is not authorized to start an auction or the item is not available for auction.");
        }
    }

    public void buyItNow(Item item) {
        if (!isSeller) {  // Any non-seller can buy an item
            ItemManager.getInstance().buyItNow(item, this);
        } else {
            System.out.println(username + " cannot buy their own item.");
        }
    }

    public void buyItNow(Item item, User user) {
        if (!isSeller) {  // Any non-seller can buy an item
            ItemManager.getInstance().buyItNow(item, user);
        } else {
            System.out.println(username + " cannot buy their own item.");
        }
    }

    public void addSoldItem(Item item) {
        if (item != null) {
            soldItems.add(item);
        }
    }

    public void addBoughtItem(Item item) {
        if (item != null) {
            boughtItems.add(item);
        }
    }

    public void showSellerReport() {
        double totalWinningBids = 0;
        double totalShippingCosts = 0;
        double totalSellerCommission = 0;

        System.out.println("Seller's Report for " + username);
        System.out.println("--------------------------------------------------");
        System.out.println("Item Name | Price | Seller's Commission | Shipping");
        System.out.println("--------------------------------------------------");

        for (Item item : soldItems) {
            if (item != null) {
                double price = item.getBuyItNowPrice();
                double sellersComission = price * 0.20;
                double shippingCost = 10.0;

                totalWinningBids += price;
                totalSellerCommission += sellersComission;
                totalShippingCosts += shippingCost;

                System.out.printf("%s | %.2f | %.2f | %.2f%n", item.getItemName(), price, sellersComission, shippingCost);
            }
        }

        double totalProfits = totalWinningBids - totalSellerCommission - totalShippingCosts;

        System.out.println("--------------------------------------------------");
        System.out.printf("Total Winning Bids: %.2f%n", totalWinningBids);
        System.out.printf("Total Shipping Costs: %.2f%n", totalShippingCosts);
        System.out.printf("Total Seller's Commissions: %.2f%n", totalSellerCommission);
        System.out.printf("Total Profits: %.2f%n", totalProfits);
    }

    public void showBuyerReport() {
        double totalSpent = 0;
        double totalShippingCosts = 0;

        System.out.println("Buyer's Report for " + username);
        System.out.println("--------------------------------------------------");
        System.out.println("Item Name | Price | Shipping");
        System.out.println("--------------------------------------------------");

        for (Item item : boughtItems) {
            if (item != null) {
                double price = item.getBuyItNowPrice();
                double shippingCost = 10.0;

                totalSpent += price;
                totalShippingCosts += shippingCost;

                System.out.printf("%s | %.2f | %.2f%n", item.getItemName(), price, shippingCost);
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Total Spent: %.2f%n", totalSpent);
        System.out.printf("Total Shipping Costs: %.2f%n", totalShippingCosts);
    }
        }


