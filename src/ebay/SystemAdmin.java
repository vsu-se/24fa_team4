package ebay;

public class SystemAdmin {
    private String username;
    private String password;

    private UserManager userManager;

    public SystemAdmin(String adminPassword, String username, String password, UserManager userManager) {
        // Password to be able to create an admin user.
        if (adminPassword.equals("Password")) {
            this.username = username;
            this.password = password;
            this.userManager = userManager;
        } else {
            throw new IllegalArgumentException("Invalid admin password provided.");
        }
    }

    public SystemAdmin(String username, String password) {
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

    // Admin specific functionalities
    public void deleteUser(String username) {
        User user = userManager.findUserByUsername(username);
        if (user != null) {
            userManager.deleteUser(username);
            System.out.println("User deleted: " + username);
        } else {
            System.out.println("User not found: " + username);
        }
    }

    public void viewAllUsers() {
        if (userManager.getUsers().isEmpty()) {
            System.out.println("No users to display.");
        } else {
            userManager.viewAllUsers();
        }
    }

    public void approveItem(ItemManager itemManager, Item item) {
        if (itemManager.getItemByUUID(item.getItemId()) == null) {
            itemManager.addItem(item);
            System.out.println("Item approved and added: " + item.getItemName());
        } else {
            System.out.println("Item is already approved: " + item.getItemName());
        }
    }
}



