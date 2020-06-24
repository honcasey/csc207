import org.junit.*;
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
    void getTransactionHistory() {
    }

    @org.junit.jupiter.api.Test
    void setTransactionHistory() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meetup1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran1 = new TransactionOneWayPerm(casey, annie, book, meetup1);

    }

    @org.junit.jupiter.api.Test
    void getTransactionDetails() {
    }

    @org.junit.jupiter.api.Test
    void setTransactionDetails() {
    }

    @org.junit.jupiter.api.Test
    void getInventory() {
    }

    @org.junit.jupiter.api.Test
    void setInventory() {
    }

    @org.junit.jupiter.api.Test
    void getWishlist() {
    }

    @org.junit.jupiter.api.Test
    void setWishlist() {
    }

    @org.junit.jupiter.api.Test
    void getBorrowThreshold() {
    }

    @org.junit.jupiter.api.Test
    void setBorrowThreshold() {
    }

    @org.junit.jupiter.api.Test
    void getWeeklyThreshold() {
    }

    @org.junit.jupiter.api.Test
    void setWeeklyThreshold() {
    }

    @org.junit.jupiter.api.Test
    void getIncompleteThreshold() {
    }

    @org.junit.jupiter.api.Test
    void setIncompleteThreshold() {
    }

    @org.junit.jupiter.api.Test
    void getStatus() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

}