// src/controller/UserHomePageController.java
package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserHomePageController {

    private UserHomePage view;
    private UserController userController;
    private ItemManager itemManager;
    private ItemController itemController;

    public UserHomePageController(UserController userController, ItemManager itemManager, ItemController itemController, UserManager userManager, User user) {
        this.userController = userController;
        this.itemManager = itemManager;
        this.itemController = itemController;
        this.view = new UserHomePage(userManager, user);
        setUpEventListeners();
    }

    private void setUpEventListeners() {
        view.getAddItemBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = view.getItemName().getText();
                String itemDescription = view.getDescription().getText();
                double startPrice = Double.parseDouble(view.getStartPrice().getText());
                String imageUrl = view.getImageUrl().getText();
                handleAddItem(itemName, itemDescription, startPrice, imageUrl);
            }
        });
    }

    private void handleAddItem(String itemName, String itemDescription, double startPrice, String imageUrl) {
        boolean isAuction = true;
        String category = view.getCategoryList().getSelectedValue();

        if (category == null) {
            view.showError("Please select an item category.");
            return;
        }

        Item newItem = new Item(itemName, itemDescription, startPrice, imageUrl, isAuction, category, startPrice);

        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            currentUser.listItem(newItem);
            view.showInfo("Item added successfully!");

            itemManager.addItem(newItem);
            view.updateActiveAuctionsList(itemManager.getActiveAuctions());
            view.addItemToMyAuctions(newItem);
            view.addItemToBuyTab(newItem);
            view.switchToMyAuctionsTab();

            view.getItemName().setText("");
            view.getDescription().setText("");
            view.getStartPrice().setText("");
            view.getImageUrl().setText("");
        } else {
            view.showError("No user is currently logged in.");
        }
    }
}