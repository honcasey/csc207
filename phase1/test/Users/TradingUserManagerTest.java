//package Users;
//
//import Exceptions.InvalidTradingUserException;
//import Items.Item;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TradingUserManagerTest {
//
//    @Test
//    void addTradingUser() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("caseyh", "pwd123");
//        tum.addTradingUser("anniel","pwd456");
//        assertEquals(2, tum.getAllTradingUsers().size());
//
//    }
//
//    @Test
//    void getTradingUser() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("caseyh", "pwd123");
//        tum.addTradingUser("anniel","pwd456");
//        assertEquals("anniel", tum.getTradingUser("anniel").getUsername());
//    }
//
//    @Test
//    void addItem() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("casey", "pwd123");
//        tum.addTradingUser("annie","pwd456");
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//        Item textbook2 = new Item("textbooktextbook");
//        Item notes = new Item("notes");
//        Item notes2 = new Item("notesnotes");
//        Item lt = new Item("laptop");
//        Item lt2 = new Item("laptoplaptop");
//        Item p = new Item("pencil");
//        Item p2 = new Item("pencilpencil");
//        tum.addItem(tum.getTradingUser("annie"), book, "wishlist");
//        tum.addItem(tum.getTradingUser("annie"), book2, "wishlist");
//        tum.addItem(tum.getTradingUser("annie"), textbook, "inventory");
//        assertEquals(2, tum.getTradingUser("annie").getWishlist().size());
//        assertEquals(1, tum.getTradingUser("annie").getInventory().size());
//    }
//
//    @Test
//    void removeItem() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("casey", "pwd123");
//        tum.addTradingUser("annie","pwd456");
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//        Item textbook2 = new Item("textbooktextbook");
//        Item notes = new Item("notes");
//        Item notes2 = new Item("notesnotes");
//        Item lt = new Item("laptop");
//        Item lt2 = new Item("laptoplaptop");
//        Item p = new Item("pencil");
//        Item p2 = new Item("pencilpencil");
//        tum.addItem(tum.getTradingUser("annie"), book, "wishlist");
//        tum.addItem(tum.getTradingUser("annie"), book2, "wishlist");
//        tum.addItem(tum.getTradingUser("annie"), textbook, "inventory");
//        assertEquals(2, tum.getTradingUser("annie").getWishlist().size());
//        assertEquals(1, tum.getTradingUser("annie").getInventory().size());
//        tum.removeItem(tum.getTradingUser("annie"), book,"wishlist");
//        tum.removeItem(tum.getTradingUser("annie"),textbook, "inventory");
//        assertEquals(1, tum.getTradingUser("annie").getWishlist().size());
//        assertEquals(0, tum.getTradingUser("annie").getInventory().size());
//
//    }
//
//    @Test
//    void changeThreshold() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("casey", "pwd123");
//        tum.addTradingUser("annie","pwd456");
//
//        assertEquals(1, tum.getTradingUser("annie").getBorrowThreshold());
//        assertEquals(3, tum.getTradingUser("annie").getWeeklyThreshold());
//        assertEquals(2, tum.getTradingUser("annie").getIncompleteThreshold());
//
//        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Borrow");
//        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Weekly");
//        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Incomplete");
//
//        assertEquals(4, tum.getTradingUser("annie").getBorrowThreshold());
//        assertEquals(4, tum.getTradingUser("annie").getWeeklyThreshold());
//        assertEquals(4, tum.getTradingUser("annie").getIncompleteThreshold());
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
//
//    @Test
//    void getAllTradingUsers() throws InvalidTradingUserException {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        tum.addTradingUser("caseyh", "pwd123");
//        tum.addTradingUser("anniel","pwd456");
//        assertEquals(2, tum.getAllTradingUsers().size());
//
//    }
//
//    @Test
//    void validUser() {
//    }
//
//    @Test
//    void getFlaggedAccounts() {
//    }
//
//    @Test
//    void getFrozenAccounts() {
//    }
//
//    @Test
//    void incompleteTransactionExceeded() {
//    }
//
//    @Test
//    void moveTransactionToTransactionHistory() {
//    }
//
//    @Test
//    void checkAvailableUsername() {
//    }
//
//    @Test
//    void getTradingUserById() {
//    }
//
//    @Test
//    void handlePermTransactionItems() {
//    }
//
//    @Test
//    void handleTempTransactionItems() {
//    }
//
//    @Test
//    void borrowThresholdExceeded() {
//    }
//
//    @Test
//    void convertFlaggedUsersToUsernames() {
//    }
//}