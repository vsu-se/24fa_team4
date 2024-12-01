package GUI;

import ebay.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserHomePageController {
    private final UserHomePage userHomePage;
    private final User sellerUser;
    private final User buyerUser;
    private final ItemManager itemManager;
    private final SystemAdminPage systemAdminPage;

    public UserHomePageController(User sellerUser, User buyerUser, UserHomePage userHomePage, ItemManager itemManager, SystemAdminPage systemAdminPage) {
        this.sellerUser = sellerUser;
        this.buyerUser = buyerUser;
        this.userHomePage = userHomePage;
        this.itemManager = itemManager;
        this.systemAdminPage = systemAdminPage;

        setUpEventListeners();
    }

    private void setUpEventListeners() {
        userHomePage.getSearchBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = userHomePage.getSearchTextField().getText();
                handleSearch(searchQuery);
            }
        });

        userHomePage.getBidButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bid = Double.parseDouble(userHomePage.getBidAmount().getText());
                handleBid(bid);
            }
        });

        userHomePage.getAddItemBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = userHomePage.getItemName().getText();
                String itemDescription = userHomePage.getDescription().getText();
                double startPrice = Double.parseDouble(userHomePage.getStartPrice().getText());
                String imageUrl = userHomePage.getImageUrl().getText();
                handleAddItem(itemName, itemDescription, startPrice, imageUrl);
            }
        });
    }

    public void handleSearch(String searchQuery) {
        // Search for items based on the search query
        List<Item> searchResults = itemManager.searchItems(searchQuery);
        userHomePage.showSearchResults(searchResults);
    }

    void handleBid(double bid) {
        // Place a bid on the selected item
        Item selectedItem = userHomePage.getSelectedItem();
        if (selectedItem != null) {
            Bid newBid = new Bid(buyerUser, bid);
            boolean bidSuccess = selectedItem.placeBid(newBid);
            if (bidSuccess) {
                userHomePage.showInfo("Bid placed successfully!");
            } else {
                userHomePage.showError("Failed to place bid. Please try again.");
            }
        } else {
            userHomePage.showError("No item selected. Please select an item to bid on.");
        }
    }

    public void handleAddItem(String itemName, String itemDescription, double startPrice, String imageUrl) {
        // Add a new item to the system
        boolean isAuction = userHomePage.getIsAuction().isSelected();
        String itemType = userHomePage.getItemType().getSelectedItem().toString();

        Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, itemType, startPrice);
        sellerUser.listItem(newItem);
        userHomePage.showInfo("Item added successfully!");
        userHomePage.addItemToMyAuctions(newItem); // Add item to "My Auctions" tab
        userHomePage.switchToMyAuctionsTab(); // Switch to the "My Auctions" tab
    }

    public void startAuction(Item item, long endTime) {
        // Set auction details and start the auction
        item.setAuction(true);
        item.setEndTime(endTime);
        itemManager.startAuction(item);
        userHomePage.showInfo("Auction started successfully!");
    }

    public void showConcludedAuctions() {
        // Show concluded auctions
        List<Item> concludedAuctions = itemManager.getConcludedAuctions();
        userHomePage.showConcludedAuctions(concludedAuctions);
        systemAdminPage.showConcludedAuctions(concludedAuctions);
    }
}