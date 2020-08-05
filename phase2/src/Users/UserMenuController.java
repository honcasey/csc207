package Users;

import Admins.AdminManager;
import Exceptions.InvalidItemException;
import Exceptions.InvalidTradingUserException;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.*;

import java.util.*;

/**
 * <h1>UserMenuController</h1>
 * Decides which use case/manager methods to call depending on the user input taken from the presenter.
 * <p>It stores instances of all use cases/managers (AdminManager, TradingUserManager, ItemManager,
 * CurrentTransactionManager, PastTransactionManager), the UserMenuPresenter,
 * and a list of allPendingItems (which is the list of all items that have been requested by a
 * TradingUser to be added to their inventory). <p/>
 */
public class UserMenuController {
    public TradingUser currentTradingUser = null; // user that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final CurrentTransactionManager tm;
    private final PastTransactionManager ptm;
    private final ItemManager im;


    private final Map<Item, TradingUser> allPendingItems;
    private Map<Item, TradingUser> availableItems = getAvailableItems();

    public UserMenuController(TradingUserManager tradingUserManager, AdminManager adminManager,
                              CurrentTransactionManager currentTransactionManager,
                              PastTransactionManager pastTransactionManager, ItemManager itemManager,
                              Map<Item, TradingUser> pendingItems) {
        allPendingItems = pendingItems;
        am = adminManager;
        um = tradingUserManager;
        tm = currentTransactionManager;
        ptm = pastTransactionManager;
        im = itemManager;
    }

    /**
     * Method that adds pending items to the current trading user's pending items
     */
    public void addToPendingItems(Item requestedItem) {
        allPendingItems.put(requestedItem, currentTradingUser);
    }

    /**
     * Adds item to a wishlist and returns true if the item was not already in the wishlist. If already in wishlist, returns false.
     */
    public boolean addToWishlist(Item item) {
        return um.addItem(currentTradingUser, item, "wishlist");
    }

    /**
     * Creates a temp transaction.
     * @param items The item that is going to be traded.
     * @param owner The other user that is currently the owner of the item you want to trade for.
     */
    public void buildTransaction(List<UUID> items, TradingUser owner, Meeting firstMeeting, Meeting secondMeeting) throws InvalidItemException {
        TreeMap<UUID, List<UUID>> itemMap = new TreeMap<>();
        itemMap.put(owner.getUserId(), items);
        Transaction newTransaction;
        newTransaction = tm.createTransaction(itemMap, firstMeeting, secondMeeting);
        tm.updateUsersCurrentTransactions(owner,currentTradingUser,newTransaction);
        flagAccountIfAboveThreshold(owner);
        for (UUID id : items) {
            Item item = im.getItem(id);
            availableItems.remove(item);
        }
    }

    /**
     * Creates a perm transaction
     */
    public void buildTransaction(List<UUID> items, TradingUser owner, Meeting firstMeeting) throws InvalidItemException {
        TreeMap<UUID, List<UUID>> itemMap = new TreeMap<>();
        itemMap.put(owner.getUserId(), items);
        Transaction newTransaction;
        newTransaction = tm.createTransaction(itemMap, firstMeeting);
        tm.updateUsersCurrentTransactions(owner,currentTradingUser,newTransaction);
        flagAccountIfAboveThreshold(owner);
        for (UUID id : items) {
            Item item = im.getItem(id);
            availableItems.remove(item);
        }
    }

    /**
     * Creates a virtual transaction
     */
    public void buildTransaction(List<UUID> items, TradingUser owner) throws InvalidItemException {
        TreeMap<UUID, List<UUID>> itemMap = new TreeMap<>();
        itemMap.put(owner.getUserId(), items);
        Transaction newTransaction = tm.createTransaction(itemMap);
        tm.updateUsersCurrentTransactions(owner, currentTradingUser, newTransaction);
        flagAccountIfAboveThreshold(owner);
        for (UUID id : items) {
            Item item = im.getItem(id);
            availableItems.remove(item);
        }
    }

