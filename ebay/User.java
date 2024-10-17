package ebay;



public class User implements Seller,Bidder {
    private String username;
    private String password;
    public boolean isSeller;
    public boolean isBidder;

    public User(String username, String password, boolean isSeller, boolean isBidder) {
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        this.isBidder = isBidder;
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

    // Implementation of listItem method from Seller interface
    @Override
    public void listItem(Item item) {
        System.out.println(username + " has listed an item: " + item.getItemName());
    }

    // Implementation of placeBid method from Bidder interface
    @Override
    public void placeBid(Item item, double bidAmount) {
        System.out.println(username + " has placed a bid of $" + bidAmount + " on item: " + item.getItemName());
    }


}

