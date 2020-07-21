import Items.Item;
import Transactions.Meeting;
import Users.TradingUser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import Transactions.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    public TradingUser casey = new TradingUser("caseyh", "pwd123");
    public TradingUser annie = new TradingUser("anniel", "pwd456");
    public Item book = new Item("book");
    public Item pencil = new Item("pencil");
    public HashMap<UUID, Transaction> allTransactionList = new HashMap<UUID, Transaction>();
    public CurrentTransactionManager currManager = new CurrentTransactionManager(allTransactionList);
    public LocalDate date = LocalDate.of(2020, 05, 02);
    public LocalTime localtime =  LocalTime.of(11, 00, 00);
    public Meeting meeting1 = new Meeting("uoft", localtime, date);
    public Meeting meeting2 = currManager.meetOneMonthLater(meeting1);


// ONE WAY PERM TESTS

//    @Test
//    void testCreateTransaction() {
//        Transaction onewayperm =  currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, meeting1);
//        Transaction onewaytemp = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, meeting1, meeting2);
//        Transaction twowayperm = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, pencil, meeting1);
//        Transaction twowaytemp = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, pencil, meeting1, meeting2);
//        assertTrue(onewayperm instanceof TransactionOneWayPerm);
//        assertTrue(onewaytemp instanceof TransactionOneWayTemp);
//        assertTrue(twowayperm instanceof TransactionPerm);
//        assertTrue(twowaytemp instanceof TransactionTemp);
//        System.out.println(onewayperm.toString());
//        System.out.println(onewaytemp.toString());
//        System.out.println(twowayperm);
//        System.out.println(twowaytemp);
//    }
//
//    @Test
//    void testAutomaticStatusUpdates() {
//        Transaction onewayperm =  currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, meeting1);
//        Transaction onewaytemp = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, meeting1, meeting2);
//        Transaction twowayperm = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, pencil, meeting1);
//        Transaction twowaytemp = currManager.createTransaction(casey.getUserId(), annie.getUserId(), book, pencil, meeting1, meeting2);
//        onewayperm.setStatusUser1("confirm");
//        onewaytemp.setStatusUser1("confirm");
//        twowayperm.setStatusUser1("confirm");
//        twowaytemp.setStatusUser1("confirm");
//
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);
//            assertEquals("pending", transaction.getStatus());
//        }
//        onewayperm.setStatusUser2("confirm");
//        onewaytemp.setStatusUser2("confirm");
//        twowayperm.setStatusUser2("confirm");
//        twowaytemp.setStatusUser2("confirm");
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);
//            assertEquals("confirmed", transaction.getStatus());
//        }
//        onewayperm.setStatusUser1("incomplete");
//        onewaytemp.setStatusUser1("incomplete");
//        twowayperm.setStatusUser1("incomplete");
//        twowaytemp.setStatusUser1("incomplete");
//        for (Transaction transaction : allTransactionList.values()){
//            currManager.updateStatus(transaction);
//            assertEquals("incomplete", transaction.getStatus());}
//        onewayperm.setStatus("confirmed");
//        onewaytemp.setStatus("confirmed");
//        twowayperm.setStatus("confirmed");
//        twowaytemp.setStatus("confirmed");
//
//        onewayperm.setStatusUser1("traded");
//        onewaytemp.setStatusUser1("traded");
//        twowayperm.setStatusUser1("traded");
//        twowaytemp.setStatusUser1("traded");
//
//        onewayperm.setStatusUser2("traded");
//        onewaytemp.setStatusUser2("traded");
//        twowayperm.setStatusUser2("traded");
//        twowaytemp.setStatusUser2("traded");
//
//
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);}
//        assertEquals("complete", onewayperm.getStatus());
//        assertEquals("complete", twowayperm.getStatus());
//        assertEquals("traded", twowaytemp.getStatus());
//        assertEquals("traded", onewaytemp.getStatus());
//
//        twowayperm.setStatusUser1("returned");
//        twowaytemp.setStatusUser1("returned");
//
//
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);}

//        assertEquals("traded", twowayperm.getStatus());
//        assertEquals("traded", twowaytemp.getStatus());
//
//        twowayperm.setStatusUser2("neverReturned");
//        twowaytemp.setStatusUser2("neverReturned");
//
//
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);}
//
//        assertEquals("neverReturned", twowayperm.getStatus());
//        assertEquals("neverReturned", twowaytemp.getStatus());
//
//        twowayperm.setStatusUser1("returned");
//        twowaytemp.setStatusUser1("returned");
//
//
//        for (Transaction transaction: allTransactionList.values()){
//            currManager.updateStatus(transaction);}
//        assertEquals("returned", twowayperm.getStatus());
//        assertEquals("returned", twowaytemp.getStatus());

    }


}