//import org.junit.jupiter.api.Test;
//import Transaction.Transaction;
//import Users.TradingUser;
//import Users.TradingUserManager;
//import Transactions.PastTransactionManager;
//import Transactions.TransactionManager;
//import Transactions.CurrentTransactionManager;
//
//
//public class TransactionHistoryTest {
////    List<TradingUser> users = new List<TradingUser>();
////    List<TradingUser> flagged = new List<TradingUser>();
////    List<Trading> frozen = new List<TradingUser>();
////    TradingUser casey = new TradingUser("caseyh", "pwd123");
////    TradingUser annie = new TradingUser("anniel", "pwd456");
////    users.add(casey);
////    users.add(annie);
////    TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
////    Item book = new Item("book");
////    Item notes = new Item("notes");
////    Item textbook = new Item("textbook");
////    List<Item> itemList = new List<>(book, notes, textbook);
//
//    @Test
//    void mostRecentOneWayTransactionsLessThan3() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item notes = new Item("notes");
//        Item textbook = new Item("textbook");
//        List<Item> itemList = new List<>(book, notes, textbook);
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey, annie, book, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(tran, annie.getTransactionHistory().mostRecentOneWayTransactions());
//        assertEquals(tran, casey.getTransactionHistory().mostRecentOneWayTransactions());
//
//    }
//    @Test
//    void mostRecentOneWayTransactionsMoreThan3() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item textbook = new Item("textbook");
//        Item notes = new Item("notes");
//        Item lt = new Item("laptop");
//        Meeting meeting11 = new Meeting("uoft", 2020, 6, 25, 10, 30);
//        Meeting meeting12 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting13 = new Meeting("uoft", 2020, 6, 26, 10, 30);
//        Meeting meeting14 = new Meeting("uoft", 2020, 6, 27, 10, 30);
//        Meeting meeting21 = new Meeting("gerstein", 2020, 7, 25, 10, 30);
//        Meeting meeting22 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        Meeting meeting23 = new Meeting("gerstein", 2020, 7, 26, 10, 30);
//        Meeting meeting24 = new Meeting("gerstein", 2020, 7, 27, 10, 30);
//        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey, annie, book, meeting11, meeting21);
//        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey, annie, textbook, meeting12, meeting22);
//        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey, annie, notes, meeting13, meeting23);
//        TransactionOneWayTemp tran4 = new TransactionOneWayTemp(casey, annie, lt, meeting14, meeting24);
//        tum.addToTransactionHistory(annie, tran1);
//        tum.addToTransactionHistory(casey, tran1);
//        tum.addToTransactionHistory(annie, tran2);
//        tum.addToTransactionHistory(casey, tran2);
//        tum.addToTransactionHistory(annie, tran3);
//        tum.addToTransactionHistory(casey, tran3);
//        tum.addToTransactionHistory(annie, tran4);
//        tum.addToTransactionHistory(casey, tran4);
//        assertEquals(3, annie.getTransactionHistory().mostRecentOneWayTransactions().size());
//        assertEquals(3, casey.getTransactionHistory().mostRecentOneWayTransactions().size());
//
//    }
//    @Test
//    void mostRecentTwoWayTransactions() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item book2 = new Item("hamlet");
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(tran, annie.getTransactionHistory().mostRecentOneWayTransactions());
//        assertEquals(tran, casey.getTransactionHistory().mostRecentOneWayTransactions());
//
//    }
//
//    @Test
//    void mostTradedWithUsers() {
//    }
//
//    @Test
//    void getOneWayTransactions() {
//    }
//
//    @Test
//    void getTwoWayTransactions() {
//    }
//
//    @Test
//    void getUsersNumTradeTimes() {
//    }
//
//    @Test
//    void getNumTransactions() {
//    }
//
//    @Test
//    void getNumItemsLended() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item book2 = new Item("hamlet");
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(1, annie.getNumItemsLended());
//        assertEquals(1, casey.getNumItemsLended());
//    }
//
//    @Test
//    void getNumItemsBorrowed() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item book2 = new Item("hamlet");
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(1, annie.getNumItemsBorrowed());
//        assertEquals(1, casey.getNumItemsBorrowed());
//    }
//
//    @Test
//    void addToTransactionHistoryOneWay() {
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item notes = new Item("notes");
//        Item textbook = new Item("textbook");
//        List<Item> itemList = new List<>(book, notes, textbook);
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey, annie, book, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(1, annie.getOneWayTransactions().size());
//        assertEquals(1, casey.getOneWayTransactions().size());
//    }
//    @Test
//    void addToTransactionHistoryTwoWay(){
//        List<TradingUser> users = new List<TradingUser>();
//        List<TradingUser> flagged = new List<TradingUser>();
//        List<Trading> frozen = new List<TradingUser>();
//        TradingUser casey = new TradingUser("caseyh", "pwd123");
//        TradingUser annie = new TradingUser("anniel", "pwd456");
//        users.add(casey);
//        users.add(annie);
//        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//        Item book = new Item("book");
//        Item book2 = new Item("hamlet");
//        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
//        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
//        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
//        tum.addToTransactionHistory(annie, tran);
//        tum.addToTransactionHistory(casey, tran);
//        assertEquals(1, annie.getTwoWayTransactions().size());
//        assertEquals(1, casey.getTwoWayTransactions().size());
//    }
//}
