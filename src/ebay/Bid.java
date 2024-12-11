package ebay;


public class Bid {
    private Bidder bidder;
    private double bidAmount;
    private Item item;

    public Bid(Bidder bidder, double bidAmount, Item item) {
        this.bidder = bidder;
        this.item = item;
        this.bidAmount = bidAmount;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public double getBidAmount() {
        return bidAmount;
    }


    public Item getItem() {
        return item;
    }
}
