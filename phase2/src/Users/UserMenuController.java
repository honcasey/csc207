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
public class UserMenuController{
    public TradingUser currentTradingUser = null; // user that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final CurrentTransactionManager tm;
    private final PastTransactionManager ptm;
    private final ItemManager im;


    private final Map<Item, TradingUser> allPendingItems;
    private Map<Item, TradingUser> availableItems;

    public UserMenuController(TradingUserManager tradingUserManager, AdminManager adminManager,
                              CurrentTransactionManager currentTransactionManager,
                              PastTransactionManager pastTransactionManager, ItemManager itemManager,
                              Map<Item, TradingUser> pendingItems, Map<Item, TradingUser> availableItems) {
        allPendingItems = pendingItems;
        am = adminManager;
        um = tradingUserManager;
        tm = currentTransactionManager;
        ptm = pastTransactionManager;
        im = itemManager;
        availableItems = getAvailableItems();
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


//    /* This takes in input from user and creates */
//    private void requestAddItem(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(ump.enterName("Item"));
//        String itemName = scanner.nextLine();
//        System.out.println(ump.itemDescription);
//        String itemDescription = scanner.nextLine();
//        Item requestedItem = new Item(itemName);
//        requestedItem.setDescription(itemDescription);
//        allPendingItems.put(requestedItem, currentTradingUser);
//        System.out.println(ump.itemRequested);
//    }

    /**
     * This is the method for handling the flow of:
     * 1) Displaying all available items to trade.
     * 2) Allowing the user to click on an item
     * 3) if the account is not frozen then the user can make a transaction for an item.
     * 4) if the account is not frozen but it breaches threshold criteria of system after the transaction, then
     * append the user to the admin's list of people that they can freeze.
     */
//    private void displayAvailableItems(){
//        boolean userInteracting = true;
//        while(userInteracting) {
//            List<Item> itemList = new ArrayList<>(availableItems.keySet());
//            if (itemList.isEmpty()) {
//                System.out.println(ump.empty("Available Items"));
//                break;
//            }
//
//            List<String> optionList = ump.constructAvailableItemsMenu(itemList);
//            int OptionChosen = ump.handleOptionsByIndex(optionList,true, "Available Items");
//            // Logic handling back to other menu vs. your account is frozen vs proceed to make create transaction menu.
//            if(OptionChosen == optionList.size() - 1) { // go back
//                System.out.println(ump.previousMenu);
//                userInteracting = false;
//            }
//            else{
//                // if account is frozen, print message and send them pack to previous menu
//                Item transactionItem = itemList.get(OptionChosen);
//                if(currentTradingUser.isFrozen()){
//                    System.out.println(ump.accountFrozen(true) + ump.requestAccountUnfreeze);
//                    if(ump.handleYesNo(ump.addToWishlist,"Yes","No")){
//                        this.currentTradingUser.getWishlist().add(transactionItem.getId());
//                    }
//                    System.out.println(ump.previousMenu);
//                    userInteracting = false;
//                }
//                else {
//                    // they are allowed to create another transaction or add to wishlist.
//                    if(ump.handleYesNo(ump.addToWishlist + " or " + ump.makeTransaction,"Wishlist",
//                            "Transaction")) { // if they selected to add to wishlist
//                        this.currentTradingUser.getWishlist().add(transactionItem.getId());
//                    }
//                    else { // if selected to create a transaction
//                        TradingUser transactionItemOwner = availableItems.get(transactionItem);
//                        userInteracting = createTransactionMenu(transactionItem, transactionItemOwner);
//                        flagAccountIfAboveThreshold(currentTradingUser);
//                    }
//                }
//            }
//        }
//    }

    /**
     * Creates a one-way transaction.
     * @param item The item that is going to be traded.
     * @param Owner The other user that is currently the owner of the item you want to trade for.
     * @param transactionType perm or temp (this should probably be an enum class somewhere maybe?)
     * @return returns true when the transaction has been made
     */
    public boolean createTransaction(Item item, TradingUser Owner, String transactionType, Meeting firstMeeting, Meeting secondMeeting) {
        TreeMap<UUID, List<UUID>> itemMap = new TreeMap<>();
        List itemList = new ArrayList<UUID>();
        itemList.add(item.getId());
        itemMap.put(Owner.getUserId(), itemList);
        Transaction newTransaction;
        if(transactionType.equals("perm")){
            newTransaction = tm.createTransaction(itemMap, firstMeeting);
        }
        else { // transaction is temp:
            newTransaction = tm.createTransaction(itemMap, firstMeeting, secondMeeting);
        }
        tm.updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
        availableItems.remove(item);
        return true;
    }

    /**
     * Creates a two-way transaction
     */
    public boolean createTransaction(Item item, Item item2, TradingUser Owner, String transactionType,
                                     Meeting firstMeeting) {
        TreeMap<UUID, List<UUID>> itemMap = new TreeMap<>();
        List itemList = new ArrayList<UUID>();
        itemList.add(item.getId());
        itemMap.put(Owner.getUserId(), itemList);
        Transaction newTransaction;
        if(transactionType.equals("perm")){
            newTransaction = tm.createTransaction(itemMap, firstMeeting);
        }
        else { // transaction is temp:
            newTransaction = tm.createTransaction(itemMap, firstMeeting);
        }
        tm.updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
        availableItems.remove(item);
        return true;
    }

//    private Item pickUserItemFlow(TradingUser CurrentUser){
//        List<Item> currentUserInventory = im.convertIdsToItems(CurrentUser.getInventory());
//        List<String> ItemOptions = ump.constructInventoryItemsList(currentUserInventory);
//        int OptionChosen = ump.handleOptionsByIndex(ItemOptions,false, "Available Inventory");
//        return currentUserInventory.get(OptionChosen);
//    }

    /* viewing a User's wishlist */
//    private void viewWishlist(){
//        boolean userInteracting = true;
//        while (userInteracting) {
//            if (currentTradingUser.getWishlist().size() == 0) { // if wishlist is empty
//                System.out.println(ump.empty("Wishlist"));
//                userInteracting = false;
//            } else {
//                List<Item> currentUserWishlist = im.convertIdsToItems(currentTradingUser.getWishlist());
//                List<String> ItemOptions = ump.constructWishlistItemsList(currentUserWishlist);
//                int itemChosen = ump.handleOptionsByIndex(ItemOptions, true, "Wishlist Items");
//                if (itemChosen == ItemOptions.size() - 1) { // checks if option chosen is "Go back."
//                    System.out.println(ump.previousMenu);
//                    userInteracting = false;
//                } else {
//                    String item = ItemOptions.get(itemChosen);
//                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Wishlist Menu");
//                    if (optionChosen == ump.itemOptionList().size()) { // checks if option chosen is "Go back."
//                        System.out.println(ump.previousMenu);
//                    } else if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
//                        try {
//                            Item whichItem = im.getItem(currentTradingUser.getWishlist().get(itemChosen));
//                            um.removeItem(currentTradingUser, whichItem, "wishlist");
//                            System.out.println(ump.successfullyRemoved(item,"wishlist"));
//                        } catch (InvalidItemException e) {
//                            // this should never happen
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /* viewing a User's inventory */
//    private void viewInventory() {
//        boolean userInteracting = true;
//        while (userInteracting) {
//            if (currentTradingUser.getInventory().size() == 0) { // if inventory is empty
//                System.out.println(ump.empty("Inventory"));
//                userInteracting = false;
//            } else {
//                List<Item> currentUserInventory = im.convertIdsToItems(currentTradingUser.getInventory()); // this is making a list of all null items right now (with the right size)
//                List<String> ItemOptions = ump.constructInventoryItemsList(currentUserInventory);
//                int itemChosen = ump.handleOptionsByIndex(ItemOptions, true, "Inventory Items");
//                if (itemChosen == ItemOptions.size() - 1) { // checks if option chosen is "Go back."
//                    System.out.println(ump.previousMenu);
//                    userInteracting = false;
//                } else {
//                    String item = ItemOptions.get(itemChosen);
//                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Inventory Menu");
//                    if (optionChosen == ump.itemOptionList().size()) { // checks if option chosen is "Go back."
//                        System.out.println(ump.previousMenu);
//                    }
//                    else if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
//                        try {
//                            Item whichItem = im.getItem(currentTradingUser.getInventory().get(itemChosen));
//                            um.removeItem(currentTradingUser, whichItem, "inventory");
//                            System.out.println(ump.successfullyRemoved(item,"inventory"));
//                        } catch (InvalidItemException e) {
//                            // this should never happen
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /* for a frozen TradingUser to request their account to be unfrozen */
//    private void requestUnfreezeAccount() {
//        if (currentTradingUser.isFrozen()) {
//            am.getFrozenAccounts().add(currentTradingUser);
//            System.out.println(ump.requestedUnfreeze);
//        } else {
//            System.out.println(ump.accountFrozen(false));
//        }
//    }
//
//    /**
//     * Displays past transactions / history of a TradingUser
//     * Waiting for user input(last 3 lines):
//     * https://stackoverflow.com/questions/26184409/java-console-prompt-for-enter-input-before-moving-on/26184565
//     * by M Anouti
//     */
//    private void pastTransactionFlow(){
//        boolean userInteracting = true;
//        while (userInteracting) {
//            List<String> MenuOptionList = ump.constructPastTransactionMenu();
//            int OptionChosen = ump.handleOptionsByIndex(MenuOptionList, true, "Past Transactions Menu");
//            if (OptionChosen == MenuOptionList.size() - 1) { // if "Go back" is chosen
//                System.out.println(ump.previousMenu);
//                userInteracting = false;
//            } else {
//                /* if viewing most recent one-way transactions */
//                if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewRecentTransactions("one"))) {
//                    List<UUID> OneWayTransactionIds = currentTradingUser.getTransactionHistory().mostRecentOneWayTransactions();
//                    if (OneWayTransactionIds.isEmpty()) { // no recent one-way transactions
//                        System.out.println(ump.empty("One Way Transactions"));
//                    } else {
//                        List<Transaction> OneWayTransaction = ptm.getTransactionsFromIdList(OneWayTransactionIds);
//                        List<String> oneWayTransactionOptions = ump.constructTransactionList(OneWayTransaction);
//                        ump.displayOptions(oneWayTransactionOptions);
//                    }
//                    /* if viewing most recent two-way transactions */
//                } else if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewRecentTransactions("two"))) {
//                    List<UUID> TwoWayTransactionIds = currentTradingUser.getTransactionHistory().mostRecentTwoWayTransactions();
//                    if (TwoWayTransactionIds.isEmpty()) { // no recent two-way transactions
//                        System.out.println(ump.empty("Two Way Transactions"));
//                    } else {
//                        List<Transaction> TwoWayTransactions = ptm.getTransactionsFromIdList(TwoWayTransactionIds);
//                        List<String> twoWayTransactionOptions = ump.constructTransactionList(TwoWayTransactions);
//                        ump.displayOptions(twoWayTransactionOptions);
//                    }
//                    /* if viewing most traded with users */
//                } else if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewThreeMostTraded)) {
//                    List<String> TradedWithUsersOptions = currentTradingUser.getTransactionHistory().mostTradedWithUsers();
//                    if (TradedWithUsersOptions.isEmpty()) { // no most traded with users
//                        System.out.println(ump.empty("Most Traded-with Users"));
//                        userInteracting = false;
//                    } else {
//                        ump.displayOptions(TradedWithUsersOptions);
//                    }
//                }
//            }
//        }
//    }


//    public TreeMap<Item,User> TransactionSuggestions() {
//        // checking for items that are exactly the same in the same city.
//        List<TradingUser> sameCityUsers = this.um.getTradingUserByCity(currentTradingUser.getCity());
//
//        List<TradingUser> diffCityUsers = this.um.getTradingUsersOutOfCity(currentTradingUser.getCity());
//    }
//
//    public List<TradingUser> PossibleBorrowers(List<TradingUser> usersList){
//        List<TradingUser> common = new ArrayList<Integer>(listA);
//        List<TradingUser> borrowerList = new ArrayList<>();
//        for(TradingUser user: usersList){
//
//
//        }
//    }
//
//    public List<Item> PossibleLentItems(TradingUser otherUser){
//        for(UUID inventoryItemId: currentTradingUser.getInventory()){
//            Item inventoryItem = im.getItem(inventoryItemId);
//
//            if(areSameItems())
//        }
//    }





    public List<Transaction> currentTransactionList() {
        List<UUID> currentTransactionsIds = currentTradingUser.getCurrentTransactions();
        return tm.getTransactionsFromIdList(currentTransactionsIds);
    }

    public boolean editMeetingFlow(UUID user, Transaction transaction, int meetingNum, String newLocation,
                                   Date newTime, Date newDate){
        updateUsers(transaction, Actions.EDITED);
        return (tm.editMeeting(meetingNum, transaction, user, newLocation) |
                tm.editMeeting(meetingNum, transaction, user, newTime, newDate));
    }

    /**
     * Updates the inputted transaction's status depending on what the TradingUser selected.
     * @param tradingUser currently logged in TradingUser
     * @param transaction inputted transaction
     * @param optionChosen which option has been chosen by the currently logged in TradingUser
     */
    public void updateStatusUser(TradingUser tradingUser, Transaction transaction, Actions optionChosen){
        UUID userId = tradingUser.getUserId();
        if (optionChosen.equals(Actions.CONFIRMMEETINGDETAILS)){
            transaction.setStatusUserID(Statuses.CONFIRMED, userId);
        }
        if (optionChosen.equals(Actions.CANCEL)){
            transaction.setStatusUserID(Statuses.CANCELLED, userId);
        }
        if (optionChosen.equals(Actions.CONFIRMMEETUP)) {
            transaction.setStatusUserID(Statuses.TRADED, userId);
        }
        if (optionChosen.equals(Actions.MEETUPINCOMPLETE)) {
            transaction.setStatusUserID(Statuses.INCOMPLETE, userId);
        }
        if (optionChosen.equals(Actions.ITEMRETURNED)){
            transaction.setStatusUserID(Statuses.COMPLETED, userId);
        }
        if (optionChosen.equals(Actions.ITEMNOTRETURNED)){
            transaction.setStatusUserID(Statuses.NEVERRETURNED, userId);
        }
        if (optionChosen.equals(Actions.EDITED)){
            transaction.setStatusUserID(Statuses.PENDING, userId);
        }
    }



    public void updateUsers(Transaction transaction, Actions optionChosen) {
        updateStatusUser(currentTradingUser, transaction, optionChosen);
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
     * returns if one of the users statuses are pending
     * @param transaction which transaction
     * @return boolean
     */
    public boolean userStatuses(Transaction transaction) {
        return !(transaction.getStatusUser1().equals(Statuses.PENDING) | transaction.getStatusUser2().equals(Statuses.PENDING));
    }

    /**
     * Creates a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    public Map<Item, TradingUser> getAvailableItems(){
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
}