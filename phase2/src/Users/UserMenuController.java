package Users;

import Admins.AdminManager;
import Exceptions.InvalidAdminException;
import Exceptions.InvalidItemException;
import Exceptions.InvalidTradingUserException;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.Meeting;
import Transactions.PastTransactionManager;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;
import Transactions.Statuses;

import java.time.LocalDate;
import java.time.LocalTime;
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
    protected TradingUser currentTradingUser = null; // user that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final CurrentTransactionManager tm;
    private final PastTransactionManager ptm;
    private final ItemManager im;
    private final Map<Item, TradingUser> allPendingItems;
    private final UserMenuPresenter ump = new UserMenuPresenter();
//    private HashMap<Item, TradingUser> availableItems;

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
//        availableItems = getAvailableItems();
    }

    /**
     * Method that calls to different helper methods depending on user's input choice in the main menu.
     */
    public void run() {
        boolean userInteracting = true;
        while(userInteracting){
            List<String> menu = ump.constructMainMenu();
            int input = ump.handleOptionsByIndex(menu, false,"TradingUser Main Menu");
            if (ump.indexToOption(input, menu, ump.requestItem)){
                requestAddItem();
            } else if (ump.indexToOption(input, menu, ump.browseAvailableItems)) {
//                displayAvailableItems();
            } else if (ump.indexToOption(input, menu, ump.viewActiveTransactions)) {
//                getActiveTransactions();
            } else if (ump.indexToOption(input, menu, ump.viewPastTransactionDetails)) {
                pastTransactionFlow();
            } else if (ump.indexToOption(input, menu, ump.viewWishlist)) {
                viewWishlist();
            } else if (ump.indexToOption(input, menu, ump.viewInventory)) {
                viewInventory();
            } else if (ump.indexToOption(input, menu, ump.requestUnfreeze)) {
                requestUnfreezeAccount();
            } else if (ump.indexToOption(input, menu, ump.logout)) {
                System.out.println(ump.successfulLogout);
                userInteracting = false;
            }
        }
    }

    /* This takes in input from user and creates */
    private void requestAddItem(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ump.enterName("Item"));
        String itemName = scanner.nextLine();
        System.out.println(ump.itemDescription);
        String itemDescription = scanner.nextLine();
        Item requestedItem = new Item(itemName);
        requestedItem.setDescription(itemDescription);
        allPendingItems.put(requestedItem, currentTradingUser);
        System.out.println(ump.itemRequested);
    }

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

    ///**
    //* Handles the flow for setting up a transaction for an available item assuming that the transaction
    // * is allowed between the 2 users.
    // *
    // * @param item The item that is going to be traded.
    // * @param Owner The other user that is currently the owner of the item you want to trade for.
    // * @return this method returns a true if the user wants to make another offer for an item and returns
    // * false if the user wants to head back to the main menu.
    // */
