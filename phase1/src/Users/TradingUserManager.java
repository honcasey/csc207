package Users;

import Exceptions.InvalidTradingUserException;
import Items.Item;
import Transactions.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class manages all TradingUsers.
 */
public class TradingUserManager {
    private final List<TradingUser> allTradingUsers;
    private final List<TradingUser> flaggedAccounts;
    private final List<TradingUser> frozenAccounts;
    private Map<UUID, TradingUser> idToUser;

    /**
     * Creates a list of tradingUsers.
     */
    public TradingUserManager(List<TradingUser> tradingUsers, List<TradingUser> flaggedAccounts, List<TradingUser> frozenAccounts) {
        allTradingUsers = tradingUsers;
        this.flaggedAccounts = flaggedAccounts;
        this.frozenAccounts = frozenAccounts;
        userListToMap();
    }

    /**
     * Adds a new user with given info.
     *
     * @param username online identifier of a Users.TradingUser
     * @param password account password
     * @return username and userId as string separated by comma.
     */
    public TradingUser addTradingUser(String username, String password) throws InvalidTradingUserException {
        TradingUser newTradingUser = new TradingUser(username, password);
        if (allTradingUsers.size() == 0) {
            allTradingUsers.add(newTradingUser);
            return newTradingUser;
        }
        if (checkAvailableUsername(username)) {
            allTradingUsers.add(newTradingUser);
            return newTradingUser;
        } else {
            throw new InvalidTradingUserException();
        }
    }

    /**
     * Retrieve a specific user by username.
     *
     * @param username online identifier of a Users.TradingUser
     * @return username and userId as string separated by comma
     */
    public TradingUser getTradingUser(String username) throws InvalidTradingUserException {
        for (TradingUser tradingUser : allTradingUsers) {
            if ((tradingUser.getUsername().equals(username))) {
                return tradingUser;
            }
        }
        throw new InvalidTradingUserException();
    }

    /**
     * Adds an item to tradingUser's specified list, which is either the Users.TradingUser's wishlist or inventory.
     *
     * @param tradingUser     the tradingUser
     * @param item     An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void addItem(TradingUser tradingUser, Item item, String listType) {
        if (listType.equals("wishlist")) {
            tradingUser.getWishlist().add(item.getId());
        } else if (listType.equals("inventory")) {
            tradingUser.getInventory().add(item.getId());
        }
    }

    /**
     * Removes a item from tradingUser's specified list, which is either the Users.TradingUser's wishlist or inventory.
     *
     * @param tradingUser    A tradingUser in the trading system.
     * @param item     An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void removeItem(TradingUser tradingUser, Item item, String listType) {
        if (listType.equals("wishlist")) {
            tradingUser.getWishlist().remove(item.getId());
        } else if (listType.equals("inventory")) {
            tradingUser.getInventory().remove(item.getId());
        }
    }

    /**
     * Changes the tradingUser's specified threshold.
     *
     * @param tradingUser  A tradingUser in the trading system.
     * @param thresholdValue new value of threshold as an int
     * @param thresholdType  either "borrow", "weekly", or "incomplete" as a String
     */
    public void changeThreshold(TradingUser tradingUser, int thresholdValue, String thresholdType) {
        switch (thresholdType) {
            case "borrowThreshold":
                tradingUser.setBorrowThreshold(thresholdValue);
                break;
            case "weeklyThreshold":
                tradingUser.setWeeklyThreshold(thresholdValue);
                break;
            case "incompleteThreshold":
                tradingUser.setIncompleteThreshold(thresholdValue);
                break;
        }
    }

    /**
     * Changes the status of a tradingUser's account from active to frozen.
     *
     * @param tradingUser A tradingUser in the trading system.
     */
    public void freezeAccount(TradingUser tradingUser) {
        tradingUser.setStatus("frozen");
        idToUser.get(tradingUser.getUserId()).setStatus("frozen");
    }

    /**
     * Changes the status of a tradingUser's account from frozen to active.
     *
     * @param tradingUser A tradingUser in the trading system.
     */
    public void unfreezeAccount(TradingUser tradingUser) {
        tradingUser.setStatus("active");
    }

    /**
     * Adds a transaction to Users.TradingUser's transaction history.
     *
     * @param tradingUser A tradingUser in the trading system.
     * @param transaction a meetup between 2 users.
     */
    public void addToTransactionHistory(TradingUser tradingUser, Transaction transaction) {
        TransactionHistory tH = tradingUser.getTransactionHistory();
        tH.setTransactionHistory(transaction);
        updateTransactionHistoryValues(tradingUser, transaction);
    }

