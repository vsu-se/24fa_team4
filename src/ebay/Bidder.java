package ebay;

public interface Bidder {
    void placeBid(Item item, double bidAmount);
    String getUsername();
}