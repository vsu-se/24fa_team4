package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        userManager = new UserManager();
        user1 = new User("john_doe", "password123", true, false);
        user2 = new User("jane_doe", "password456", false, true);
        userManager.addUser(user1);
        userManager.addUser(user2);
    }

    @Test
    public void testAddUser() {
        User newUser = new User("new_user", "password789", false, false);
        userManager.addUser(newUser);
        assertEquals(3, userManager.getUsers().size(), "User list should have 3 users");
        assertEquals(newUser, userManager.findUserByUsername("new_user"), "New user should be added and found by username");
    }

    @Test
    public void testDeleteUser() {
        userManager.deleteUser("john_doe");
        assertEquals(1, userManager.getUsers().size(), "User list should have 1 user after deletion");
        assertNull(userManager.findUserByUsername("john_doe"), "Deleted user should not be found");
    }

    @Test
    public void testFindUserByUsername_UserExists() {
        User foundUser = userManager.findUserByUsername("jane_doe");
        assertNotNull(foundUser, "User should be found");
        assertEquals(user2, foundUser, "Found user should be Jane Doe");
    }

    @Test
    public void testFindUserByUsername_UserDoesNotExist() {
        User foundUser = userManager.findUserByUsername("nonexistent_user");
        assertNull(foundUser, "Nonexistent user should return null");
    }

    @Test
    public void testViewAllUsers() {
        userManager.viewAllUsers();
        assertEquals(2, userManager.getUsers().size(), "User list should still have 2 users");
    }

}


