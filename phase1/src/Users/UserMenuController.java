package Users;

import Admins.AdminManager;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.Meeting;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class UserMenuController{
    /**
     *
     */
    private User currentUser; // user that's logged in
    private AdminManager am;
    private UserManager um;
    private CurrentTransactionManager tm;
    private ItemManager im;
    private Map<Item, User> allPendingItems;
    private UserMenuPresenter userMenuPresenter = new UserMenuPresenter();

    public UserMenuController(UserManager userManager, AdminManager adminManager, CurrentTransactionManager currentTransactionManager,
                    ItemManager itemManager, Map<Item, User> pendingItems, User user) {
        currentUser = user;
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
                false,"User Main Menu");
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
        requestAddItemInput(itemName,itemDescription);
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
            HashMap<Item, User> AvailableItems  = getAvailableItems();
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
                if(currentUser.isFrozen()){
                    System.out.println("Your account is frozen so you cannot make an offer for this item. Please request" +
                        "to have your account unfrozen.");
                    System.out.println("You will now be taken back to the main user menu.");
                    userInteracting = false;
                }
                else {
                    Item TransactionItem = ItemList.get(OptionChosen);
                    User TransactionItemOwner = AvailableItems.get(TransactionItem);
                    userInteracting = CreateTransactionMenu(TransactionItem,TransactionItemOwner);
                    if(//TODO put master threshold method here){
                        this.am.getPendingFrozenUsers().add(this.currentUser);
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
    private boolean CreateTransactionMenu(Item item, User Owner) {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;
        while (userInteracting) {
            System.out.println("Transactions.Transaction Menu");
            System.out.println("----------------");
            List<String> transOptionList = this.userMenuPresenter.constructTransactionTypeMenu();
            String OptionChosen = this.userMenuPresenter.handleOptions(transOptionList, true, ""
            );
            if (OptionChosen.equals("back")) {
                System.out.println("Loading Previous Menu");
                userInteracting = false;

            }
            else{
                Meeting FirstMeeting = MeetingDetailsMenu("First Meeting Details");
                if(OptionChosen.equals()){

                }

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

    private void viewWishlist() {
        Scanner scanner = new Scanner(System.in);
        if (currentUser.getWishlist() == null) {
            System.out.println("Your wishlist is empty.");
        }
        else {
            Iterator<Item> itemIterator = currentUser.getWishlist().iterator();
            List<String> optionList = new ArrayList<>();
            while (itemIterator.hasNext()) {
                optionList.add(itemIterator.next().toString());
                System.out.println(itemIterator.next().toString());
            }
            int optionChosen =this.userMenuPresenter.handleOptionsByIndex(optionList, true, "Wishlist Menu"
            );
            if (optionChosen == optionList.size()) {
                System.out.println("Loading Previous Menu");
            }
            else {
                withdrawItem(currentUser.getWishlist().get(optionChosen), "wishlist");
                System.out.println("The item has been removed from your wishlist.");
            }
        }
    }

    private void viewInventory() {
        Scanner scanner = new Scanner(System.in);
        if (currentUser.getInventory() == null) {
            System.out.println("Your inventory is empty.");
        }
        else {
            Iterator<Item> itemIterator = currentUser.getInventory().iterator();
            List<String> optionList = new ArrayList<>();
            while (itemIterator.hasNext()) {
                optionList.add(itemIterator.next().toString());
            } // TO-DO: can this be shortened to add all the items at once in one line?
            int optionChosen =this.userMenuPresenter.handleOptionsByIndex(optionList, true,
                    "Inventory Menu");
            if (optionChosen == optionList.size()) {
                System.out.println("Loading Previous Menu");
            }
            else {
                withdrawItem(currentUser.getInventory().get(optionChosen), "inventory");
                System.out.println("The item has been removed from your inventory.");
            }
        }
    }

    private void requestUnfreezeAccount() {
        if (currentUser.isFrozen()) {
            requestUnfreezeAccount();
            System.out.println("You have successfully requested for your account to be unfrozen.");
        }
        else {
            System.out.println("Your account is not frozen.");
        }
    }

    private void viewPastTransaction(){
        TransactionHistory transactionHistory= currentUser.getTransactionHistory();
        if (transactionHistory == null){
            System.out.println("Your transaction history is empty.");
        }
        else {
            System.out.println(transactionHistory.toString());
        }
    }

    /**
     * TODO: This method should display all the transaction that are in progress for the user
     */
    private void getActiveTransactions() throws InvalidTransactionException {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        User user = currentUser;

        while (userInteracting) {
            List<UUID> currentTransactionsIds = user.getCurrentTransactions();
            ArrayList<Transaction> currTransactionsList = tm.getTransactionsFromIdList(currentTransactionsIds);

            List<String> optionList = this.userMenuPresenter.constructTransactionList(currTransactionsList);
            String currTransactionsTitle = "List of Current Transaction:";
            String currTransactionsPrompt = "Type the number corresponding to the transaction you wish to" +
                    " modify. To go back to the previous menu, type the number corresponding to that" +
                    "option.";

            int OptionChosen = this.userMenuPresenter.handleOptionsByIndex(optionList, true, currTransactionsPrompt
            );
            // Logic handling back to other menu vs. your account is frozen vs proceed to make create transaction menu.
            if (OptionChosen == optionList.size()) {
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            } else {
                    Transaction transaction = currTransactionsList.get(OptionChosen);
                    ArrayList<String> transactionactions = tm.userTransactionActions(transaction);


                }
            }
        }
    }

    /**
     * TODO: this is the method where the user can edit their statusUser for their transactions
     */
    private void changeTransactionStatus() {}

    /**
     * This helper method constructs a new instance of item from user input then adds the item to the pending items list.
     * @param itemName the name of the item to be requested.
     * @param itemDescription this is the description of the item.
     */
    public void requestAddItemInput(String itemName, String itemDescription){
        Item RequestedItem = new Item(itemName);
        RequestedItem.setDescription(itemDescription);
        allPendingItems.put(RequestedItem,currentUser);
    }

    /**
     * To withdraw item from user's specified list, which is either the Users.User's wishlist or inventory.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void withdrawItem(Item item, String listType){
        if(listType.equals("wishlist")){
            um.removeItem(currentUser, item, "wishlist");
        }else if (listType.equals("inventory")){
            um.removeItem(currentUser,item,"inventory");
        }
    }

    /**
     * To add an given item to user's wishlist
     * @param item An item in the trading system
     */
    public void addToWishlist(Item item){
        um.addItem(currentUser, item, "wishlist");
    }

    /**
     * Returns a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    public HashMap<Item,User> getAvailableItems(){
        List<User> allUsers = um.getAllUsers();
        HashMap<Item,User> availableItems = new HashMap<>();
        for (User user:allUsers) {
            if(!user.equals(currentUser)) {
                for (Item item : user.getInventory()) {
                    availableItems.put(item, user);
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
        currentUser.getCurrentTransactions().getUsersTransactions().remove(transaction);
        User u =  um.getUserById(transaction.getUser1());
        if (u == currentUser){
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
     * @param targetUser The Users.User to whom currUser sends a Transactions.Transaction
     */
    public void createTransaction(User targetUser){
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
        User user1 = um.getUserById(transaction.getUser1());
        User user2 = um.getUserById(transaction.getUser2());
        um.addToTransactionHistory(user1, transaction);
        um.addToTransactionHistory(user2, transaction);
        user1.getCurrentTransactions().getUsersTransactions().remove(transaction); // Is the transaction in both user's "sent offers"?
        user2.getCurrentTransactions().getUsersTransactions().remove(transaction);
    }

    /**
     * Requests the admin user to unfreeze the current user's account, if it's status is already frozen.
     */
    public void requestUnfreezeAccount() {
        am.getPendingFrozenUsers().add(currentUser);
        am.getFrozenAccounts().remove(currentUser);
    }

    /**
     * Deletes a transaction that is in progress
     */
    public void deleteTransaction(Transaction transaction){
        // TODO: Method Body once I confirm some things about the details of this method
    }

    public ArrayList<Transaction> getTransactionList(List<UUID> idList) throws InvalidTransactionException {
        return tm.getTransactionsFromIdList(idList);
    }



}