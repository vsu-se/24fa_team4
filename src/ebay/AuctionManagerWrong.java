/* package ebay;
DONT USE THIS/CAN BE DELETED
import java.util.ArrayList;
import java.util.List;

    public class AuctionManager {
        private List<Auction> auctions;

        public AuctionManager() {
            this.auctions = new ArrayList<>();
        }

        public void addAuction(Auction auction) {
            auctions.add(auction);
        }

        public List<Auction> getActiveAuctions() {
            List<Auction> activeAuctions = new ArrayList<>();
            for (Auction auction : auctions) {
                if (auction.isAuctionActive()) {
                    activeAuctions.add(auction);
                }
            }
            return activeAuctions;
        }
    }
}
*/