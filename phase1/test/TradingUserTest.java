//import Exceptions.InvalidUserException;
import Items.Item;
import Transactions.Meeting;
import Transactions.Transaction;
import Transactions.TransactionOneWayTemp;
import Users.TradingUser;
import Users.TransactionHistory;
import Transactions.TransactionOneWayPerm;
import Users.TradingUserManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//public class TradingUserTest {

    // Users.TradingUser entity tests
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
//    void getTransactionHistory() {
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        TradingUser brandon = new TradingUser("brandont", "password123");
//        TradingUser anna = new TradingUser("annas", "password456");
//        TradingUser tingting = new TradingUser("tingtingz", "pw123");
//
//        users.add(casey);
//        users.add(annie);
//        users.add(brandon);
//        users.add(anna);
//        users.add(tingting);
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
//        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
//        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
//        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
//        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
//        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
//        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
//        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
//        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
//        tum.addToTransactionHistory(annie, tran1);
//        tum.addToTransactionHistory(annie, tran2);
//        tum.addToTransactionHistory(annie, tran3);
//        Map<UUID, Transaction> transactionMap = new HashMap<>();
//        transactionMap.put(tran1.getId(), tran1);
//        transactionMap.put(tran2.getId(), tran2);
//        transactionMap.put(tran3.getId(), tran3);
//        assertEquals(3, annie.getTransactionHistory().getAllPastTransactions().size());
//        assertEquals(3, annie.getTransactionHistory().mostRecentOneWayTransactions().size());
//
//    }
//
//    @Test
//    void getCurrentTransaction(){
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        TradingUser brandon = new TradingUser("brandont", "password123");
//        TradingUser anna = new TradingUser("annas", "password456");
//        TradingUser tingting = new TradingUser("tingtingz", "pw123");
//
//        users.add(casey);
//        users.add(annie);
//        users.add(brandon);
//        users.add(anna);
//        users.add(tingting);
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
//        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
//        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
//        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
//        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
//        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
//        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
//        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
//        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
//
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
//    @Test
//    void addToWishList(){
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        TradingUser brandon = new TradingUser("brandont", "password123");
//        TradingUser anna = new TradingUser("annas", "password456");
//        TradingUser tingting = new TradingUser("tingtingz", "pw123");
//
//        users.add(casey);
//        users.add(annie);
//        users.add(brandon);
//        users.add(anna);
//        users.add(tingting);
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
//        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
//        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
//        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
//        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
//        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
//        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
//        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
//        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
//
//        List<UUID> itemList = new ArrayList<>();
//        itemList.add(book.getId());
//        itemList.add(book2.getId());
//        itemList.add(textbook.getId());
//
//        annie.addToWishlist(book.getId());
//        annie.addToWishlist(book2.getId());
//        annie.addToWishlist(textbook.getId());
//
//        assertEquals(3, annie.getWishlist().size());
//        assertEquals(itemList, annie.getWishlist());
//
//    }
//
//    @Test
//    void removeFromWishList(){
//        List<TradingUser> users = new ArrayList<>();
//        List<TradingUser> flagged = new ArrayList<>();
//        List<TradingUser> frozen = new ArrayList<>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        TradingUser brandon = new TradingUser("brandont", "password123");
//        TradingUser anna = new TradingUser("annas", "password456");
//        TradingUser tingting = new TradingUser("tingtingz", "pw123");
//
//        users.add(casey);
//        users.add(annie);
//        users.add(brandon);
//        users.add(anna);
//        users.add(tingting);
//        Item book = new Item("book");
//        Item book2 = new Item("bookbook");
//        Item textbook = new Item("textbook");
//
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
//        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
//        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
//        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
//        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
//        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
//        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
//        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
//        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
//
//        List<UUID> itemList = new ArrayList<>();
//        itemList.add(book2.getId());
//        itemList.add(textbook.getId());
//
//        annie.addToWishlist(book.getId());
//        annie.addToWishlist(book2.getId());
//        annie.addToWishlist(textbook.getId());
//
//        assertEquals(true, annie.removeFromWishlist(book.getId()));
//        assertEquals(2, annie.getWishlist().size());
//        assertEquals(itemList, annie.getWishlist());
//        assertEquals(false, annie.removeFromWishlist(book.getId()));
//
//    }
//
//}