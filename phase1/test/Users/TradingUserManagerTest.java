package Users;

import Exceptions.InvalidTradingUserException;
import Items.Item;
import Transactions.Meeting;
import Transactions.TransactionOneWayTemp;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TradingUserManagerTest {

    @Test
    void addTradingUser() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("caseyh", "pwd123");
        tum.addTradingUser("anniel","pwd456");
        assertEquals(2, tum.getAllTradingUsers().size());

    }

    @Test
    void getTradingUser() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("caseyh", "pwd123");
        tum.addTradingUser("anniel","pwd456");
        assertEquals("anniel", tum.getTradingUser("anniel").getUsername());
    }

    @Test
    void addItem() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("casey", "pwd123");
        tum.addTradingUser("annie","pwd456");
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Item p = new Item("pencil");
        Item p2 = new Item("pencilpencil");
        tum.addItem(tum.getTradingUser("annie"), book, "wishlist");
        tum.addItem(tum.getTradingUser("annie"), book2, "wishlist");
        tum.addItem(tum.getTradingUser("annie"), textbook, "inventory");
        assertEquals(2, tum.getTradingUser("annie").getWishlist().size());
        assertEquals(1, tum.getTradingUser("annie").getInventory().size());
    }

    @Test
    void removeItem() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("casey", "pwd123");
        tum.addTradingUser("annie","pwd456");
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Item p = new Item("pencil");
        Item p2 = new Item("pencilpencil");
        tum.addItem(tum.getTradingUser("annie"), book, "wishlist");
        tum.addItem(tum.getTradingUser("annie"), book2, "wishlist");
        tum.addItem(tum.getTradingUser("annie"), textbook, "inventory");
        assertEquals(2, tum.getTradingUser("annie").getWishlist().size());
        assertEquals(1, tum.getTradingUser("annie").getInventory().size());
        tum.removeItem(tum.getTradingUser("annie"), book,"wishlist");
        tum.removeItem(tum.getTradingUser("annie"),textbook, "inventory");
        assertEquals(1, tum.getTradingUser("annie").getWishlist().size());
        assertEquals(0, tum.getTradingUser("annie").getInventory().size());

    }

    @Test
    void changeThreshold() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("casey", "pwd123");
        tum.addTradingUser("annie","pwd456");

        assertEquals(1, tum.getTradingUser("annie").getBorrowThreshold());
        assertEquals(3, tum.getTradingUser("annie").getWeeklyThreshold());
        assertEquals(2, tum.getTradingUser("annie").getIncompleteThreshold());

        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Borrow");
        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Weekly");
        tum.changeThreshold(tum.getTradingUser("annie"), 4, "Incomplete");

        assertEquals(4, tum.getTradingUser("annie").getBorrowThreshold());
        assertEquals(4, tum.getTradingUser("annie").getWeeklyThreshold());
        assertEquals(4, tum.getTradingUser("annie").getIncompleteThreshold());
    }

    @Test
    void freezeAccount(){
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        users.add(casey);
        users.add(annie);
        users.add(brandon);
        users.add(anna);
        users.add(tingting);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);

        tum.freezeAccount(annie);
        assertEquals("frozen", annie.getStatus());
    }

    @Test
    void unfreezeAccount() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        users.add(casey);
        users.add(annie);
        users.add(brandon);
        users.add(anna);
        users.add(tingting);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);

        tum.freezeAccount(annie);
        tum.unfreezeAccount(annie);

        assertEquals("active", annie.getStatus());
    }


    @Test
    void getAllTradingUsers() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("caseyh", "pwd123");
        tum.addTradingUser("anniel","pwd456");
        assertEquals(2, tum.getAllTradingUsers().size());

    }

    @Test
    void validUser() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("casey", "pwd123");
        tum.addTradingUser("annie","pwd456");
        assertTrue(tum.validUser("annie", "pwd456"));
        assertFalse(tum.validUser("christan", "oldman"));



    }

    @Test
    void moveTransactionToTransactionHistory() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        users.add(casey);
        users.add(annie);
        users.add(brandon);
        users.add(anna);
        users.add(tingting);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");

        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
        tran1.setStatus("complete");
        tran2.setStatus("incomplete");

        assertTrue(tum.moveTransactionToTransactionHistory(tran1, casey));
        assertTrue(tum.moveTransactionToTransactionHistory(tran2,casey));
        assertFalse(tum.moveTransactionToTransactionHistory(tran3, casey));
        assertEquals(2, casey.getTransactionHistory().getAllPastTransactions().size());

    }

    @Test
    void checkAvailableUsername() throws InvalidTradingUserException {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        tum.addTradingUser("casey", "pwd123");
        tum.addTradingUser("annie","pwd456");
        assertTrue(tum.checkAvailableUsername("bobbob"));
        assertFalse(tum.checkAvailableUsername("annie"));
    }

    @Test
    void getTradingUserById() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        users.add(casey);
        users.add(annie);
        users.add(brandon);
        users.add(anna);
        users.add(tingting);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);

        assertEquals("anniel", tum.getTradingUserById(annie.getUserId()).getUsername());

    }

    @Test
    void handlePermTransactionItems() {
    }

    @Test
    void handleTempTransactionItems() {
    }

    @Test
    void borrowThresholdExceeded() {
    }

    @Test
    void convertFlaggedUsersToUsernames() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        List<String> usernames = new ArrayList<>();
        usernames.add(casey.getUsername());
        usernames.add(annie.getUsername());
        usernames.add(brandon.getUsername());
        usernames.add(anna.getUsername());
        usernames.add(tingting.getUsername());

        flagged.add(casey);
        flagged.add(annie);
        flagged.add(brandon);
        flagged.add(anna);
        flagged.add(tingting);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);

        assertEquals(5, tum.convertFlaggedUsersToUsernames().size());
        assertEquals(usernames, tum.convertFlaggedUsersToUsernames());

    }
}