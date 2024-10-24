package ebay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User users;
    private User sellerUser;
    private Item testItem;

    @BeforeEach
    void setUp() {
        users = new User("testUser", "testPassword", false, true);
        sellerUser = new User("sellerUser", "password", true, false);
        testItem = new Item(
                "Vintage Camera",
                "A vintage camera from the 1950s in excellent condition.",
                250.00,
                "https://example.com/vintage-camera.jpg",
                true, // This is an auction item
                "Electronics");
    }

    @Test
    void testGetUsername(){
        assertEquals("testUser",users.getUsername());
    }
    @Test
    void testSetUsername(){
        users.setUsername("newUser");
        assertEquals("newUser", users.getUsername());
    }
    @Test
    void testGetPassword(){
        assertEquals("testPassword",users.getPassword());
    }
    @Test
    void testSetPassword(){
        users.setPassword("newPassword");
        assertEquals("newPassword",users.getPassword());
    }
    @Test
    void testIsSeller(){
        assertTrue(users.isSeller);
    }
    @Test
    void testIsBidder(){
        assertTrue(users.isBidder);
    }
}