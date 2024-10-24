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
        userManager.deleteUser(username);
    }

    public void viewAllUsers() {
        userManager.viewAllUsers();
    }

    public void approveItem(ItemManager itemManager, Item item) {
        itemManager.addItem(item);
        System.out.println("Item approved and added: " + item.getItemName());
    }
}


