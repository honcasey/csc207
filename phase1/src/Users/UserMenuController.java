package Users;

import Admins.AdminManager;
import Exceptions.InvalidTransactionException;
import Items.Item;
import Items.ItemManager;
import Transactions.Meeting;
import Transactions.Transaction;
import Transactions.TransactionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserMenuController{
    /**
     *
     */
    private User currentUser; // user that's logged in
    private AdminManager am;
    private UserManager um;
    private TransactionManager tm;
    private ItemManager im;
    private Map<Item, User> allPendingItems;
    private UserMenu userMenu = new UserMenu();
    private UserMenuPresenter userMenuPresenter = new UserMenuPresenter();

    public UserMenuController(UserManager userManager, AdminManager adminManager, TransactionManager transactionManager,
                    ItemManager itemManager, Map<Item, User> pendingItems, User user) {
        currentUser = user;
        allPendingItems = pendingItems;
        am = adminManager;
        um = userManager;
        tm = transactionManager;
        im = itemManager;
    }

    public void run() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while(userInteracting){
        String input = this.userMenuPresenter.handleOptions(this.userMenuPresenter.constructMainMenu(),
                false,"User Main Menu");
            if (input.equals("Request Items.Item for Approval")){
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
        this.userMenu.requestAddItemInput(itemName,itemDescription);
        System.out.println("Items.Item has been requested and is now being reviewed by the administrators.");
    }

    /**
     * This is the method for handling the flow of:
     * 1) Displaying all available items to trade.
     * 2) Allowing the user to click on an item
     * 3) if the account is not frozen then the user can make a transaction for an item.
     */
    private void DisplayAvailableItems(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);

        while(userInteracting){
            HashMap<Item, User> AvailableItems  = this.userMenu.getAvailableItems();
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
                userMenu.withdrawItem(currentUser.getWishlist().get(optionChosen), "wishlist");
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
                userMenu.withdrawItem(currentUser.getInventory().get(optionChosen), "inventory");
                System.out.println("The item has been removed from your inventory.");
            }
        }
    }

    private void requestUnfreezeAccount() {
        if (currentUser.isFrozen()) {
            userMenu.requestUnfreezeAccount();
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

        while(userInteracting){
            List <UUID> currentTransactionsIds  = user.getCurrentTransactions();
            ArrayList<Transaction> currTransactionsList = userMenu.getTransactionList(currentTransactionsIds);

            List<String> OptionList = this.userMenuPresenter.constructAvailableItemsMenu(ItemList);
            String AvailableItemsTitle = "List of Current Transaction:";
            String AvailableItemsPrompt = "Type the number corresponding to the transaction you wish to" +
                    " modify. To go back to the previous menu, type the number corresponding to that" +
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
                }
    }

    /**
     * TODO: this is the method where the user can edit their statusUser for their transactions
     */
    private void changeTransactionStatus(){

    }
}