    public List<Transaction> currentTransactionList() {
        try {
            List<UUID> currentTransactionsIds = currentTradingUser.getCurrentTransactions();
            return tm.getTransactionsFromIdList(currentTransactionsIds);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean editMeetingFlow(UUID user, Transaction transaction, int meetingNum, String newLocation,
                                   Date newTime, Date newDate){
        updateUsers(transaction, Actions.EDITED);
        return (tm.editMeeting(meetingNum, transaction, user, newLocation) |
                tm.editMeeting(meetingNum, transaction, user, newTime, newDate));
    }

    public void updateUsers(Transaction transaction, Actions optionChosen) {
        tm.updateStatusUser(currentTradingUser, transaction, optionChosen);
        List<UUID> currentTransactionsIds = currentTradingUser.getCurrentTransactions();
        tm.updateStatus(transaction); //update status of transaction
        if (transaction.isPerm()) { // if transaction is permanent (only one meeting)
            um.handlePermTransactionItems(transaction); // remove items from both users inventories and wishlists
        }
        /* if transaction is temporary (two meetings) */
        else { um.handleTempTransactionItems(transaction); } // handles users inventories and wishlists
        /* if transaction is cancelled, remove from current transactions */
        if (transaction.getStatus().equals(Statuses.CANCELLED)) {
            try {
                tm.removeTransactionFromAllTransactions(transaction.getId()); // if cancelled, the transaction is deleted forever
                currentTransactionsIds.remove(transaction.getId()); // remove from current/active transactions
            }
            catch (InvalidTransactionException e) {
                //
            }
        }
        /* if transaction is over (incomplete, complete, never returned) then move to transaction history
        * and remove from current transactions */
        if (um.moveTransactionToTransactionHistory(transaction)) {
            currentTransactionsIds.remove(transaction.getId()); // remove from the list of active transaction's the logged in user sees
        }
    }

    /**
     * Creates a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    public Map<Item, TradingUser> getAvailableItems(){
        try {
            List<TradingUser> allTradingUsersInCity = um.getTradingUserByCity(currentTradingUser.getCity());
            HashMap<Item, TradingUser> availableItems = new HashMap<>();
            for (TradingUser tradingUser : allTradingUsersInCity) {
                if(!tradingUser.equals(currentTradingUser)) {
                    for (Item item : im.convertIdsToItems(tradingUser.getInventory())) {
                        availableItems.put(item, tradingUser);
                    }
                }
            }
            return availableItems;
        } catch (NullPointerException e) {
            return null;
        }
    }

    /* set a TradingUser to be flagged for admin approval if either the borrow, weekly, or incomplete thresholds have been reached */
    private void flagAccountIfAboveThreshold(TradingUser user) {
        boolean weeklyThreshold = ptm.weeklyThresholdExceeded(user);
        boolean TransactionsExceeded = um.incompleteTransactionExceeded(user);
        boolean borrowThreshold = um.borrowThresholdExceeded(user);
        if (weeklyThreshold || TransactionsExceeded || borrowThreshold) {
            List<String> flaggedUsernames = um.convertFlaggedUsersToUsernames();
            if (!flaggedUsernames.contains(user.getUsername())) {
                um.getFlaggedAccounts().add(user);
            }
        }
    }

    public boolean validUser(String username, String password) {
        return um.validUser(username, password);
    }

    public void setCurrentTradingUser(String username) {
        try {
            currentTradingUser = um.getTradingUser(username);
        } catch (InvalidTradingUserException e) {
            // TODO
        }
    }

    public boolean availableUsername(String username) {
        return am.checkAvailableUsername(username) && um.checkAvailableUsername(username);
    }

    public void addTradingUser(String username, String password) {
        try {
            um.addTradingUser(username, password);
        } catch (InvalidTradingUserException e) {
            // TODO
        }
    }

    public ItemManager getIm(){
        return im;
    }

    /**
     * This method takes in a transaction and the current user using the system. It then formats a string
     * representation of the transaction by making calls to the different managers in the program.
     * This method should output different strings depending on if the transaction is Permanent/Temporary,
     * One Way/Two Way.
     * @param transaction The transaction whose string representation you want.
     * @param Currentuser The user who is currently using the program.
     * @return returns a string representation for the transaction in the perspective of the user who is using the
     * program.
     */
    public String getTransactionString(Transaction transaction, User Currentuser){
        String returnString;
        UUID otherUserid = transaction.getOtherUser(Currentuser.getUserId());
        String otherUser = um.getTradingUserById(otherUserid).toString();
        if(transaction.isPerm()){
            returnString = "Permanent Transaction between you and " +otherUser;
        }
        else{
            returnString = "Temporary Transaction between you and "+ otherUser;
        }
        if(transaction.getItemIdDesired(Currentuser.getUserId()) == null) {
            String YourItemString = null;
            try {
                Item YourItem = im.getItem(transaction.getItemIdOwned(Currentuser.getUserId()));
                YourItemString = YourItem.toString();
            } catch (InvalidItemException e) {
                System.out.println("Item Manager is not being updated  properly");
            }
            return returnString + "to give " + YourItemString;
        }
        if(transaction.getItemIdOwned(Currentuser.getUserId()) == null){
            String TheirItemString = null;
            try {
                Item TheirItem = im.getItem(transaction.getItemIdOwned(otherUserid));
                TheirItemString = TheirItem.toString();
            } catch (InvalidItemException e) {
                System.out.println("Item Manager is not being updated  properly");
            }
            return returnString + "to get " + TheirItemString;
        }
        else{
            String TheirItemString = null;
            String YourItemString = null;
            try {
                Item TheirItem = im.getItem(transaction.getItemIdOwned(otherUserid));
                TheirItemString = TheirItem.toString();
                Item YourItem = im.getItem(transaction.getItemIdOwned(Currentuser.getUserId()));
                YourItemString = YourItem.toString();
            } catch (InvalidItemException e) {
                System.out.println("Item Manager is not being updated  properly");
            }
            return returnString + "to get " + TheirItemString+ " for " + YourItemString;
        }
    }

    public CurrentTransactionManager getTm() { return tm; }

    public TradingUserManager getUm() { return um; }

    public Map<Item, TradingUser> getAllPendingItems() {
        return allPendingItems;
    }
}