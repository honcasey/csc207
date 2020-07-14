package Transactions;

import Items.Item;
import Users.TradingUser;
import Users.TradingUserManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class PastTransactionManagerTest {

    @Test
    void numTransactionsInWeekWithTransactionsOnlyInWeek(){
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
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

        assertEquals(3, ptm.numTransactionsInWeek(annie));
    }

    @Test
    void numTransactionsInWeekWithTransactionsOutSideWeek1(){
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
        Item textbook2 = new Item("textbooktextbook");

        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Meeting meeting10 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,4));
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
        Meeting meeting20 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,4));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
        TransactionOneWayTemp tran0 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook2.getId(), meeting10, meeting20, textbook2.getName());
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());

        tum.addToTransactionHistory(annie, tran0);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran0);
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

        assertEquals(3, ptm.numTransactionsInWeek(annie));
    }
    @Test
    void numTransactionsInWeekWithTransactionsOutSideWeek2(){
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
        Item textbook2 = new Item("textbooktextbook");

        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Meeting meeting10 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,4));
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,5));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,6));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
        Meeting meeting20 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,4));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,5));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,6));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
        TransactionOneWayTemp tran0 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook2.getId(), meeting10, meeting20, textbook2.getName());
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());

        tum.addToTransactionHistory(annie, tran0);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran0);
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

        assertEquals(1, ptm.numTransactionsInWeek(annie));
    }


    @Test
    void weeklyThresholdExceededInsideWeek(){
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
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

        assertTrue(ptm.weeklyThresholdExceeded(annie));
    }

    @Test
    void thresholdExceededOutsideWeek1(){
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
        Item textbook2 = new Item("textbooktextbook");

        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,1));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,2));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,3));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,1));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,2));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,3));
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

        assertFalse(ptm.weeklyThresholdExceeded(annie));

    }
    @Test
    void thresholdExceededOutsideWeek2(){
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
        Item textbook2 = new Item("textbooktextbook");

        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Meeting meeting10 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,4));
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,13));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,14));
        Meeting meeting20 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,4));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,12));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,13));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,14));
        TransactionOneWayTemp tran0 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook2.getId(), meeting10, meeting20, textbook2.getName());
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21, book.getName());
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book2.getId(), meeting12, meeting22, book2.getName());
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting13, meeting23, textbook.getName());

        tum.addToTransactionHistory(annie, tran0);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(annie, tran3);
        Map<UUID, Transaction> transactionMap = new HashMap<>();
        transactionMap.put(tran1.getId(), tran0);
        transactionMap.put(tran1.getId(), tran1);
        transactionMap.put(tran2.getId(), tran2);
        transactionMap.put(tran3.getId(), tran3);
        PastTransactionManager ptm = new PastTransactionManager(transactionMap);

       assertTrue(ptm.weeklyThresholdExceeded(annie));
    }

}