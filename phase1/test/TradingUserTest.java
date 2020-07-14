//import Exceptions.InvalidUserException;
//import Items.Item;
//import Transactions.Meeting;
//import Transactions.Transaction;
//import Users.TradingUser;
//import Users.TransactionHistory;
//import Transactions.TransactionOneWayPerm;
//import Users.TradingUserManager;
//import org.junit.jupiter.api.Test;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//public class TradingUserTest {
//
//    // Users.TradingUser entity tests
//    @Test
//    public void testUser() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//    }
//
//    @Test
//    public void testUserMethods() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals("caseyh", casey.getUsername());
//        assertEquals("pwd123", casey.getPassword());
//
//    }
//
//
//    @Test
//    void setTransactionDetails() {
//    }
//
//    @Test
//    void setInventory() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        Item book = new Item("book");
//        List<Item> inv1 = new ArrayList<>();
//        inv1.add(book);
//        casey.setInventory(inv1);
//        assertEquals(casey.getInventory(), inv1);
//    }
//
//    @Test
//    void setWishlist() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        Item book = new Item("book");
//        List<Item> wl1 = new ArrayList<>();
//        wl1.add(book);
//        casey.setWishlist(wl1);
//        assertEquals(casey.getWishlist(), wl1);
//    }
//
//    @Test
//    void setBorrowThreshold() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals(casey.getBorrowThreshold(), 1);
//        casey.setBorrowThreshold(2);
//        assertEquals(casey.getBorrowThreshold(), 2);
//    }
//
//    @Test
//    void setWeeklyThreshold() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals(casey.getWeeklyThreshold(), 3);
//        casey.setWeeklyThreshold(1);
//        assertEquals(casey.getWeeklyThreshold(), 1);
//    }
//
//
//    @Test
//    void setIncompleteThreshold() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals(casey.getIncompleteThreshold(), 2);
//        casey.setIncompleteThreshold(1);
//        assertEquals(casey.getIncompleteThreshold(), 1);
//    }
//
//    @Test
//    void getStatus() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals(casey.getStatus(), "active");
//        casey.setStatus("frozen");
//        assertEquals(casey.getStatus(), "frozen");
//    }
//
//    @Test
//    void testToString() {
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        assertEquals(casey.toString(), "caseyh, " + casey.getUserId());
//    }
//
//    // Users.UserManager use case tests
//
//    @Test
//    void addUser() throws InvalidUserException {
//        TradingUser tradingUser1 = new TradingUser("casey", "pwd123");
//        TradingUser tradingUser2 = new TradingUser("annie", "pwd123");
//        List<TradingUser> tradingUserList = new ArrayList<>();
//        List<TradingUser> flaggedAccounts = new ArrayList<>();
//        List<TradingUser> frozenAccounts = new ArrayList<>();
//        tradingUserList.add(tradingUser1);
//        tradingUserList.add(tradingUser2);
//        TradingUserManager um = new TradingUserManager(tradingUserList, flaggedAccounts, frozenAccounts);
//        assertEquals(um.addTradingUser("anna", "pwd123").getUsername(), "anna");
//        assertNull(um.addTradingUser("casey", "pwd123"));
//    }
//
//    @Test
//    void getUser() throws Exception {
//        TradingUser tradingUser1 = new TradingUser("casey", "pwd123");
//        TradingUser tradingUser2 = new TradingUser("annie", "pwd123");
//        List<TradingUser> tradingUserList = new ArrayList<>();
//        List<TradingUser> flaggedAccounts = new ArrayList<>();
//        List<TradingUser> frozenAccounts = new ArrayList<>();
//        tradingUserList.add(tradingUser1);
//        tradingUserList.add(tradingUser2);
//        TradingUserManager um = new TradingUserManager(tradingUserList, flaggedAccounts, frozenAccounts);
//        assertEquals(um.getTradingUser("casey"), tradingUser1);
//        assertEquals(um.getTradingUser("annie"), tradingUser2);
//
//    }
//
//    @Test
//    void addItem() {
//        TradingUser tradingUser1 = new TradingUser("casey", "pwd123");
//        TradingUser tradingUser2 = new TradingUser("annie", "pwd123");
//        List<TradingUser> tradingUserList = new ArrayList<>();
//        List<TradingUser> flaggedAccounts = new ArrayList<>();
//        List<TradingUser> frozenAccounts = new ArrayList<>();
//        tradingUserList.add(tradingUser1);
//        tradingUserList.add(tradingUser2);
//        TradingUserManager um = new TradingUserManager(tradingUserList, flaggedAccounts, frozenAccounts);
//        Item book = new Item("hamlet");
//        um.addItem(tradingUser1, book, "wishlist");
//        assertTrue(tradingUser1.getWishlist().contains(book));
//    }
//
//    @Test
//    void removeItem() {
//        TradingUser tradingUser1 = new TradingUser("casey", "pwd123");
//        TradingUser tradingUser2 = new TradingUser("annie", "pwd123");
//        List<TradingUser> tradingUserList = new ArrayList<>();
//        List<TradingUser> flaggedAccounts = new ArrayList<>();
//        List<TradingUser> frozenAccounts = new ArrayList<>();
//        tradingUserList.add(tradingUser1);
//        tradingUserList.add(tradingUser2);
//        TradingUserManager um = new TradingUserManager(tradingUserList, flaggedAccounts, frozenAccounts);
//        Item book = new Item("hamlet");
//        um.addItem(tradingUser1, book, "wishlist");
//        assertTrue(tradingUser1.getWishlist().contains(book));
//        um.removeItem(tradingUser1, book, "wishlist");
//        assertFalse(tradingUser1.getWishlist().contains(book));
//    }
//
//    @Test
//    void changeThreshold() {
//    }
//
//    @Test
//    void freezeAccount() {
//    }
//
//    @Test
//    void unfreezeAccount() {
//    }
//
//    @Test
//    void addToTransactionHistory() {
//    }
//
//    @Test
//    void checkAvailableUsername() {
//    }
//
//    @Test
//    void getAllUsers() {
//    }
//
//    @Test
//    void validUser() {
//    }
//
//}