//    private boolean createTransactionMenu(Item item, TradingUser Owner) {
//        System.out.println(ump.scheduleMeeting);
//        Meeting FirstMeeting = meetingDetailsMenu("First");
//
//        boolean permBool = ump.handleYesNo(ump.whatTypeOfTransaction,"Permanent","Temporary");
//        boolean oneWayBool = !ump.handleYesNo(ump.offerItem,"Yes","No");
//
//        if(permBool & oneWayBool){
//            Transaction newTransaction = tm.createTransaction(
//                    Owner.getUserId(),currentTradingUser.getUserId(), item, FirstMeeting);
//            updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
//        }
//        else if(permBool & !oneWayBool){
//            if(currentTradingUser.getInventory().isEmpty()){
//                System.out.println(ump.empty("inventory"));
//            }
//            else{
//                System.out.println(ump.selectItemToOffer);
//                Item ChosenItem = this.pickUserItemFlow(this.currentTradingUser);
//                Transaction newTransaction = tm.createTransaction(
//                    Owner.getUserId(), currentTradingUser.getUserId(), item, ChosenItem, FirstMeeting);
//                updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
//                availableItems.remove(ChosenItem);
//            }
//        }
//        else if(oneWayBool){   // note permBool must be false at this point: aka you're creating a temp Transaction
//            Meeting SecondMeeting = tm.meetOneMonthLater(FirstMeeting);
//            Transaction newTransaction = tm.createTransaction(
//                    Owner.getUserId(),currentTradingUser.getUserId(), item,FirstMeeting,SecondMeeting);
//            updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
//        }
//        else{
//            if(currentTradingUser.getInventory().isEmpty()){
//                System.out.println(ump.empty("Inventory"));
//            }
//            else{
//                System.out.println(ump.selectItemToOffer);
//                Item ChosenItem = this.pickUserItemFlow(this.currentTradingUser);
//                Meeting SecondMeeting = tm.meetOneMonthLater(FirstMeeting);
//                Transaction newTransaction = tm.createTransaction(Owner.getUserId(),
//                    currentTradingUser.getUserId(), item, ChosenItem,FirstMeeting,SecondMeeting);
//                updateUsersCurrentTransactions(Owner,currentTradingUser,newTransaction);
//                availableItems.remove(ChosenItem);
//            }
//        }
//        availableItems.remove(item);
//        return(ump.handleYesNo(ump.makeTransaction,"Yes","No"));
//    }

    private Item pickUserItemFlow(TradingUser CurrentUser){
        List<Item> currentUserInventory = im.convertIdsToItems(CurrentUser.getInventory());
        List<String> ItemOptions = ump.constructInventoryItemsList(currentUserInventory);
        int OptionChosen = ump.handleOptionsByIndex(ItemOptions,false, "Available Inventory");
        return currentUserInventory.get(OptionChosen);
    }

    /**
     * This method is ONLY allowed to be used in the createTransactionMenu
     *
     * DO NOT USE THIS METHOD. THERE ARE NO EXCEPTIONS WRITTEN AND THIS METHOD IS OUT OF PLACE
     * THIS WILL HAVE TO BE MOVED SOMEWHERE ELSE
     * @param user1 one of the users in transaction.
     * @param user2 one of the users in transaction.
     * @param newTransaction the actual transaction object. (for which method will get ids for)
     */
    private void updateUsersCurrentTransactions(TradingUser user1,TradingUser user2,Transaction newTransaction){
        user1.getCurrentTransactions().add(newTransaction.getId());
        user2.getCurrentTransactions().add(newTransaction.getId());
    }

    /**
     * Prompts user for details required for a meeting, then constructs a meeting.
     * @param meetingTitle The first thing that will be displayed "Second Transactions.Meeting Details"/"First Transactions.Meeting Details"
     */
    private Meeting meetingDetailsMenu(String meetingTitle){
        Scanner scanner = new Scanner(System.in);
        System.out.println(meetingTitle + "Meeting Details");
        System.out.println(ump.meetingLocation);
        String MeetingLocation = scanner.nextLine();
        LocalTime MeetingTime = ump.inputTimeGetter(ump.enterWhatInFormat("time", "hh:mm:ss"));
        LocalDate MeetingDate = ump.inputDateGetter(ump.enterWhatInFormat("date", "dd-mm-yyyy"));
        return new Meeting(MeetingLocation,MeetingTime,MeetingDate);
    }

    /* viewing a User's wishlist */
    private void viewWishlist(){
        boolean userInteracting = true;
        while (userInteracting) {
            if (currentTradingUser.getWishlist().size() == 0) { // if wishlist is empty
                System.out.println(ump.empty("Wishlist"));
                userInteracting = false;
            } else {
                List<Item> currentUserWishlist = im.convertIdsToItems(currentTradingUser.getWishlist());
                List<String> ItemOptions = ump.constructWishlistItemsList(currentUserWishlist);
                int itemChosen = ump.handleOptionsByIndex(ItemOptions, true, "Wishlist Items");
                if (itemChosen == ItemOptions.size() - 1) { // checks if option chosen is "Go back."
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                } else {
                    String item = ItemOptions.get(itemChosen);
                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Wishlist Menu");
                    if (optionChosen == ump.itemOptionList().size()) { // checks if option chosen is "Go back."
                        System.out.println(ump.previousMenu);
                    } else if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
                        try {
                            Item whichItem = im.getItem(currentTradingUser.getWishlist().get(itemChosen));
                            um.removeItem(currentTradingUser, whichItem, "wishlist");
                            System.out.println(ump.successfullyRemoved(item,"wishlist"));
                        } catch (InvalidItemException e) {
                            // this should never happen
                        }
                    }
                }
            }
        }
    }

    /* viewing a User's inventory */
    private void viewInventory() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (currentTradingUser.getInventory().size() == 0) { // if inventory is empty
                System.out.println(ump.empty("Inventory"));
                userInteracting = false;
            } else {
                List<Item> currentUserInventory = im.convertIdsToItems(currentTradingUser.getInventory()); // this is making a list of all null items right now (with the right size)
                List<String> ItemOptions = ump.constructInventoryItemsList(currentUserInventory);
                int itemChosen = ump.handleOptionsByIndex(ItemOptions, true, "Inventory Items");
                if (itemChosen == ItemOptions.size() - 1) { // checks if option chosen is "Go back."
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                } else {
                    String item = ItemOptions.get(itemChosen);
                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Inventory Menu");
                    if (optionChosen == ump.itemOptionList().size()) { // checks if option chosen is "Go back."
                        System.out.println(ump.previousMenu);
                    }
                    else if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
                        try {
                            Item whichItem = im.getItem(currentTradingUser.getInventory().get(itemChosen));
                            um.removeItem(currentTradingUser, whichItem, "inventory");
                            System.out.println(ump.successfullyRemoved(item,"inventory"));
                        } catch (InvalidItemException e) {
                            // this should never happen
                        }
                    }
                }
            }
        }
    }

    /* for a frozen TradingUser to request their account to be unfrozen */
    private void requestUnfreezeAccount() {
        if (currentTradingUser.isFrozen()) {
            am.getFrozenAccounts().add(currentTradingUser);
            System.out.println(ump.requestedUnfreeze);
        } else {
            System.out.println(ump.accountFrozen(false));
        }
    }

    /**
     * Displays past transactions / history of a TradingUser
     * Waiting for user input(last 3 lines):
     * https://stackoverflow.com/questions/26184409/java-console-prompt-for-enter-input-before-moving-on/26184565
     * by M Anouti
     */
    private void pastTransactionFlow(){
        boolean userInteracting = true;
        while (userInteracting) {
            List<String> MenuOptionList = ump.constructPastTransactionMenu();
            int OptionChosen = ump.handleOptionsByIndex(MenuOptionList, true, "Past Transactions Menu");
            if (OptionChosen == MenuOptionList.size() - 1) { // if "Go back" is chosen
                System.out.println(ump.previousMenu);
                userInteracting = false;
            } else {
                /* if viewing most recent one-way transactions */
                if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewRecentTransactions("one"))) {
                    List<UUID> OneWayTransactionIds = currentTradingUser.getTransactionHistory().mostRecentOneWayTransactions();
                    if (OneWayTransactionIds.isEmpty()) { // no recent one-way transactions
                        System.out.println(ump.empty("One Way Transactions"));
                    } else {
                        List<Transaction> OneWayTransaction = ptm.getTransactionsFromIdList(OneWayTransactionIds);
                        List<String> oneWayTransactionOptions = ump.constructTransactionList(OneWayTransaction);
                        ump.displayOptions(oneWayTransactionOptions);
                    }
                    /* if viewing most recent two-way transactions */
                } else if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewRecentTransactions("two"))) {
                    List<UUID> TwoWayTransactionIds = currentTradingUser.getTransactionHistory().mostRecentTwoWayTransactions();
                    if (TwoWayTransactionIds.isEmpty()) { // no recent two-way transactions
                        System.out.println(ump.empty("Two Way Transactions"));
                    } else {
                        List<Transaction> TwoWayTransactions = ptm.getTransactionsFromIdList(TwoWayTransactionIds);
                        List<String> twoWayTransactionOptions = ump.constructTransactionList(TwoWayTransactions);
                        ump.displayOptions(twoWayTransactionOptions);
                    }
                    /* if viewing most traded with users */
                } else if (ump.indexToOption(OptionChosen, MenuOptionList, ump.viewThreeMostTraded)) {
                    List<String> TradedWithUsersOptions = currentTradingUser.getTransactionHistory().mostTradedWithUsers();
                    if (TradedWithUsersOptions.isEmpty()) { // no most traded with users
                        System.out.println(ump.empty("Most Traded-with Users"));
                        userInteracting = false;
                    } else {
                        ump.displayOptions(TradedWithUsersOptions);
                    }
                }
            }
        }
    }

    public List<Transaction> currentTransactionList() {
        List<UUID> currentTransactionsIds = currentTradingUser.getCurrentTransactions();
        return tm.getTransactionsFromIdList(currentTransactionsIds);
    }

    public TradingUser getOtherUser(Transaction transaction) {
        return um.getTradingUserById(transaction.getUser2());
    }

    /**
     * Displays all of the active transactions for TradingUser and then redirects the user to either edit the Meetings
     * for that transaction or to change the statusUser of the Transaction
     */
    private void getActiveTransactions() {
        if (ump.indexToOption(optionChosen2, transactionActions, "Edit Transactions Meeting(s)")) {
                        editMeeting(currentTradingUser, transaction); // prompt user to edit meeting
                        // check if status is changed to cancelled after this edit
                        if (transaction.getStatus().equals(Statuses.CANCELLED)) {
                            try {
                                tm.removeTransactionFromAllTransactions(transaction.getId()); // if cancelled, the transaction is deleted forever
                                currentTransactionsIds.remove(transaction.getId()); // remove from current/active transactions
                            } catch (InvalidTransactionException e) {
                                //
                            }
                        }
                    }
                    // updates the users, transactions, inventories, and wishlists
                    updateUsers(transaction, transactionActions, optionChosen2, currentTransactionsIds);
                }

    private void updateUsers(Transaction transaction, List<String> transactionActions, int optionChosen2, List<UUID> currentTransactionsIds) {
        if (tm.updateStatusUser(currentTradingUser, transaction, transactionActions.get(optionChosen2))) { //update status of user
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
    }

    /**
     * Creates a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    private HashMap<Item, TradingUser> getAvailableItems(){
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

//    private void editMeeting(TradingUser currentTradingUser, Transaction transaction) {
//        boolean userInteracting = true;
//        UUID user = currentTradingUser.getUserId();
//
//        while (userInteracting) {
//            // check if both users have reached their edit threshold without confirming
//            if (transaction.getFirstMeeting().getNumEditsUser1() == 3 && transaction.getFirstMeeting().getNumEditsUser2() == 3) {
//                System.out.println("Transaction has been cancelled");
//                transaction.setStatus(Statuses.CANCELLED);
//            }
//            if (!tm.transactionHasMultipleMeetings(transaction)) { // for transactions with one meeting
//                List<String> options = ump.constructEditMeetingOptions();
//                int OptionChosen = ump.handleOptionsByIndex(options, true, "Meeting Options");
//                if (OptionChosen == options.size() - 1) {
//                    System.out.println(ump.previousMenu);
//                    userInteracting = false;
//                } else {
//                    switch (OptionChosen) {
//                        case 0:
//                            editMeetingFlow(user, transaction, 1, "location");
//                            break;
//                        case 1:
//                            editMeetingFlow(user, transaction, 1, "time");
//                            break;
//                        default:
//                            editMeetingFlow(user, transaction, 1, "date");
//                    }
//                }
//            } else if (tm.transactionHasMultipleMeetings(transaction)) { // for transactions with two meetings
//                int meetingNum = ump.handleOptionsByIndex(ump.constructWhichMeetingList(), true,
//                        "Transaction with two meetings");
//                if (meetingNum == ump.constructWhichMeetingList().size()) {
//                    System.out.println(ump.previousMenu);
//                    userInteracting = false;
//                } else {
//                    Meeting meeting = transaction.getTransactionMeetings().get(meetingNum);
//                    System.out.println("This is the meeting you wish to edit " + meeting.toString());
//                    List<String> options = ump.constructEditMeetingOptions();
//                    int OptionChosen = ump.handleOptionsByIndex(options, true, "Meeting Options");
//                    // Logic handling back to other menu vs. Editing a meeting
//                    if (OptionChosen == options.size() - 1) {
//                        System.out.println(ump.previousMenu);
//                        userInteracting = false;
//                    } else {
//                        switch (OptionChosen) {
//                            case 0:
//                                editMeetingFlow(user, transaction, meetingNum + 1, "location");
//                                break;
//                            case 1:
//                                editMeetingFlow(user, transaction, meetingNum + 1, "time");
//                                break;
//                            default:
//                                editMeetingFlow(user, transaction, meetingNum + 1, "date");
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void editMeetingFlow(UUID user, Transaction transaction, int meetingNum, String which) {
        switch (which) {
            case "location":
                Scanner scanner = new Scanner(System.in);
                System.out.println(ump.meetingLocation);
                String MeetingLocation = scanner.nextLine();
                if (tm.editMeeting(meetingNum, transaction, user, MeetingLocation)) {
                    System.out.println(ump.successfullyEditedMeeting(MeetingLocation));
                } else {
                    System.out.println(ump.editThresholdReached);
                }
                break;
            case "time":
                LocalTime MeetingTime = ump.inputTimeGetter(ump.enterWhatInFormat("time", "hh:mm:ss"));
                if (tm.editMeeting(meetingNum, transaction, user, MeetingTime)) {
                    System.out.println(ump.successfullyEditedMeeting(MeetingTime.toString()));
                } else {
                    System.out.println(ump.editThresholdReached);
                }
                break;
            case "date":
                LocalDate MeetingDate = ump.inputDateGetter(ump.enterWhatInFormat("date", "dd-mm-yyyy"));
                if (tm.editMeeting(meetingNum, transaction, user, MeetingDate)) {
                    System.out.println(ump.successfullyEditedMeeting(MeetingDate.toString()));
                } else {
                    System.out.println(ump.editThresholdReached);
                }
                break;
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
}