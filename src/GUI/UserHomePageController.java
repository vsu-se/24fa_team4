package GUI;

import ebay.*;
import java.util.List;

public class UserHomePageController {

    private User sellerUser;
    private User buyerUser;
    private UserHomePage userHomePage;
    private ItemManager itemManager;

    public UserHomePageController(User sellerUser, User buyerUser, UserHomePage userHomePage, ItemManager itemManager) {
        this.sellerUser = sellerUser;
        this.buyerUser = buyerUser;
        this.userHomePage = userHomePage;
        this.itemManager = itemManager;
    }

    public void handleSearch(String searchQuery) {
        List<Item> searchResults = itemManager.searchItems(searchQuery);
        userHomePage.setSearchResults(searchResults);
        userHomePage.showSearchResults(searchResults);
    }

    public void handleBid(double bidAmount) {
        Item selectedItem = userHomePage.getSelectedItem();
        if (selectedItem == null) {
            userHomePage.setErrorMessage("No item selected. Please select an item to bid on.");
            return;
        } else if (bidAmount < selectedItem.getStartPrice()) {
            userHomePage.setErrorMessage("Bid amount is less than the starting price.");
            return;
        } else {
            selectedItem.placeBid(new Bid(buyerUser, bidAmount));
            userHomePage.setErrorMessage("");
        }
    }

    public void handleAddItem(String itemName, String itemDescription, double startPrice, String imageUrl) {
        boolean isAuction = userHomePage.isAuctionCheckBox.isSelected();
        String itemType = (String) userHomePage.itemTypeComboBox.getSelectedItem();

        Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, itemType, startPrice);
        itemManager.addItem(newItem);
        userHomePage.addItemToMyAuctions(newItem);
    }

    public void startAuction(Item item, long endTime) {
        item.setAuction(true);
        item.setEndTime(endTime);
        itemManager.startAuction(item);
    }

    public void showConcludedAuctions() {
        List<Item> concludedAuctions = itemManager.getConcludedAuctions();
        userHomePage.setConcludedAuctions(concludedAuctions);
        userHomePage.showConcludedAuctions(concludedAuctions);
    }
}