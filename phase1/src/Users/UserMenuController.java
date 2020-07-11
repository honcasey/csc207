package Users;

import Admins.AdminManager;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.Meeting;
import Transactions.PastTransactionManager;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class UserMenuController{

    private TradingUser currentTradingUser; // user that's logged in
    private AdminManager am;
    private TradingUserManager um;
    private CurrentTransactionManager tm;
    private PastTransactionManager ptm;
    private ItemManager im;
    private Map<Item, TradingUser> allPendingItems;
    private UserMenuPresenter ump = new UserMenuPresenter();

    public UserMenuController(TradingUserManager tradingUserManager, AdminManager adminManager, CurrentTransactionManager currentTransactionManager,
                              ItemManager itemManager, Map<Item, TradingUser> pendingItems, TradingUser tradingUser) {
        currentTradingUser = tradingUser;
        allPendingItems = pendingItems;
        am = adminManager;
        um = tradingUserManager;
        tm = currentTransactionManager;
        im = itemManager;
        ptm = new PastTransactionManager(tm.getAllTransactions());
    }

    public void run() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while(userInteracting){
            List<String> menu = ump.constructMainMenu();
            int input = ump.handleOptionsByIndex(menu, false,"TradingUser Main Menu");
            if (ump.indexToOption(input, menu, ump.requestItem)){
                requestAddItem();
            } else if (ump.indexToOption(input, menu, ump.browseAvailableItems)) {
                DisplayAvailableItems();
            } else if (ump.indexToOption(input, menu, ump.viewActiveTransactions)) {
                getActiveTransactions();
            } else if (ump.indexToOption(input, menu, ump.viewPastTransactionDetails)) {
                viewPastTransaction();
            } else if (ump.indexToOption(input, menu, ump.viewWishlist)) {
                viewWishlist();
            } else if (ump.indexToOption(input, menu, ump.viewInventory)) {
                viewInventory();
            } else if (ump.indexToOption(input, menu, ump.requestUnfreeze)) {
                requestUnfreezeAccount();
            } else if (ump.indexToOption(input, menu, ump.logout)) {
                System.out.println(ump.successfulLogout());
                userInteracting = false;
            }
        }
    }

    /**
     * This takes in input from user and creates
     */
    private void requestAddItem(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ump.enterName("Item"));
        String itemName = scanner.nextLine();
        System.out.println("What is the description of this item?");
        String itemDescription = scanner.nextLine();
        Item requestedItem = new Item(itemName);
        requestedItem.setDescription(itemDescription);
        allPendingItems.put(requestedItem, currentTradingUser);
        System.out.println("Items has been requested and is now being reviewed by the administrators.");
    }

    /**
     * This is the method for handling the flow of:
     * 1) Displaying all available items to trade.
     * 2) Allowing the user to click on an item
     * 3) if the account is not frozen then the user can make a transaction for an item.
     * 4) if the account is not frozen but it breaches threshold criteria of system after the transaction, then
     * append the user to the admin's list of people that they can freeze.
     */
    private void DisplayAvailableItems(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);

        while(userInteracting){
            HashMap<Item, TradingUser> availableItems  = getAvailableItems();
            List<Item> itemList = new ArrayList<>(availableItems.keySet());

            List<String> optionList = ump.constructAvailableItemsMenu(itemList);
            int OptionChosen = ump.handleOptionsByIndex(optionList,true, "Available Items");
            // Logic handling back to other menu vs. your account is frozen vs proceed to make create transaction menu.
            if(OptionChosen == optionList.size()){
                System.out.println(ump.previousMenu);
                userInteracting = false;
            }
            else{
                if(currentTradingUser.isFrozen()){
                    System.out.println("Your account is frozen so you cannot make an offer for this item. Please request" +
                        "to have your account unfrozen.");
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                }
                else {
                    Item transactionItem = itemList.get(OptionChosen);
                    TradingUser transactionItemOwner = availableItems.get(transactionItem);
                    userInteracting = CreateTransactionMenu(transactionItem,transactionItemOwner);
                    if(//TODO put master threshold method here){
                        am.getPendingFrozenTradingUsers().add(currentTradingUser));
                    }
                }
            }
        }

    /**
     * This method handles the flow for setting up a transaction for an available item assuming that the transaction
     * is allowed between the 2 users.
     *
     * @param item The item that is going to be traded.
     * @param Owner The other user that is currently the owner of the item you want to trade for.
     * @return this method returns a true if the user wants to make another offer for an item and returns
     * false if the user wants to head back to the main menu.
     */
    private boolean CreateTransactionMenu(Item item, TradingUser Owner) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transactions Menu");
        System.out.println("----------------");
        System.out.println("You need to schedule a meeting time with the other user.");
        Meeting FirstMeeting = MeetingDetailsMenu("Meeting Details");
        if(!ump.handleYesNo("Would you like this transaction to be Permanent?")){
            System.out.println("You need to schedule a second meeting to reverse the transaction.");
            Meeting SecondMeeting = MeetingDetailsMenu("Second Meeting Details");
            if(ump.handleYesNo("Would you like to offer one of your items?")){


            }

        }

    }

    /**
     * This method walks the user through the details required for a meeting, then constructs a meeting.
     * @param MeetingTitle The first thing that will be displayed "Second Transactions.Meeting Details"/"First Transactions.Meeting Details"
     */
    private Meeting MeetingDetailsMenu(String MeetingTitle){
        Scanner scanner = new Scanner(System.in);
        System.out.println(MeetingTitle);
        System.out.println("Where do you want to have the meeting?");
        String MeetingLocation = scanner.nextLine();
        LocalTime MeetingTime = ump.inputTimeGetter(ump.enterWhatInFormat("time", "HH:mm:ss"));
        LocalDate MeetingDate = ump.inputDateGetter(ump.enterWhatInFormat("date", "dd-mm-yyyy"));
        return new Meeting(MeetingLocation,MeetingTime,MeetingDate);
    }

    private void viewWishlist(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while (userInteracting) {
            if (currentTradingUser.getWishlist().isEmpty()) {
                ump.empty("Wishlist");
                userInteracting = false;
            } else {
                int itemChosen = ump.handleOptionsByIndex(ump.constructWishlistItemsList(currentTradingUser), true, "Wishlist Items");
                if (itemChosen == ump.constructWishlistItemsList(currentTradingUser).size()) {
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                } else {
                    String item = ump.constructWishlistItemsList(currentTradingUser).get(itemChosen);
                    System.out.println(ump.itemOptionList());
                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Wishlist Menu");
                    if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
                        Item whichItem = currentTradingUser.getWishlist().get(itemChosen);
                        um.removeItem(currentTradingUser, whichItem, "wishlist");
                        System.out.println(ump.successfullyRemoved(item,"wishlist"));
                }}}}}

    private void viewInventory(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while (userInteracting) {
            if (currentTradingUser.getInventory().isEmpty()) {
                ump.empty("Inventory");
                userInteracting = false;
            } else {
                int itemChosen = ump.handleOptionsByIndex(ump.constructInventoryItemsList(currentTradingUser), true, "Inventory Items");
                if (itemChosen == ump.constructInventoryItemsList(currentTradingUser).size()) {
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                } else {
                    String item = ump.constructInventoryItemsList(currentTradingUser).get(itemChosen);
                    System.out.println(ump.itemOptionList());
                    int optionChosen = ump.handleOptionsByIndex(ump.itemOptionList(), true, "Inventory Menu");
                    if(ump.indexToOption(optionChosen, ump.itemOptionList(), ump.removeItem)){
                        Item whichItem = currentTradingUser.getInventory().get(itemChosen);
                        um.removeItem(currentTradingUser, whichItem, "inventory");
                        System.out.println(ump.successfullyRemoved(item,"inventory"));
                    }}}}}

    private void requestUnfreezeAccount() {
        if (currentTradingUser.isFrozen()) {
            am.getPendingFrozenTradingUsers().add(currentTradingUser);
            am.getFrozenAccounts().remove(currentTradingUser);
            System.out.println("You have successfully requested for your account to be unfrozen.");
        } else {
            System.out.println("Your account is not frozen.");
        }
    }

    private void viewPastTransaction(){
        TransactionHistory transactionHistory= currentTradingUser.getTransactionHistory();
        if (transactionHistory == null){
            System.out.println(ump.empty("Transaction History"));
        } else {
            System.out.println(transactionHistory.toString());
        }
    }

    /**
     * This method displays all of the active transactions for TradingUser and then redirects the user to either edit the Meetings
     * for that transaction or to change the statusUser of the Transaction
     */
    private void getActiveTransactions() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        TradingUser tradingUser = currentTradingUser;

        while (userInteracting) {
            List<UUID> currentTransactionsIds = tradingUser.getCurrentTransactions();

            ArrayList<Transaction> currTransactionsList = tm.getTransactionsFromIdList(currentTransactionsIds);

            List<String> optionList = ump.constructTransactionList(currTransactionsList);
            String currTransactionsTitle = "List of Current Transaction";
            int OptionChosen = ump.handleOptionsByIndex(optionList, true, currTransactionsTitle
            );
            // Logic handling back to other menu vs. Editing a meeting vs changing the StatusUser of a Transaction.
            if (OptionChosen == optionList.size()) {
                System.out.println(ump.previousMenu);
                userInteracting = false;
            } else {
                Transaction transaction = currTransactionsList.get(OptionChosen);
                ArrayList<String> transactionActions = tm.userTransactionActions(transaction);
                String transactionActionPrompt = "This is the list of actions that you can do with your transaction";
                int optionChosen2 = ump.handleOptionsByIndex(transactionActions, true, transactionActionPrompt);
                if (tm.updateStatusUser(currentTradingUser, transaction, transactionActions.get(optionChosen2))) {
                    tm.updateStatus(transaction);
                    um.addToTransactionHistory(currentTradingUser, transaction);
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                } else {
                    editMeeting(currentTradingUser, transaction);
                    System.out.println(ump.previousMenu);
                    userInteracting = false;
                }
            }
        }
    }

    /**
     * Returns a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    public HashMap<Item, TradingUser> getAvailableItems(){
        List<TradingUser> allTradingUsers = um.getAllTradingUsers();
        HashMap<Item, TradingUser> availableItems = new HashMap<>();
        for (TradingUser tradingUser : allTradingUsers) {
            if(!tradingUser.equals(currentTradingUser)) {
                for (Item item : tradingUser.getInventory()) {
                    availableItems.put(item, tradingUser);
                }
            }
        }
        return availableItems;
    }

    /**
     * Changes a Transactions.Transaction status to cancelled
     * @param transaction A transaction to be cancelled and to remove transaction from tra
     */
    public void cancelTransaction(Transaction transaction)  {
        currentTradingUser.getCurrentTransactions().getUsersTransactions().remove(transaction);
        TradingUser u =  um.getTradingUserById(transaction.getUser1());
        if (u == currentTradingUser){
            transaction.setStatusUser1("cancel");
        }
        else{
            transaction.setStatusUser2("cancel");
        }
        tm.updateStatus(transaction);
    }

    /**
     * Creates a Transactions.Transaction and adds it to users
     * adds the Transactions.Transaction to transaction details of both users
     * @param targetTradingUser The Users.TradingUser to whom currUser sends a Transactions.Transaction
     */
    public void createTransaction(TradingUser targetTradingUser){
        //TODO: method body
    }

    /**
     * Changes status of a Transactions.Transaction to confirmed, when details of all meetings have been confirmed by both users.
     * @param transaction the transaction to be confirmed
     */
    public void acceptTransaction(Transaction transaction) {
        transaction.setStatus("confirmed");
    }

    /**
     * Changes status of a Transactions.Transaction to completed, when the last meeting of the transaction has occurred and been completed by both users.
     * @param transaction the transaction to be completed
     */
    public void confirmTransaction(Transaction transaction) {
        transaction.setStatus("completed");
        TradingUser tradingUser1 = um.getTradingUserById(transaction.getUser1());
        TradingUser tradingUser2 = um.getTradingUserById(transaction.getUser2());
        um.addToTransactionHistory(tradingUser1, transaction);
        um.addToTransactionHistory(tradingUser2, transaction);
        tradingUser1.getCurrentTransactions().remove(transaction); // Is the transaction in both user's "sent offers"?
        tradingUser2.getCurrentTransactions().remove(transaction);
    }

    /**
     * Deletes a transaction that is in progress
     */
    public void deleteTransaction(Transaction transaction){
        // TODO: Method Body once I confirm some things about the details of this method
    }

    public ArrayList<Transaction> getTransactionList(List<UUID> idList) throws InvalidTransactionException {
        return tm.getTransactionsFromIdList(idList); //(from Casey) if this method is just one line, consider not making it a helper method
    }

    private void editMeeting(TradingUser currentTradingUser, Transaction transaction) {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        UUID user = currentTradingUser.getUserId();
        int meetingNum = 1;

        while (userInteracting) {
            if (!tm.transactionHasMultipleMeetings(transaction)){
                meetingNum = pickMeetingToEdit();}

            Meeting meeting = transaction.getTransactionMeetings().get(meetingNum - 1);
            System.out.println("This is the meeting you wish to edit " + meeting.toString());
            List<String> options = ump.constructEditMeetingOptions();
            int OptionChosen = ump.handleOptionsByIndex(options, true, "Meeting Options");
            // Logic handling back to other menu vs. Editing a meeting
            if (OptionChosen == options.size()) {
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            } else {
                switch (OptionChosen) {
                    case 0:
                      editMeetingLocationFlow(user,transaction,meetingNum);
                        break;
                    case 1:
                       editMeetingTimeFlow(user,transaction,meetingNum);
                        break;
                    default:
                        editMeetingDateFlow(user,transaction,meetingNum);
                }
            }
        }
    }

    private void editMeetingLocationFlow(UUID user, Transaction transaction,int meetingNum){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Where do you want to have the meeting?");
        String MeetingLocation = scanner.nextLine();
        tm.editMeeting(meetingNum, transaction, user, MeetingLocation);
        System.out.println(ump.successfullyEditedMeeting(MeetingLocation));
    }

    private void editMeetingTimeFlow(UUID user, Transaction transaction,int meetingNum){
        Scanner scanner = new Scanner(System.in);
        LocalTime MeetingTime = ump.inputTimeGetter(ump.enterWhatInFormat("time","HH:mm:ss"));
        tm.editMeeting(meetingNum, transaction, user, MeetingTime);
        System.out.println(ump.successfullyEditedMeeting(MeetingTime.toString()));
    }

    private void editMeetingDateFlow(UUID user, Transaction transaction,int meetingNum){
        Scanner scanner = new Scanner(System.in);
        LocalDate MeetingDate = ump.inputDateGetter(ump.enterWhatInFormat("date", "dd-mm-yyyy"));
        tm.editMeeting(meetingNum, transaction, user, MeetingDate);
        System.out.println(ump.successfullyEditedMeeting(MeetingDate.toString()));
    }

    private int pickMeetingToEdit(){
        List<String> meetNum = new ArrayList<String>(Arrays.asList("Edit first meeting", "Edit second meeting"));
        String meetNumTitle = "This transaction has two meetings";
        int num = ump.handleOptionsByIndex(meetNum, true, meetNumTitle);
        return num + 1; //this is because we can either have meeting one or meeting two but index of list starts from 0
    }
}