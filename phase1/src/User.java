import java.util.List;

/**
 * Represents a User in the trading system
 */

public class User {
    private final String username;
    private final String password;
    private final String email;
    // private final int userId; I'll add an id counter once UserManager is done!
    private List<Transaction> tradeHistory;
    private List<Item> inventory;
    private int numItemsLended = 0;
    private int numItemsBorrowed = 0;
    private int threshold = 1;
    private List<Item> wishlist;
    private String status = "active";

    /**
     * Constructs an instance of User based on Strings of username, password, and email.
     * @param username online identifier of a User
     * @param password account password
     * @param email User's email address
     */
     public User(String username, String password, String email) {
         this.username = username;
         this.password = password;
         this.email = email;
         // TO-DO: set user id String.valueOf(idCounter++);
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
     * Getter for email as a String
     * @return email as a String
     */
    public String getEmail() { return email; }

    /**
     * Getter for user id as an int
     * @return userid as an integer
     */
    public int getUserId() { return userId; }

    /**
     * Getter for this User's tradeHistory as list of Transactions they have previously been involved with.
     * @return list of Transaction objects
     */
    public List<Transaction> getTradeHistory() { return tradeHistory; }

    /**
     * Setter for this User's tradeHistory.
     * @param tradeHistory list of Transactions
     */
    public void setTradeHistory(List<Transaction> tradeHistory) { this.tradeHistory = tradeHistory; }

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
     * Getter for numItemsLended as an int
     * @return numItemsLended as an integer
     */
    public int getNumItemsLended() { return numItemsLended; }

    /**
     * Setter for numItemsLended, increases by 1 every time it is called
     */
    public void setNumItemsLended() { this.numItemsLended++; }

    /**
     * Getter for numItemsBorrowed
     * @return numItemsBorrowed as an integer
     */
    public int getNumItemsBorrowed() { return numItemsBorrowed; }

    /**
     * Setter for numItemsBorrowed, increases by 1 every time it is called
     */
    public void setNumItemsBorrowed() { this.numItemsBorrowed++; }

    /**
     * Getter for the minimum number of Items that this User has to have lent before they can borrow an Item.
     * @return threshold as an integer
     */
    public int getThreshold() { return threshold; }

    /**
     * Setter for the minimum number of Items that this User has to have lent before they can borrow an Item.
     * @param threshold as an integer
     */
    public void setThreshold(int threshold) { this.threshold = threshold; }

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
    public String toString() { return username + ", " + userId; }

}
