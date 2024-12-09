package model;

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

    public boolean register(String username, String password) {
        if (findUserByUsername(username) == null) {
            User newUser = new User(username, password);
            addUser(newUser);
            return true;
        }
        return false;
    }

    public User login(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

