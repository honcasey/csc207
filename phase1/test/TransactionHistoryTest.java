import Transactions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import Users.TradingUser;
import Users.TradingUserManager;
import Items.Item;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TransactionHistoryTest {
//    List<TradingUser> users = new ArrayList<>();
//    List<TradingUser> flagged = new ArrayList<>();
//    List<TradingUser> frozen = new ArrayList<>();
//    TradingUser casey = new TradingUser("caseyh", "pwd123");
//    TradingUser annie = new TradingUser("anniel", "pwd456");
//    TradingUser brandon = new TradingUser("brandont", "password123");
//    TradingUser anna = new TradingUser("annas", "password456");
//    TradingUser tingting = new TradingUser("tingtingz", "pw123");
//        users.add(casey);
//        users.add(annie);
//        users.add(brandon);
//        users.add(anna);
//        users.add(tingting);
//    TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
//    Item book = new Item("book");
//    Item book2 = new Item("bookbook");
//    Item textbook = new Item("textbook");
//    Item textbook2 = new Item("textbooktextbook");
//    Item notes = new Item("notes");
//    Item notes2 = new Item("notesnotes");
//    Item lt = new Item("laptop");
//    Item lt2 = new Item("laptoplaptop");
//    Item p = new Item("pencil");
//    Item p2 = new Item("pencilpencil");
//
//    Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
//    Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
//    Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
//    Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,27));
//    Meeting meeting15 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,26));
//    Meeting meeting16 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,25));
//    Meeting meeting17 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,24));
//    Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
//    Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
//    Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
//    Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,27));
//    Meeting meeting25 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,26));
//    Meeting meeting26 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,25));
//    Meeting meeting27 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,24));

    @Test
    void mostRecentOneWayTransactionsLessThan3() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item notes = new Item("notes");
        Item textbook = new Item("textbook");
        List<Item> itemList = new ArrayList<>();
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        List<UUID> transList = new ArrayList<>();
        transList.add(tran.getId());
        assertEquals(transList, annie.getTransactionHistory().mostRecentOneWayTransactions());
        assertEquals(transList, casey.getTransactionHistory().mostRecentOneWayTransactions());

    }
    @Test
    void mostRecentOneWayTransactionsMoreThan3() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item textbook = new Item("textbook");
        Item notes = new Item("notes");
        Item lt = new Item("laptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21);
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting12, meeting22);
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), meeting13, meeting23);
        TransactionOneWayTemp tran4 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        List<UUID> transList = new ArrayList<>();
        transList.add(tran2.getId());
        transList.add(tran3.getId());
        transList.add(tran4.getId());
        assertEquals(3, annie.getTransactionHistory().mostRecentOneWayTransactions().size());
        assertEquals(3, casey.getTransactionHistory().mostRecentOneWayTransactions().size());
        assertEquals(transList,annie.getTransactionHistory().mostRecentOneWayTransactions());
        assertEquals(transList,casey.getTransactionHistory().mostRecentOneWayTransactions());

    }
    @Test
    void mostRecentTwoWayTransactionsLessThan3() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        List<UUID> transList = new ArrayList<>();
        transList.add(tran.getId());
        assertEquals(transList, annie.getTransactionHistory().mostRecentTwoWayTransactions());
        assertEquals(transList, casey.getTransactionHistory().mostRecentTwoWayTransactions());

    }
    @Test
    void mostRecentTwoWayTransactionsMoreThan3() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionTwoWayTemp tran1 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        TransactionTwoWayTemp tran2 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), textbook2.getId(), meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        assertEquals(3, annie.getTransactionHistory().mostRecentTwoWayTransactions().size());
        assertEquals(3, casey.getTransactionHistory().mostRecentTwoWayTransactions().size());
        List<UUID> transList = new ArrayList<>();
        transList.add(tran2.getId());
        transList.add(tran3.getId());
        transList.add(tran4.getId());
        assertEquals(transList,annie.getTransactionHistory().mostRecentTwoWayTransactions());
        assertEquals(transList,casey.getTransactionHistory().mostRecentTwoWayTransactions());
    }

    @Test
    void mostTradedWithUsers1() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionTwoWayTemp tran1 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        TransactionTwoWayTemp tran2 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), textbook2.getId(), meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        List<String> usernamesList= new ArrayList<>();
        usernamesList.add(casey.getUsername());
        assertEquals(usernamesList,annie.getTransactionHistory().mostTradedWithUsers());
    }
    @Test
    void mostTradedWithUsersMoreThan1(){
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

        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,27));
        Meeting meeting15 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,26));
        Meeting meeting16 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,25));
        Meeting meeting17 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,6,24));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,27));
        Meeting meeting25 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,26));
        Meeting meeting26 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,25));
        Meeting meeting27 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,24));

        TransactionTwoWayTemp tran1 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        TransactionTwoWayTemp tran2 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), textbook2.getId(), meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(anna.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(anna.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        TransactionTwoWayTemp tran5 = new TransactionTwoWayTemp(brandon.getUserId(), annie.getUserId(), p.getId(),p2.getId(), meeting15, meeting25);
        TransactionTwoWayTemp tran6 = new TransactionTwoWayTemp(brandon.getUserId(), annie.getUserId(), p.getId(),p2.getId(), meeting16, meeting26);
        TransactionTwoWayTemp tran7 = new TransactionTwoWayTemp(tingting.getUserId(), annie.getUserId(), p.getId(),p2.getId(), meeting17, meeting27);


        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(anna, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(anna, tran4);
        tum.addToTransactionHistory(annie, tran5);
        tum.addToTransactionHistory(brandon, tran5);
        tum.addToTransactionHistory(annie, tran6);
        tum.addToTransactionHistory(brandon, tran6);
        tum.addToTransactionHistory(annie, tran7);
        tum.addToTransactionHistory(tingting, tran7);
        List<String> usernamesList= new ArrayList<>();
        usernamesList.add(brandon.getUsername());
        usernamesList.add(casey.getUsername());
        usernamesList.add(anna.getUsername());
        annie.getTransactionHistory().mostTradedWithUsers();
        assertEquals(usernamesList,annie.getTransactionHistory().mostTradedWithUsers());
        assertEquals(3,annie.getTransactionHistory().mostTradedWithUsers().size());

    }

    @Test
    void getOneWayTransactions() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item textbook = new Item("textbook");
        Item notes = new Item("notes");
        Item lt = new Item("laptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21);
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), meeting12, meeting22);
        TransactionOneWayTemp tran3 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), meeting13, meeting23);
        TransactionOneWayTemp tran4 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        List<UUID> transList = new ArrayList<>();
        transList.add(tran1.getId());
        transList.add(tran2.getId());
        transList.add(tran3.getId());
        transList.add(tran4.getId());
        assertEquals(4, annie.getTransactionHistory().getOneWayTransactions().size());
        assertEquals(4, casey.getTransactionHistory().getOneWayTransactions().size());
        assertEquals(transList,annie.getTransactionHistory().getOneWayTransactions());
        assertEquals(transList,casey.getTransactionHistory().getOneWayTransactions());

    }

    @Test
    void getTwoWayTransactions() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionTwoWayTemp tran1 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        TransactionTwoWayTemp tran2 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), textbook2.getId(), meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        assertEquals(4, annie.getTransactionHistory().getTwoWayTransactions().size());
        assertEquals(4, casey.getTransactionHistory().getTwoWayTransactions().size());
        List<UUID> transList = new ArrayList<>();
        transList.add(tran1.getId());
        transList.add(tran2.getId());
        transList.add(tran3.getId());
        transList.add(tran4.getId());
        assertEquals(transList,annie.getTransactionHistory().getTwoWayTransactions());
        assertEquals(transList,casey.getTransactionHistory().getTwoWayTransactions());
    }

    @Test
    void getUsersNumTradeTimes() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionTwoWayTemp tran1 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        TransactionTwoWayTemp tran2 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(), textbook2.getId(), meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        Map<String,Integer> userNum= new HashMap<>();
        userNum.put(casey.getUsername(), 4);
        assertEquals(userNum,annie.getTransactionHistory().getUsersNumTradeTimes());

    }

    @Test
    void getNumTransactions() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("bookbook");
        Item textbook = new Item("textbook");
        Item textbook2 = new Item("textbooktextbook");
        Item notes = new Item("notes");
        Item notes2 = new Item("notesnotes");
        Item lt = new Item("laptop");
        Item lt2 = new Item("laptoplaptop");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting12 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,29));
        Meeting meeting13 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,28));
        Meeting meeting14 = new Meeting("uoft", LocalTime.of(10,30,30),  LocalDate.of(2020,7,27));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        Meeting meeting22 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,29));
        Meeting meeting23 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        Meeting meeting24 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,28));
        TransactionOneWayTemp tran1 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21);
        TransactionOneWayTemp tran2 = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), textbook.getId(),  meeting12, meeting22);
        TransactionTwoWayTemp tran3 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), notes.getId(), notes2.getId(), meeting13, meeting23);
        TransactionTwoWayTemp tran4 = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), lt.getId(), lt2.getId(), meeting14, meeting24);
        tum.addToTransactionHistory(annie, tran1);
        tum.addToTransactionHistory(casey, tran1);
        tum.addToTransactionHistory(annie, tran2);
        tum.addToTransactionHistory(casey, tran2);
        tum.addToTransactionHistory(annie, tran3);
        tum.addToTransactionHistory(casey, tran3);
        tum.addToTransactionHistory(annie, tran4);
        tum.addToTransactionHistory(casey, tran4);
        assertEquals(4,casey.getTransactionHistory().getNumTransactions());
        assertEquals(4,annie.getTransactionHistory().getNumTransactions());
    }

    @Test
    void getNumItemsLended() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        assertEquals(1, annie.getTransactionHistory().getNumItemsLended());
        assertEquals(1, casey.getTransactionHistory().getNumItemsLended());
    }

    @Test
    void getNumItemsBorrowed() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        assertEquals(1, annie.getTransactionHistory().getNumItemsBorrowed());
        assertEquals(1, casey.getTransactionHistory().getNumItemsBorrowed());
    }

    @Test
    void addToTransactionHistoryOneWay() {
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item notes = new Item("notes");
        Item textbook = new Item("textbook");

        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        assertEquals(1, annie.getTransactionHistory().getOneWayTransactions().size());
        assertEquals(1, casey.getTransactionHistory().getOneWayTransactions().size());
    }
    @Test
    void addToTransactionHistoryTwoWay(){
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        users.add(casey);
        users.add(annie);
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting11 = new Meeting("uoft", LocalTime.of(10,30,30), LocalDate.of(2020,6,30));
        Meeting meeting21 = new Meeting("gerstein", LocalTime.of(10,30,30), LocalDate.of(2020,7,30));
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey.getUserId(), annie.getUserId(), book.getId(), book2.getId(), meeting11, meeting21);
        tum.addToTransactionHistory(annie, tran);
        tum.addToTransactionHistory(casey, tran);
        assertEquals(1, annie.getTransactionHistory().getTwoWayTransactions().size());
        assertEquals(1, casey.getTransactionHistory().getTwoWayTransactions().size());
    }

}
