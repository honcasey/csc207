package Users;

import Items.Item;
import Transactions.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Users.User in the trading system
 */

public class User implements Serializable {
    private String username;
    private String password;
    private final UUID userId = UUID.randomUUID();
    private TransactionHistory transactionHistory;
    private List<UUID> currentTransactions;
    private List<Item> inventory;
    private int borrowThreshold = 1;
    private int weeklyThreshold = 3;
    private int incompleteThreshold = 2;
    private List<Item> wishlist;
    private String status = "active";

    /**
     * Constructs an instance of Users.User based on Strings of username, password, and email.
     * @param username online identifier of a Users.User(
     * @param password account password
     */
     public User(String username, String password) {
         this.username = username;
         this.password = password;
         currentTransactions = new ArrayList<>();
     }

    /**
     * Getter for username as a String
     * @return username as a String
     */
    public String getUsername() { return username; }

    /**
     * Getter for password as a String
     * @return password as a String
     */
    public String getPassword() { return password; }

    /**
     * Getter for user id as a UUID
     * @return userid as a UUID
     */
    public UUID getUserId() { return userId; }

    /**
     * Getter for this Users.User's transactionHistory as list of Transactions they have previously been involved with.
     * @return list of Transactions.Transaction objects
     */
    public TransactionHistory getTransactionHistory() { return transactionHistory; }

    /**
     * Setter for this Users.User's tradeHistory.
     * @param transactionHistory list of Transactions
     */
    public void setTransactionHistory(TransactionHistory transactionHistory) { this.transactionHistory = transactionHistory; }

    /**
     * Getter for this Users.User's TransactionDetails.
     * @return list of Transactions.Transaction objects
     */
    public List<UUID> getCurrentTransactions() { return currentTransactions; }

    /**
     * Getter for this Users.User's inventory as a list of (approved) Items.
     * @return list of Items
     */
    public List<Item> getInventory() { return inventory; }

    /**
     * Setter for this Users.User's inventory.
     * @param inventory list of Items
     */
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }

    /**
     * Getter for this Users.User's wishlist as a list of Items.
     * @return list of Items
     */
    public List<Item> getWishlist() { return wishlist; }

    /**
     * Setter for this Users.User's wishlist.
     * @param wishlist list of Items
     */
    public void setWishlist(List<Item> wishlist) { this.wishlist = wishlist; }

    /**
     * Getter for the minimum number of Items that this Users.User has to have lent before they can borrow an Items.Item.
     * @return borrowThreshold as an integer
     */
    public int getBorrowThreshold() { return borrowThreshold; }

    /**
     * Setter for the minimum number of Items that this Users.User has to have lent before they can borrow an Items.Item.
     * @param borrowThreshold as an integer
     */
    public void setBorrowThreshold(int borrowThreshold) { this.borrowThreshold = borrowThreshold; }

    /**
     * Getter for maximum number of Transactions that this Users.User can make in a given week.
     * @return weeklyThreshold as an integer
     */
    public int getWeeklyThreshold() { return weeklyThreshold; }

    /**
     * Setter for maximum number of Transactions that this Users.User can make in a given week.
     * @param weeklyThreshold as an integer
     */
    public void setWeeklyThreshold(int weeklyThreshold) { this.weeklyThreshold = weeklyThreshold; }

    /**
     * Getter for maximum number of incomplete Transactions that this Users.User can make before their account is frozen.
     * @return incompleteThreshold as an integer
     */
    public int getIncompleteThreshold() { return incompleteThreshold; }

    /**
     * Setter for maximum number of incomplete Transactions that this Users.User can make before their account is frozen.
     * @param incompleteThreshold as an integer
     */
    public void setIncompleteThreshold(int incompleteThreshold) { this.incompleteThreshold = incompleteThreshold; }

    /**
     * Getter for the current Users.User's account status, which can be active or frozen.
     * @return status as a String
     */
    public String getStatus() { return status; }

    /**
     * Setter for the current Users.User's account status, which can be active or frozen.
     * @param status as a String
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Represents the current Users.User by their username and userId
     * @return the username and userid separated by a comma
     */
    @Override
    public String toString() { return username + ", " + userId; }

    public boolean isFrozen(){
        return status.equals("frozen");
    }
    
}



