package model;

public class Bid {
    private Bidder bidder;
    private double bidAmount;

    public Bid(Bidder bidder, double bidAmount) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public double getBidAmount() {
        return bidAmount;
    }


    }