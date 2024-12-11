package ebay;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class User implements Seller, Bidder {
    private String username;
    private String password;
    private boolean isSeller;
    private boolean isBidder;
    private boolean isAdmin;
    private UserManager userManager;
    private ItemManager itemManager;
    private List<Item> soldItems = new ArrayList<>();
    private List<Item> boughtItems = new ArrayList<>();
    private List<Bid> bids;
    private boolean authorizedToListItems;

    public User(String username, String password, boolean isSeller, boolean isBidder) {
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        this.isBidder = isBidder;
        this.isAdmin = false;
        this.bids = new ArrayList<>();
    }

    public User(String adminPassword, String username, String password, UserManager userManager) {
        this(username, password, false, false);
        if (adminPassword.equals("admin")) {
            this.isAdmin = true;
            this.userManager = userManager;
        }
    }

    public User(String username, String password) {
        this(username, password, false, false);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Bid> getBids() {
        return bids;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsSeller(boolean isSeller) {
        this.isSeller = isSeller;
    }

    public void setIsBidder(boolean isBidder) {
        this.isBidder = isBidder;
    }

    @Override
    public void listItem(Item item) {
        if (isSeller) {
            ItemManager.getInstance().addItem(item);
            System.out.println(username + " has listed an item: " + item.getItemName());
        } else {
            System.out.println(username + " is not authorized to list items.");
        }
    }

    @Override
    public void placeBid(Item item, double bidAmount) {
        Bid bid = new Bid(this, bidAmount,item);
        System.out.println(username + " has placed a bid of $" + bidAmount + " on item: " + item.getItemName());
        item.addBid(bid);
    }

    @Override
    public void startAuction(Item item) {
        if (isSeller && item.isAuction()) {
            item.setAuction(true);
            Instant endTime = Instant.now().plusSeconds(86400); // Default to 1 day
            ItemManager.getInstance().startAuction(item,endTime);
            System.out.println(username + " has started an auction for item: " + item.getItemName() + " (ID: " + item.getItemId() + ")");
        } else {
            System.out.println(username + " is not authorized to start an auction or the item is not available for auction.");
        }
    }

    public void buyItNow(Item item) {
        if (!isSeller) {
            ItemManager.getInstance().buyItNow(item, this);
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

    public List<Item> getSoldItems() {
        return soldItems;
    }

    public List<Item> getBoughtItems() {
        return boughtItems;
    }

    // Admin specific functionalities
    public void deleteUser(String username) {
        if (isAdmin) {
            userManager.deleteUser(username);
        }
    }

    public void viewAllUsers() {
        if (isAdmin) {
            userManager.viewAllUsers();
        }
    }

    public void setAuthorizedToListItems(boolean authorized) {
        this.authorizedToListItems = authorized;
    }

    public boolean isAuthorizedToListItems() {
        return authorizedToListItems;
    }

    public void approveItem(ItemManager itemManager, Item item) {
        if (isAdmin) {
            itemManager.addItem(item);
            System.out.println("Item approved and added: " + item.getItemName());
        }
    }

    public List<Item> getActiveAuctions() {
        return itemManager.getActiveAuctions();
    }
}