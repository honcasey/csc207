import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Represents a User in the trading system
 */

public class User implements Serializable {
    private String username;
    private String password;
    private final UUID userId = UUID.randomUUID();
    private TransactionHistory transactionHistory;
    private TransactionDetails transactionDetails;
    private List<Item> inventory;
    private int borrowThreshold = 1;
    private int weeklyThreshold = 3;
    private int incompleteThreshold = 2;
    private List<Item> wishlist;
    private String status = "active";

    /**
     * Constructs an instance of User based on Strings of username, password, and email.
     * @param username online identifier of a User
     * @param password account password
     */
     public User(String username, String password) {
         this.username = username;
         this.password = password;
     }

    /**
     * Getter for username as a String
     * @return username as a String
     */
    public String getUsername() { return username; }

    /**
     * Setter for username as a String
     * @param username new username as a String
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Getter for password as a String
     * @return password as a String
     */
    public String getPassword() { return password; }

    /**
     * Setter for password as a String
     * @param password new password as a String
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Getter for user id as a UUID
     * @return userid as a UUID
     */
    public UUID getUserId() { return userId; }

    /**
     * Getter for this User's transactionHistory as list of Transactions they have previously been involved with.
     * @return list of Transaction objects
     */
    public TransactionHistory getTransactionHistory() { return transactionHistory; }

    /**
     * Setter for this User's tradeHistory.
     * @param transactionHistory list of Transactions
     */
    public void setTransactionHistory(TransactionHistory transactionHistory) { this.transactionHistory = transactionHistory; }

    /**
     * Getter for this User's TransactionDetails.
     * @return list of Transaction objects
     */
    public TransactionDetails getTransactionDetails() { return transactionDetails; }

    /**
     * Setter for this User's TransactionDetails.
     * @param transactionDetails list of Transactions
     */
    public void setTransactionDetails(TransactionDetails transactionDetails) {this.transactionDetails = transactionDetails; }

    /**
     * Getter for this User's inventory as a list of (approved) Items.
     * @return list of Items
     */
    public List<Item> getInventory() { return inventory; }

    /**
     * Setter for this User's inventory.
     * @param inventory list of Items
     */
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }

    /**
     * Getter for this User's wishlist as a list of Items.
     * @return list of Items
     */
    public List<Item> getWishlist() { return wishlist; }

    /**
     * Setter for this User's wishlist.
     * @param wishlist list of Items
     */
    public void setWishlist(List<Item> wishlist) { this.wishlist = wishlist; }

    /**
     * Getter for the minimum number of Items that this User has to have lent before they can borrow an Item.
     * @return borrowThreshold as an integer
     */
    public int getBorrowThreshold() { return borrowThreshold; }

    /**
     * Setter for the minimum number of Items that this User has to have lent before they can borrow an Item.
     * @param borrowThreshold as an integer
     */
    public void setBorrowThreshold(int borrowThreshold) { this.borrowThreshold = borrowThreshold; }

    /**
     * Getter for maximum number of Transactions that this User can make in a given week.
     * @return weeklyThreshold as an integer
     */
    public int getWeeklyThreshold() { return weeklyThreshold; }

    /**
     * Setter for maximum number of Transactions that this User can make in a given week.
     * @param weeklyThreshold as an integer
     */
    public void setWeeklyThreshold(int weeklyThreshold) { this.weeklyThreshold = weeklyThreshold; }

    /**
     * Getter for maximum number of incomplete Transactions that this User can make before their account is frozen.
     * @return incompleteThreshold as an integer
     */
    public int getIncompleteThreshold() { return incompleteThreshold; }

    /**
     * Setter for maximum number of incomplete Transactions that this User can make before their account is frozen.
     * @param incompleteThreshold as an integer
     */
    public void setIncompleteThreshold(int incompleteThreshold) { this.incompleteThreshold = incompleteThreshold; }

    /**
     * Getter for the current User's account status, which can be active or frozen.
     * @return status as a String
     */
    public String getStatus() { return status; }

    /**
     * Setter for the current User's account status, which can be active or frozen.
     * @param status as a String
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Represents the current User by their username and userId
     * @return the username and userid separated by a comma
     */
    @Override
    public String toString() { return username + ", " + userId; }

}



