package Users;

import Admins.AdminManager;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.Meeting;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;
import com.sun.xml.internal.bind.v2.TODO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class UserMenuController{
    /**
     *
     */
    private TradingUser currentTradingUser; // user that's logged in
    private AdminManager am;
    private UserManager um;
    private CurrentTransactionManager tm;
    private ItemManager im;
    private Map<Item, TradingUser> allPendingItems;
    private UserMenuPresenter userMenuPresenter = new UserMenuPresenter();

    public UserMenuController(UserManager userManager, AdminManager adminManager, CurrentTransactionManager currentTransactionManager,
                              ItemManager itemManager, Map<Item, TradingUser> pendingItems, TradingUser tradingUser) {
        currentTradingUser = tradingUser;
        allPendingItems = pendingItems;
        am = adminManager;
        um = userManager;
        tm = currentTransactionManager;
        im = itemManager;
    }

    public void run() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while(userInteracting){
        String input = this.userMenuPresenter.handleOptions(this.userMenuPresenter.constructMainMenu(),
                false,"TradingUser Main Menu");
            if (input.equals("Request Items for Approval")){
                requestAddItem();
            } else if (input.equals("Browse Available Items for Trade")) {
                DisplayAvailableItems();
            } else if (input.equals("View Active Transactions")) {
                //This Still needs to be done. this.userMenu.getActiveTransactions();
            } else if (input.equals("View Past Transactions.Transaction Details")) {
                viewPastTransaction();
            } else if (input.equals("View Wishlist")) {
                viewWishlist();
            } else if (input.equals("View Inventory")) {
                viewInventory();
            } else if (input.equals("Request Admin to Unfreeze Account")) {
                requestUnfreezeAccount();
            } else if (input.equals("Log Out")) {
                System.out.println("You have successfully logged out.");
                userInteracting = false;
            }
        }
    }

    /**
     * This takes in input from user and creates
     */
    private void requestAddItem(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the name of your item?");
        String itemName = scanner.nextLine();
        System.out.println("What is the description of this item?");
        String itemDescription = scanner.nextLine();
        Item RequestedItem = this.createItemflow();
        allPendingItems.put(RequestedItem, currentTradingUser);
        System.out.println("Items has been requested and is now being reviewed by the administrators.");
    }

    /**
     * This is a helper method to other methods in this class.This method handles the flow of asking:
     * (1)What is the name of your item.
     * (2)What is the description of your item.
     * (2)constructing and returning an item.
     * @return the item that has been constructed from the user input.
     */
    private Item createItemflow(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the name of your item?");
        String itemName = scanner.nextLine();
        System.out.println("What is the description of this item?");
        String itemDescription = scanner.nextLine();
        Item returnItem = new Item(itemName);
        returnItem.setDescription(itemDescription);
        return(returnItem);
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
            HashMap<Item, TradingUser> AvailableItems  = getAvailableItems();
            List<Item> ItemList = new ArrayList<>(AvailableItems.keySet());

            List<String> OptionList = this.userMenuPresenter.constructAvailableItemsMenu(ItemList);
            String AvailableItemsTitle = "Available Items For Transaction:";
            String AvailableItemsPrompt = "Type the number corresponding to the item you wish to" +
                " create a transaction for. To go back to the previous menu, type the number corresponding to that" +
                "option.";

            int OptionChosen = this.userMenuPresenter.handleOptionsByIndex(OptionList,true,AvailableItemsPrompt
            );
            // Logic handling back to other menu vs. your account is frozen vs proceed to make create transaction menu.
            if(OptionChosen == OptionList.size()){
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            }
            else{
                if(currentTradingUser.isFrozen()){
                    System.out.println("Your account is frozen so you cannot make an offer for this item. Please request" +
                        "to have your account unfrozen.");
                    System.out.println("You will now be taken back to the main user menu.");
                    userInteracting = false;
                }
                else {
                    Item TransactionItem = ItemList.get(OptionChosen);
                    TradingUser TransactionItemOwner = AvailableItems.get(TransactionItem);
                    userInteracting = CreateTransactionMenu(TransactionItem,TransactionItemOwner);
                    if(//TODO put master threshold method here){
                        this.am.getPendingFrozenTradingUsers().add(this.currentTradingUser);
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
        if(!this.userMenuPresenter.handleYesNo("Would you like this transaction to be Permanent?")){
            System.out.println("You need to schedule a second meeting to reverse the transaction.");
            Meeting SecondMeeting = MeetingDetailsMenu("Second Meeting Details");
            if(this.userMenuPresenter.handleYesNo("Would you like to offer one of your items?")){


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
        LocalTime MeetingTime = this.userMenuPresenter.inputTimeGetter("Please Enter the time of your meeting in the" +
                " format: HH:mm:ss");
        LocalDate MeetingDate = this.userMenuPresenter.inputDateGetter("Please Enter the date of your meeting in the" +
                " format: dd-mm-yyyy");
        return new Meeting(MeetingLocation,MeetingTime,MeetingDate);
    }

    private void viewWishlist(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while (userInteracting) {
            if (currentTradingUser.getWishlist().isEmpty()) {
                userMenuPresenter.empty("Wishlist");
                userInteracting = false;
            } else {
                Iterator<Item> itemIterator = currentTradingUser.getWishlist().iterator();
                List<String> optionList = new ArrayList<>();
                optionList.add("Remove item from wishlist.");
                optionList.add("Go to next item.");

                while(itemIterator.hasNext()){
                    System.out.println(itemIterator.next().toString());
                    int optionChosen =this.userMenuPresenter.handleOptionsByIndex(optionList, true, "Wishlist Menu");
                    if(optionChosen == optionList.size()){
                        System.out.println("Loading Previous Menu");
                        userInteracting = false;
                    }
                    else if(this.userMenuPresenter.indexToOption(optionChosen, optionList, "Remove item from wishlist.")){
                        withdrawItem(itemIterator.next(), "wishlist");
                        System.out.println(userMenuPresenter.successfullyRemoved(itemIterator.next().toString(),"wishlist"));
                    }
                }
            }
        }
    }


    private void viewInventory(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while (userInteracting) {
            if (currentTradingUser.getInventory().isEmpty()) {
                userMenuPresenter.empty("Inventory");
                userInteracting = false;
            } else {
                Iterator<Item> itemIterator = currentTradingUser.getInventory().iterator();
                List<String> optionList = new ArrayList<>();
                optionList.add("Remove item from inventory.");
                optionList.add("Go to next item.");

                while(itemIterator.hasNext()){
                    System.out.println(itemIterator.next().toString());
                    int optionChosen =this.userMenuPresenter.handleOptionsByIndex(optionList, true, "Inventory Menu");
                    if(optionChosen == optionList.size()){
                        System.out.println("Loading Previous Menu");
                        userInteracting = false;
                    }
                    else if(this.userMenuPresenter.indexToOption(optionChosen, optionList, "Remove item from inventory.")){
                        withdrawItem(itemIterator.next(), "inventory");
                        System.out.println(userMenuPresenter.successfullyRemoved(itemIterator.next().toString(),"inventory"));
                    }
                }
            }
        }
    }

    private void requestUnfreezeAccount() {
        if (currentTradingUser.isFrozen()) {
            requestUnfreezeAccount();
            System.out.println("You have successfully requested for your account to be unfrozen.");
        }
        else {
            System.out.println("Your account is not frozen.");
        }
    }

    private void viewPastTransaction(){
        TransactionHistory transactionHistory= currentTradingUser.getTransactionHistory();
        if (transactionHistory == null){
            System.out.println("Your transaction history is empty.");
        }
        else {
            System.out.println(transactionHistory.toString());
        }
    }

    /**
     * This method displays all of the active transactions for TradingUser and then redirects the user to either edit the Meetings
     * for that transaction or to change the statusUser of the Transaction
     */
    private void getActiveTransactions() throws InvalidTransactionException {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        TradingUser tradingUser = currentTradingUser;

        while (userInteracting) {
            List<UUID> currentTransactionsIds = tradingUser.getCurrentTransactions();
            ArrayList<Transaction> currTransactionsList = tm.getTransactionsFromIdList(currentTransactionsIds);

            List<String> optionList = this.userMenuPresenter.constructTransactionList(currTransactionsList);
            String currTransactionsTitle = "List of Current Transaction:";
            String currTransactionsPrompt = "Type the number corresponding to the transaction you wish to" +
                    " modify. To go back to the previous menu, type the number corresponding to that" +
                    "option.";

            int OptionChosen = this.userMenuPresenter.handleOptionsByIndex(optionList, true, currTransactionsPrompt
            );
            // Logic handling back to other menu vs. Editing a meeting vs changing the StatusUser of a Transaction.
            if (OptionChosen == optionList.size()) {
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            } else {
                Transaction transaction = currTransactionsList.get(OptionChosen);
                ArrayList<String> transactionActions = tm.userTransactionActions(transaction);
                String transactionActionPrompt = "This is the list of actions that you can do with your transaction"
                int optionChosen2 = this.userMenuPresenter.handleOptionsByIndex(transactionActions, true, transactionActionPrompt);
                if (tm.updateStatusUser(currentTradingUser, transaction, transactionActions.get(optionChosen2))) {
                    tm.updateStatus(transaction);
                    um.addToTransactionHistory(currentTradingUser, transaction);
                    System.out.println("Loading Previous Menu");
                    userInteracting = false;
                } else {
                    this.editMeeting(currentTradingUser, transaction);
                    System.out.println("Loading Previous Menu");
                    userInteracting = false;
                }
            }
        }
    }

    /**
     * To withdraw item from user's specified list, which is either the Users.TradingUser's wishlist or inventory.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void withdrawItem(Item item, String listType){
        if(listType.equals("wishlist")){
            um.removeItem(currentTradingUser, item, "wishlist");
        }else if (listType.equals("inventory")){
            um.removeItem(currentTradingUser,item,"inventory");
        }
    }

    /**
     * To add an given item to user's wishlist
     * @param item An item in the trading system
     */
    public void addToWishlist(Item item){
        um.addItem(currentTradingUser, item, "wishlist");
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
        TradingUser u =  um.getUserById(transaction.getUser1());
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
        TradingUser tradingUser1 = um.getUserById(transaction.getUser1());
        TradingUser tradingUser2 = um.getUserById(transaction.getUser2());
        um.addToTransactionHistory(tradingUser1, transaction);
        um.addToTransactionHistory(tradingUser2, transaction);
        tradingUser1.getCurrentTransactions().remove(transaction); // Is the transaction in both user's "sent offers"?
        tradingUser2.getCurrentTransactions().remove(transaction);
    }

    /**
     * Requests the admin user to unfreeze the current user's account, if it's status is already frozen.
     */
    public void requestUnfreezeAccount(){
        am.getPendingFrozenTradingUsers().add(currentTradingUser);
        am.getFrozenAccounts().remove(currentTradingUser);
    }

    /**
     * Deletes a transaction that is in progress
     */
    public void deleteTransaction(Transaction transaction){
        // TODO: Method Body once I confirm some things about the details of this method
    }

    public ArrayList<Transaction> getTransactionList(List<UUID> idList){
        return tm.getTransactionsFromIdList(idList);
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
            List<String> options = this.userMenuPresenter.constructEditMeetingOptions();
            String optionsTitle = "You can edit one of the following options:";
            String optionsPrompt ="Type the number corresponding to the transaction you wish to" +
                    " modify." + this.userMenuPresenter.getToGoBackPrompt();

            int OptionChosen = this.userMenuPresenter.handleOptionsByIndex(options, true, optionsPrompt);
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
        System.out.println("You have successfully edited your meeting to be at " + MeetingLocation);
    }

    private void editMeetingTimeFlow(UUID user, Transaction transaction,int meetingNum){
        Scanner scanner = new Scanner(System.in);
        LocalTime MeetingTime = this.userMenuPresenter.inputTimeGetter("Please Enter the time of your meeting in the" +
                " format: HH:mm:ss");
        tm.editMeeting(meetingNum, transaction, user, MeetingTime);
        System.out.println("You have successfully edited your meeting to be at " + MeetingTime);
    }
    private void editMeetingDateFlow(UUID user, Transaction transaction,int meetingNum){
        Scanner scanner = new Scanner(System.in);
        LocalDate MeetingDate = this.userMenuPresenter.inputDateGetter("Please Enter the date of your meeting in the" +
                " format: dd-mm-yyyy");
        tm.editMeeting(meetingNum, transaction, user, MeetingDate);
        System.out.println("You have successfully edited your meeting to be at " + MeetingDate);
    }

    private int pickMeetingToEdit(){

        ArrayList<String> meetNum = new ArrayList<String>(Arrays.asList("Edit first meeting", "Edit second meeting"));
        String meetNumTitle = "This transaction has two meetings";
        String meetNumPrompt = "Type the number corresponding to the meeting you wish to" +
                " modify. To go back to the previous menu, type the number corresponding to that" +
                "option.";
        int num = this.userMenuPresenter.handleOptionsByIndex(meetNum, true, meetNumPrompt);
        return num + 1; //this is because we can either have meeting one or meeting two but index of list starts from 0
    }
}