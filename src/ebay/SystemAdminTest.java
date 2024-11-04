package ebay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemAdminTest {
    private SystemAdmin systemAdmin;
    private UserManager userManager;
    private ItemManager itemManager;
    private User user1;
    private Item item1;

    @BeforeEach
    public void setUp() {
        userManager = new UserManager();
        itemManager = ItemManager.getInstance();
        systemAdmin = new SystemAdmin("Password", "admin", "admin123", userManager);
        user1 = new User("john_doe", "password123", true, false);
        item1 = new Item("Vintage Camera", "A vintage camera from the 1950s", 250.00, "https://example.com/vintage-camera.jpg", true, "Electronics");
        userManager.addUser(user1);
    }

    @Test
    public void testDeleteUser() {
        systemAdmin.deleteUser("john_doe");
        assertNull(userManager.findUserByUsername("john_doe"), "User should be deleted by the admin");
    }

    @Test
    public void testDeleteNonExistentUser() {
        systemAdmin.deleteUser("non_existent_user");
        assertNull(userManager.findUserByUsername("non_existent_user"), "Non-existent user should not be found");
    }

    @Test
    public void testViewAllUsersWhenEmpty() {
        userManager.deleteUser("john_doe");
        systemAdmin.viewAllUsers();
        assertEquals(0, userManager.getUsers().size(), "There should be no users to view");
    }

    @Test
    public void testViewAllUsers() {
        systemAdmin.viewAllUsers();
        assertEquals(1, userManager.getUsers().size(), "Admin should be able to view all users");
    }

    @Test
    public void testApproveItem() {
        systemAdmin.approveItem(itemManager, item1);
        assertTrue(itemManager.getAllItems().contains(item1), "Item should be approved and added by the admin");
    }

    @Test
    public void testApproveAlreadyApprovedItem() {
        systemAdmin.approveItem(itemManager, item1);
        systemAdmin.approveItem(itemManager, item1);
        assertEquals(1, itemManager.getAllItems().stream().filter(item -> item.equals(item1)).count(), "Item should not be added multiple times");
    }

    @Test
    public void testInvalidAdminPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new SystemAdmin("WrongPassword", "invalidAdmin", "invalid123", userManager);
        });
        assertEquals("Invalid admin password provided.", exception.getMessage(), "Admin should not be created with an invalid password");
    }
}




