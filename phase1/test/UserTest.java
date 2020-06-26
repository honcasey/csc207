import org.junit.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class UserTest {

    // the User constructor
    @Test
    public void testUser() {
        User casey = new User("caseyh", "pwd123");
    }

    // test User methods
    @Test
    public void testUserMethods() {
        User casey = new User("caseyh", "pwd123");
        assertEquals("caseyh", casey.getUsername());
        assertEquals("pwd123", casey.getPassword());

    }

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
    void setTransactionDetails() {
    }

    @org.junit.jupiter.api.Test
    void setInventory() {
        User casey = new User("caseyh", "pwd123");
        Item book = new Item("book");
        List<Item> inv1 = new ArrayList<Item>();
        inv1.add(book);
        casey.setInventory(inv1);
        assertEquals(casey.getInventory(), inv1);
    }

    @org.junit.jupiter.api.Test
    void setWishlist() {
        User casey = new User("caseyh", "pwd123");
        Item book = new Item("book");
        List<Item> wl1 = new ArrayList<Item>();
        wl1.add(book);
        casey.setWishlist(wl1);
        assertEquals(casey.getWishlist(), wl1);
    }

    @org.junit.jupiter.api.Test
    void setBorrowThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getBorrowThreshold(), 1);
        casey.setBorrowThreshold(2);
        assertEquals(casey.getBorrowThreshold(), 2);
    }

    @org.junit.jupiter.api.Test
    void setWeeklyThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getWeeklyThreshold(), 3);
        casey.setWeeklyThreshold(1);
        assertEquals(casey.getWeeklyThreshold(), 1);
    }


    @org.junit.jupiter.api.Test
    void setIncompleteThreshold() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getIncompleteThreshold(), 3);
        casey.setIncompleteThreshold(1);
        assertEquals(casey.getIncompleteThreshold(), 1);
    }

    @org.junit.jupiter.api.Test
    void getStatus() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.getStatus(), "active");
        casey.setStatus("frozen");
        assertEquals(casey.getStatus(), "frozen");
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        User casey = new User("caseyh", "pwd123");
        assertEquals(casey.toString(), "caseyh, " + casey.getUserId());
    }

}