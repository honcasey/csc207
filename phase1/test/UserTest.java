import org.junit.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserTest {

    // User entity tests
    @Test
    public void testUser() {
        User casey = new User("caseyh", "pwd123");
    }

    @Test
    public void testUserMethods() {
        User casey = new User("caseyh", "pwd123");
        assertEquals("caseyh", casey.getUsername());
        assertEquals("pwd123", casey.getPassword());

    }

    @Test
    void setTransactionHistory() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meetup1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran1 = new TransactionOneWayPerm(casey, annie, book, meetup1);
        TransactionHistory his1 = new TransactionHistory();
        his1.setTransactionHistory(tran1);
        casey.setTransactionHistory(his1);
        assertEquals(casey.getTransactionHistory(), his1);
    }

    @Test
    void setTransactionDetails() {
    }

    @Test
    void setInventory() {
        User casey = new User("caseyh", "pwd123");
        Item book = new Item("book");
        List<Item> inv1 = new ArrayList<>();
        inv1.add(book);
        casey.setInventory(inv1);
        assertEquals(casey.getInventory(), inv1);
    }

    @Test
    void setWishlist() {
        User casey = new User("caseyh", "pwd123");
        Item book = new Item("book");
        List<Item> wl1 = new ArrayList<>();
        wl1.add(book);
        casey.setWishlist(wl1);
        assertEquals(casey.getWishlist(), wl1);
    }

    @Test
    void setBorrowThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getBorrowThreshold(), 1);
        casey.setBorrowThreshold(2);
        assertEquals(casey.getBorrowThreshold(), 2);
    }

    @Test
    void setWeeklyThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getWeeklyThreshold(), 3);
        casey.setWeeklyThreshold(1);
        assertEquals(casey.getWeeklyThreshold(), 1);
    }


    @Test
    void setIncompleteThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getIncompleteThreshold(), 2);
        casey.setIncompleteThreshold(1);
        assertEquals(casey.getIncompleteThreshold(), 1);
    }

    @Test
    void getStatus() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getStatus(), "active");
        casey.setStatus("frozen");
        assertEquals(casey.getStatus(), "frozen");
    }

    @Test
    void testToString() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.toString(), "caseyh, " + casey.getUserId());
    }

    // UserManager use case tests

    @Test
    void addUser() {
        User user1 = new User("casey", "pwd123");
        User user2 = new User("annie", "pwd123");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        UserManager um = new UserManager(userList);
        assertEquals(um.addUser("anna", "pwd123").getUsername(), "anna");
        assertNull(um.addUser("casey", "pwd123"));
    }

    @Test
    void getUser() throws Exception {
        User user1 = new User("casey", "pwd123");
        User user2 = new User("annie", "pwd123");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        UserManager um = new UserManager(userList);
        assertEquals(um.getUser("casey"), user1);
        assertEquals(um.getUser("annie"), user2);

    }

    @Test
    void addItem() {
        User user1 = new User("casey", "pwd123");
        User user2 = new User("annie", "pwd123");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        UserManager um = new UserManager(userList);
        Item book = new Item("hamlet");
        um.addItem(user1, book, "wishlist");
        assertTrue(user1.getWishlist().contains(book));
    }

    @Test
    void removeItem() {
        User user1 = new User("casey", "pwd123");
        User user2 = new User("annie", "pwd123");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        UserManager um = new UserManager(userList);
        Item book = new Item("hamlet");
        um.addItem(user1, book, "wishlist");
        assertTrue(user1.getWishlist().contains(book));
        um.removeItem(user1, book, "wishlist");
        assertFalse(user1.getWishlist().contains(book));
    }

    @Test
    void changeThreshold() {
    }

    @Test
    void freezeAccount() {
    }

    @Test
    void unfreezeAccount() {
    }

    @Test
    void addToTransactionHistory() {
    }

    @Test
    void checkAvailableUsername() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void validUser() {
    }

}