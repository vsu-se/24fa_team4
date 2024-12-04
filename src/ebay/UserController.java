package ebay;

public class UserController {
    private User currentUser;

    public boolean login(String username, String password) {
        // Add your login validation logic here
        // For now, let's assume a simple validation
        if ("validUsername".equals(username) && "validPassword".equals(password)) {
            currentUser = new User(username, password);
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