    /**
     * A private helper method for addToTransactionHistory that updates UserNumTradeTimes, NumItemsBorrowed, and NumItemsLended
     *
     * @param tradingUser        A tradingUser in a trading system
     * @param transaction a transaction between two Users
     */
    // TO-DO: consider splitting into two methods. Reasoning for having one method, user1 == tradingUser is needed for both updating the UserNumTradeTimes and NumItemsBorrowed, NumItemsLended
    private void updateTransactionHistoryValues(TradingUser tradingUser, Transaction transaction) {
        TransactionHistory tH = tradingUser.getTransactionHistory();
        if (transaction.getUser1() == tradingUser.getUserId()) {
            tradingUser.getTransactionHistory().setNumItemsLended();
            String u2 = idToUser.get(transaction.getUser2()).getUsername();
            if (tH.getUsersNumTradeTimes().containsKey(u2)) {
                tH.getUsersNumTradeTimes().put(u2, tH.getUsersNumTradeTimes().get(u2) + 1);
            } else {
                tH.getUsersNumTradeTimes().put(u2, 1);
            }
            if (!transaction.isOneWay()) {
                tradingUser.getTransactionHistory().setNumItemsBorrowed();
            }
        } else {
            tradingUser.getTransactionHistory().setNumItemsBorrowed();
            String u1 = idToUser.get(transaction.getUser1()).getUsername();
            if (tH.getUsersNumTradeTimes().containsKey(u1)) {
                tH.getUsersNumTradeTimes().put(u1, tH.getUsersNumTradeTimes().get(u1) + 1);
            } else {
                tH.getUsersNumTradeTimes().put(u1, 1);
                if (!transaction.isOneWay()) {
                    tradingUser.getTransactionHistory().setNumItemsLended();
                }
            }
        }
    }

    /**
     * Returns a list of all Users in the Trading System.
     * @return all users in the system.
     */
    public List<TradingUser> getAllTradingUsers() {
        return allTradingUsers;
    }

    /**
     * Returns whether this user with the given username and password is in the system or not.
     *
     * @param username the username of the specified user
     * @param password the password of the specified user
     * @return boolean if this user account is already in the system or not.
     */
    public boolean validUser(String username, String password) {
        for (TradingUser tradingUser : allTradingUsers) {
            if (tradingUser.getUsername().equals(username) && tradingUser.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of Users that have had their account flagged to be frozen automatically by the system
     *
     * @return list of flagged to be frozen users
     */
    public List<TradingUser> getFlaggedAccounts() {
        return flaggedAccounts;
    }

    /**
     * Retrieves a list of Users that have had their account frozen after approval by Admin.
     *
     * @return list of frozen users
     */
    public List<TradingUser> getFrozenAccounts() {
        return frozenAccounts;
    }

    /**
     * Returns of the number of current transactions of TradingUser exceed the incomplete transaction threshold
     *
     * @param tradingUser TradingUser of interest
     * @return boolean
     */
    public boolean incompleteTransactionExceeded(TradingUser tradingUser) {
        return tradingUser.getCurrentTransactions().size() >= tradingUser.getIncompleteThreshold();
    }

    /**
     * Takes Transaction and moves it from TradingUser's currentTransactions to their TransactionHistory.
     *
     * @param transaction The Transaction being moved.
     * @param tradingUser A TradingUser involved in the Transaction.
     */
    public void moveTransactionToTransactionHistory(Transaction transaction, TradingUser tradingUser) {
        String status = transaction.getStatus();
        if (status.equals("incomplete") || status.equals("complete") || status.equals("neverReturned")) {
            UUID id = transaction.getId();
            tradingUser.getCurrentTransactions().remove(id);
            addToTransactionHistory(tradingUser, transaction);
        }
    }

    /**
     * Checks whether the input username is valid.
     *
     * @param username online identifier of a Users.TradingUser
     * @return True or False as boolean
     */
    public boolean checkAvailableUsername(String username) {
        for (TradingUser user : allTradingUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private void userListToMap() {
        idToUser = new HashMap<>();
        for (TradingUser user : allTradingUsers) {
            idToUser.put(user.getUserId(), user);
        }
    }

    /**
     * Retrieves tradingUser by userId
     * @param id id of tradingUser
     * @return a tradingUser
     */
    public TradingUser getTradingUserById(UUID id) {
        return idToUser.get(id);
    }

    protected void handleConfirmedTransaction(Transaction newlyConfirmed){
        if(newlyConfirmed.getStatus().equals("Confirmed")){
            List<UUID> itemidlist = newlyConfirmed.getTransactionItems();
            TradingUser user1 = this.getTradingUserById(newlyConfirmed.getUser1());
            TradingUser user2 = this.getTradingUserById(newlyConfirmed.getUser2());
            if(itemidlist.size()==2){
                user1.removeFromWishlist(itemidlist.get(1));
                user2.removeFromWishlist(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(0));
                user2.getInventory().remove(itemidlist.get(1));
            }
            else if(itemidlist.size() == 1){
                user2.removeFromWishlist(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(1));
            }
        }
    }

    boolean borrowThresholdExceeded(TradingUser tradingUser){
    int numBorrowed = tradingUser.getTransactionHistory().getNumItemsBorrowed();
    int numLent = tradingUser.getTransactionHistory().getNumItemsLended();
    int threshold = tradingUser.getBorrowThreshold();

    return numBorrowed - numLent >= threshold;
    }
}
