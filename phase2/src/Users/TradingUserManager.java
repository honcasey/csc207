package Users;

import Exceptions.InvalidTradingUserException;
import Items.Item;
import Transactions.Statuses;
import Transactions.Transaction;

import java.util.*;

/**
 * <h1>TradingUserManager</h1>
 *
 * Manages all TradingUsers in the system.
 * <p>
 * Stores a list of all TradingUsers in the system, all flagged TradingUsers, all frozen Users, and a HashMap mapping between
 *  * TradingUsers and their UUID.
 * </p>
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
     * @param username online identifier of a TradingUser
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
     * Retrieves a specific user by username.
     *
     * @param username online identifier of a TradingUser
     * @return username and userId as string separated by comma
     */
    public TradingUser getTradingUser(String username) throws InvalidTradingUserException {
        for (TradingUser tradingUser : allTradingUsers) {
            if ((tradingUser.getUsername().equals(username))) {
                return tradingUser;
            }
        }
        throw new InvalidTradingUserException();// TradingUser does not exist
    }

    /**
     * Returns the user thresholds in a list given a username
     * @param username: the string username of the user
     * @return List of ints (borrowThreshold, weeklyThreshold, incompleteThreshold)
     */
    public List<Integer> getCurrThresholds(String username) {
            ArrayList<Integer> thresholdList = new ArrayList<Integer>();
            try {
                TradingUser tradingUser = getTradingUser(username);
                thresholdList.add(tradingUser.getBorrowThreshold());
                thresholdList.add(tradingUser.getWeeklyThreshold());
                thresholdList.add(tradingUser.getIncompleteThreshold());
                return thresholdList;
            }
            catch(InvalidTradingUserException e){
                return thresholdList;
        }
    }
    /**
     * Retrieves a list of TradingUsers by city.
     * @param city = desired city
     * @return list of TradingUsers in that city
     */
    public List<TradingUser> getTradingUserByCity(String city) {
        List<TradingUser> userList = new ArrayList<>();
        for (TradingUser tradingUser : allTradingUsers)
            //check TradingUser is in the desired city and not on vacation status
            if (sameCity(tradingUser.getCity(),city)&&(!tradingUser.isOnVacation())) {
                userList.add(tradingUser);
            }
        if (userList.size() == 0) return null; // if there are no TradingUser's in this city
        else { return userList; }
    }

    /**
     * Retreives of a list of TradingUsers that are outside of a city but still ready to trade.
     * @param city This is the disired city
     * @return list of TradingUsers outside of that city.
     */
    public List<TradingUser> getTradingUsersOutOfCity(String city){
        List<TradingUser> userList = new ArrayList<>();
        for (TradingUser tradingUser : allTradingUsers)
            //check TradingUser is in the desired city and not on vacation status
            if (!sameCity(tradingUser.getCity(),city)&&(!tradingUser.isOnVacation())) {
                userList.add(tradingUser);
            }
        if (userList.size() == 0) return null; // if there are no TradingUser's in this city
        else { return userList; }
    }

    private Boolean sameCity(String city1, String city2){
        String All_upper_city1 = city1.toUpperCase();
        String All_upper_city2 = city2.toUpperCase();
        return All_upper_city1.equals(All_upper_city2);
    }

    /**
     * Retrieves a list of TradingUsers not on vacation status.
     * @return list of TradingUsers not on vacation
     */
    public List<TradingUser> getTradingUserNotOnVacation(){
        List<TradingUser> userList = new ArrayList<>();
        for (TradingUser tradingUser : allTradingUsers){
            if (!tradingUser.getStatus().equals(UserStatuses.VACATION)){
                userList.add(tradingUser);
            }
        }return userList;
    }


    /**
     * Adds an item to tradingUser's specified list, which is either the Users.TradingUser's wishlist or inventory.
     *
     * @param tradingUser the tradingUser
     * @param item an item in the trading system.
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
     * @param tradingUser a tradingUser in the trading system.
     * @param item an item in the trading system.
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
     * @param tradingUser  a tradingUser in the trading system.
     * @param thresholdValue new value of threshold as an int
     * @param thresholdType  either "borrow", "weekly", or "incomplete" as a String
     */
    public void changeThreshold(TradingUser tradingUser, int thresholdValue, String thresholdType) {
        switch (thresholdType) {
            case "Borrow":
                tradingUser.setBorrowThreshold(thresholdValue);
                break;
            case "Weekly":
                tradingUser.setWeeklyThreshold(thresholdValue);
                break;
            case "Incomplete":
                tradingUser.setIncompleteThreshold(thresholdValue);
                break;
        }
    }

    /**
     * Changes the status of a tradingUser's account from active to frozen.
     *
     * @param tradingUser a tradingUser in the trading system.
     */
    public void freezeAccount(TradingUser tradingUser) {
        tradingUser.setStatus(UserStatuses.FROZEN);
        idToUser.get(tradingUser.getUserId()).setStatus(UserStatuses.FROZEN);
    }

    /**
     * Changes the status of a tradingUser's account from frozen to active.
     *
     * @param tradingUser a tradingUser in the trading system.
     */
    public void unfreezeAccount(TradingUser tradingUser) {
        tradingUser.setStatus(UserStatuses.ACTIVE);
        idToUser.get(tradingUser.getUserId()).setStatus(UserStatuses.ACTIVE);
    }

    /**
     * Changes the status of a tradingUser's account to vacation.
     *
     * @param tradingUser a tradingUser in the trading system.
     */
    public void onVacation(TradingUser tradingUser){
        tradingUser.setStatus(UserStatuses.VACATION);
        idToUser.get(tradingUser.getUserId()).setStatus(UserStatuses.VACATION);
    }

    /**
     * Adds a transaction to TradingUser's transaction history.
     *
     * @param tradingUser a tradingUser in the trading system.
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
     * @param tradingUser a tradingUser in a trading system
     * @param transaction a transaction between two Users
     */
    private void updateTransactionHistoryValues(TradingUser tradingUser, Transaction transaction) {
        TransactionHistory tH = tradingUser.getTransactionHistory();
        // TODO: Refactor this with a design pattern
        // if the user is the person giving away the object (user1) in transaction
        if (transaction.getUser1() == tradingUser.getUserId()) {
            // increment the numLended
            tradingUser.getTransactionHistory().setNumItemsLended();

            // get the username of user2 and see if it's in the idToUser and update it; otherwise, add the username
            String u2 = idToUser.get(transaction.getUser2()).getUsername();
            if (tH.getUsersNumTradeTimes().containsKey(u2)) {
                tH.getUsersNumTradeTimes().put(u2, tH.getUsersNumTradeTimes().get(u2) + 1);
            } else {
                tH.getUsersNumTradeTimes().put(u2, 1);
            }
            if (!transaction.isOneWay()) {
                // if the transaction is a twoway, increment borrowed
                tradingUser.getTransactionHistory().setNumItemsBorrowed();
            }
        } else { // if the user is the person receiving the object (user2) in transaction
            // increment the numBorrowed
            tradingUser.getTransactionHistory().setNumItemsBorrowed();
            // check to see if the other user is in the first user's usersNumTradeTimes list, increment or otherwise add the new user
            String u1 = idToUser.get(transaction.getUser1()).getUsername();
            if (tH.getUsersNumTradeTimes().containsKey(u1)) {
                tH.getUsersNumTradeTimes().put(u1, tH.getUsersNumTradeTimes().get(u1) + 1);
            } else {
                tH.getUsersNumTradeTimes().put(u1, 1);

                // if the transaction is twoway, increment lent
                if (!transaction.isOneWay()) {
                    tradingUser.getTransactionHistory().setNumItemsLended();
                }
            }
        }
    }

    /**
     * Returns a list of all TradingUsers in the Trading System.
     *
     * @return all tradingUsers in the system.
     */
    public List<TradingUser> getAllTradingUsers() {
        return allTradingUsers;
    }

    public List<String> getAllTradingUsersUsernames(){
        List<String> userList = new ArrayList<String>();
        for(User user : allTradingUsers){
            String username = user.getUsername();
            userList.add(username);
        }
        return userList;
    }

    /**
     * Returns whether this TradingUser with the given username and password is in the system or not.
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
     * Retrieves a list of TradingUsers that have had their account flagged to be frozen automatically by the system
     *
     * @return list of flagged to be frozen TradingUsers
     */
    public List<TradingUser> getFlaggedAccounts() {
        return flaggedAccounts;
    }

    /**
     * Retrieves a list of TradingUsers that have had their account frozen after approval by Admin.
     *
     * @return list of frozen TradingUsers
     */
    public List<TradingUser> getFrozenAccounts() {
        return frozenAccounts;
    }

    /**
     * Returns of the number of current transactions of TradingUser exceed the incomplete transaction threshold
     *
     * @param tradingUser TradingUser of interest
     * @return true iff incompleteTransactionExceeded
     */
    public boolean incompleteTransactionExceeded(TradingUser tradingUser) {
        return tradingUser.getCurrentTransactions().size() >= tradingUser.getIncompleteThreshold();
    }

    /**
     * Takes Transaction and moves it from both involved TradingUser's currentTransactions to their TransactionHistory.
     *
     * @param transaction the Transaction being moved.
     */
    public boolean moveTransactionToTransactionHistory(Transaction transaction) {
        Statuses status = transaction.getStatus();
        TradingUser user1 = getTradingUserById(transaction.getUser1());
        TradingUser user2 = getTradingUserById(transaction.getUser2());
        if (status.equals(Statuses.INCOMPLETE) || status.equals(Statuses.COMPLETED) || status.equals(Statuses.NEVERRETURNED)) {
            UUID id = transaction.getId();
            user1.getCurrentTransactions().remove(id);
            user2.getCurrentTransactions().remove(id);
            addToTransactionHistory(user1, transaction);
            addToTransactionHistory(user2, transaction);
            return true;
        }
        return false;
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
     *
     * @param id id of tradingUser
     * @return a tradingUser
     */
    public TradingUser getTradingUserById(UUID id) {
        return idToUser.get(id);
    }

    /**
     * Retrieves a List of TradingUsers usernames by a List of userIDs
     * @param ids List of userIDs
     * @return usernames List of TradingUser Usernames
     */

    public List<String> getUsernameListByID(List<UUID> ids){
        List<String> usernames = new ArrayList<>();
        for (UUID id : ids){
            usernames.add(getTradingUserById(id).getUsername());
        }
        return usernames;
    }

    /**
     * Removes item(s) temporarily from both users wishlists and inventory when the item(s) is involved in a transaction.
     * @param transaction the transaction involved.
     */
    protected void handlePermTransactionItems(Transaction transaction) { // if permanent transaction
        if (transaction.getStatus().equals(Statuses.COMPLETED)) {
            List<UUID> itemidlist = transaction.getTransactionItems();
            TradingUser user1 = this.getTradingUserById(transaction.getUser1());
            TradingUser user2 = this.getTradingUserById(transaction.getUser2());
            if (itemidlist.size() == 2) {
                user1.removeFromWishlist(itemidlist.get(1));
                user2.removeFromWishlist(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(0));
                user2.getInventory().remove(itemidlist.get(1));
            } else if (itemidlist.size() == 1) { // user 1 giving to user 2
                user2.getWishlist().remove(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(0));
            }
        }
    }

    protected void handleTempTransactionItems(Transaction transaction) { // if temporary transaction
        if (transaction.getStatus().equals(Statuses.TRADED)) { // after first meeting
            List<UUID> itemidlist = transaction.getTransactionItems();
            TradingUser user1 = this.getTradingUserById(transaction.getUser1());
            TradingUser user2 = this.getTradingUserById(transaction.getUser2());
            if (itemidlist.size() == 2) {
                user1.removeFromWishlist(itemidlist.get(1));
                user2.removeFromWishlist(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(0));
                user2.getInventory().remove(itemidlist.get(1));
            } else if (itemidlist.size() == 1) { // user 1 giving to user 2
                user2.getWishlist().remove(itemidlist.get(0));
                user1.getInventory().remove(itemidlist.get(0));
            }
        }
        if (transaction.getStatus().equals(Statuses.COMPLETED)) { // after second meeting
            List<UUID> itemidlist = transaction.getTransactionItems();
            TradingUser user1 = this.getTradingUserById(transaction.getUser1());
            TradingUser user2 = this.getTradingUserById(transaction.getUser2());
            if (itemidlist.size() == 2) {
                user1.getInventory().add(itemidlist.get(0));
                user2.getInventory().add(itemidlist.get(1));
            } else if (itemidlist.size() == 1) { // user 1 giving to user 2
                user1.getInventory().add(itemidlist.get(0));
            }
        }
    }

    /**
     * Returns whether the TradingUser's borrow threshold has been exceeded.
     *
     * @param tradingUser which TradingUser to check
     * @return boolean whether the threshold has been exceeded.
     */
    public boolean borrowThresholdExceeded(TradingUser tradingUser) {
        int numBorrowed = tradingUser.getTransactionHistory().getNumItemsBorrowed();
        int numLent = tradingUser.getTransactionHistory().getNumItemsLended();
        int threshold = tradingUser.getBorrowThreshold();
        return numBorrowed - numLent >= threshold;
    }

    /**
     * Returns a list of usernames corresponding to the TradingUser's in flaggedAccounts.
     * @return a list of flagged TradingUser's usernames.
     */
    public List<String> convertFlaggedUsersToUsernames() {
        List<String> usernames = new ArrayList<>();
        for (TradingUser user : flaggedAccounts) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

}
