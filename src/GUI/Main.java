package GUI;

import ebay.ItemController;
import ebay.ItemManager;
import ebay.UserController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();

        UserController userController = new UserController();
        ItemManager itemManager = new ItemManager();
        ItemController itemController = new ItemController(itemManager);

        new UserHomePage("username", "password", userController, itemManager, itemController);
        });
    }
}
