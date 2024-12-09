package ebay;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getUsername());
    }

    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
        System.out.println("User deleted: " + username);
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    public void viewAllUsers() {
        users.forEach(user -> System.out.println("Username: " + user.getUsername()));
    }

    // Getter for users list for SystemAdmin or other classes
    public List<User> getUsers() {
        return users;
    }
}

