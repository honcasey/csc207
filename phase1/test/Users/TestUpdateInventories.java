package Users;

import Items.Item;

import Transactions.Meeting;
import Transactions.Transaction;

import Transactions.TransactionTwoWayPerm;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestUpdateInventories {

    @Test
    public void testUpdateInventory() {
        // create the trading user manager
        List<TradingUser> users = new ArrayList<>();
        List<TradingUser> flagged = new ArrayList<>();
        List<TradingUser> frozen = new ArrayList<>();

        // create new users
        TradingUser casey = new TradingUser("caseyh", "pwd123");
        TradingUser annie = new TradingUser("anniel", "pwd456");
        TradingUser brandon = new TradingUser("brandont", "password123");
        TradingUser anna = new TradingUser("annas", "password456");
        TradingUser tingting = new TradingUser("tingtingz", "pw123");

        // add users to list of all users
        users.add(casey);
        users.add(annie);
        users.add(brandon);
        users.add(anna);
        users.add(tingting);

        // create a trading user manager
        TradingUserManager tum = new TradingUserManager(users, flagged, frozen);

        // create new items
        Item book = new Item("book");
        Item textbook = new Item("textbook");

        // add items to users inventories
        casey.getInventory().add(book.getId()); // casey has book
        annie.getInventory().add(textbook.getId()); // annie has textbook

        // create a meeting and transaction
        Meeting meeting = new Meeting("uoft",
                LocalTime.of(10,30,30), LocalDate.of(2020,6,12));
        Transaction transaction = new TransactionTwoWayPerm(casey.getUserId(), annie.getUserId(),
                book.getId(), textbook.getId(), meeting, book.getName(), textbook.getName());

        // set the transaction's status to complete
        transaction.setStatus("completed");

        // add items to each others wishlist
        casey.getWishlist().add(textbook.getId()); // casey wants textbook
        annie.getWishlist().add(book.getId()); // annie wants book

        // call method to update wishlists and inventory
        tum.handlePermTransactionItems(transaction);

        assertFalse(casey.getInventory().contains(textbook.getId())); // casey has nothing now
        assertFalse(annie.getInventory().contains(book.getId())); // annie has nothing now
        assertFalse(casey.getWishlist().contains(textbook.getId())); // casey doesn't want anything now
        assertFalse(annie.getWishlist().contains(book.getId())); // annie doesn't want anything now
    }
}
