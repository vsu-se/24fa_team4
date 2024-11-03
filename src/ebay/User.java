package ebay;



public class User implements Seller,Bidder {
    private String username;
    private String password;
    private boolean isSeller;
    private boolean isBidder;


    public User(String username, String password, boolean isSeller, boolean isBidder) {
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        this.isBidder = isBidder;
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


}

