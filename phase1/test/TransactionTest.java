import Items.Item;
import Transactions.*;
import Users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    // ONE WAY PERM TESTS
    @Test
    void testTransactionOneWayPerm() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran = new TransactionOneWayPerm(casey, annie, book, meeting);

    }

    @Test
    void getStatus() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran = new TransactionOneWayPerm(casey, annie, book, meeting);
        tran.setStatus("confirmed");
        assertEquals(tran.getStatus(), "confirmed");
    }

    @Test
    void getUser1() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran = new TransactionOneWayPerm(casey, annie, book, meeting);
        assertEquals(tran.getUser1(), casey);
    }

    @Test
    void getUser2() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran = new TransactionOneWayPerm(casey, annie, book, meeting);
        assertEquals(tran.getUser2(), annie);
    }

    @Test
    void getFirstMeeting() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Transaction tran = new TransactionOneWayPerm(casey, annie, book, meeting);
        assertEquals(tran.getFirstMeeting(), meeting);
    }

    @Test
    void getItemOneWayPerm() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting = new Meeting("uoft", 2020, 6, 30, 10, 30);
        TransactionOneWayPerm tran = new TransactionOneWayPerm(casey, annie, book, meeting);
        assertEquals(tran.getItem(), book);
    }

    // ONE WAY TEMP TESTS
    @Test
    void testTransactionOneWayTemp() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("uoft", 2020, 7, 30, 10, 30);
        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey, annie, book, meeting1, meeting2);

}

    @Test
    void getItem() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey, annie, book, meeting1, meeting2);
        assertEquals(tran.getItem(), book);
        Item book2 = new Item("hamlet");
        tran.setItem1(book2);
        assertEquals(tran.getItem(), book2);
    }

    @Test
    void getSecondMeeting() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionOneWayTemp tran = new TransactionOneWayTemp(casey, annie, book, meeting1, meeting2);
        assertEquals(tran.getSecondMeeting(), meeting2);
        Meeting meeting3 = new Meeting("robarts", 2020, 7, 30, 10, 30);
        tran.setSecondMeeting(meeting3);
        assertEquals(tran.getSecondMeeting(), meeting3);
    }

    // TWO WAY PERM TESTS
    @Test
    void testTransactionTwoWayPerm() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        TransactionTwoWayPerm tran = new TransactionTwoWayPerm(casey, annie, book, book2, meeting1);
    }

    @Test
    void getItem1() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        TransactionTwoWayPerm tran = new TransactionTwoWayPerm(casey, annie, book, book2, meeting1);
        assertEquals(tran.getItem1(), book);
    }

    @Test
    void getItem2() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        TransactionTwoWayPerm tran = new TransactionTwoWayPerm(casey, annie, book, book2, meeting1);
        assertEquals(tran.getItem2(), book2);
    }

    // TWO WAY TEMP TESTS
    @Test
    void testTransactionTwoWayTemp() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);

    }

    @Test
    void testGetItem1() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
        assertEquals(tran.getItem1(), book);

    }

    @Test
    void testGetItem2() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
        assertEquals(tran.getItem2(), book2);
    }

    @Test
    void testGetSecondMeeting() {
        User casey = new User("caseyh", "pwd123");
        User annie = new User("anniel", "pwd456");
        Item book = new Item("book");
        Item book2 = new Item("hamlet");
        Meeting meeting1 = new Meeting("uoft", 2020, 6, 30, 10, 30);
        Meeting meeting2 = new Meeting("gerstein", 2020, 7, 30, 10, 30);
        TransactionTwoWayTemp tran = new TransactionTwoWayTemp(casey, annie, book, book2, meeting1, meeting2);
        assertEquals(tran.getSecondMeeting(), meeting2);
    }

}