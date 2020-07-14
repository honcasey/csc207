package Users;

import Items.Item;
import Presenters.MenuPresenter;
import Transactions.Transaction;
import java.util.*;

/**
 * This Presenter class contains helper methods that will be used to format and output the prompts that a user sees.
 *
 * Class variables representing common strings that users will see as prompts will be here.
 *
 * Methods that combine these common strings will be here.
 *
 * Methods that print output(Phase 1)/ display stuff(Phase 2) will be here.
 *
 * Class variables:
 *
 * Users.UserMenu: Instance of Users.UserMenu class.
 * menuOptions: This is a dictionary mapping the menu to the options that a user can Select.
 */

public class UserMenuPresenter extends MenuPresenter {

    //option strings
    protected String removeItem = "Remove item.";
    protected String requestItem = "Request Items for Approval";
    protected String browseAvailableItems = "Browse Available Items for Trade";
    protected String viewActiveTransactions = "View Active Transactions";
    protected String viewPastTransactionDetails = "Past Transactions";
    protected String viewWishlist = "View Wishlist";
    protected String viewInventory = "View Inventory";
    protected String requestUnfreeze = "Request Admin to Unfreeze Account";
    protected String ViewRecentThreeOneWay = "View 3 most recent one way past transactions.";
    protected String ViewRecentThreeTwoWay = "View 3 most recent two way past transactions."; // TO-DO: these 3 things could be combined into one method
    protected String ViewThreeMostTraded = "View 3 most frequent trading partners.";
    protected String itemRequested = "Items has been requested and is now being reviewed by the administrators.";
    protected String requestAccountUnfreeze = "You cannot make an offer for this item. Please request to have your account unfrozen.";
    protected String scheduleMeeting = "Transactions Menu \nYou need to schedule a meeting time with the other user.";
    protected String scheduleSecondMeeting = "You need to schedule a second meeting to reverse the transaction."; // what did you mean by "reverse" the transaction?
    protected String offerItem = "Would you like to offer one of your items?";
    protected String makeTransaction = "Would you like to make another transaction?";
    protected String meetingLocation = "Where do you want to have the meeting?";
    protected String requestedUnfreeze = "You have successfully requested for your account to be unfrozen.";
    protected String transactionActions = "List of actions that you can do with your transaction:";
    protected String enterLocation = "Where do you want to have the meeting?";
    protected String noInventoryItems = "There are no Items in your inventory.";
    protected String addToWishlist = "Would you like to add this item to your wishlist?";
    protected String createTransaction = "create transaction.";
    /**
     * Construct methods like this return a list of options/prompts that the menu will have.
     *
     * This particular method constructs the option list that the user will be greeted with upon first logging into
     * the program.
     * @return this returns a list of options that the user can choose from.
     */
    public List<String> constructMainMenu(){
        List<String> MenuOptionList = new ArrayList<>();
        MenuOptionList.add(requestItem);
        MenuOptionList.add(browseAvailableItems);
        MenuOptionList.add(viewActiveTransactions);
        MenuOptionList.add(viewPastTransactionDetails);
        MenuOptionList.add(viewWishlist);
        MenuOptionList.add(viewInventory);
        MenuOptionList.add(requestUnfreeze);
        MenuOptionList.add(logout);
        return(MenuOptionList);
    }

    public List<String> constructPastTransactionMenu(){
        List<String> MenuOptionList = new ArrayList<>();
        MenuOptionList.add(ViewRecentThreeOneWay);
        MenuOptionList.add(ViewRecentThreeTwoWay);
        MenuOptionList.add(ViewThreeMostTraded);
        return(MenuOptionList);
    }

    /**
     * Construct methods like this will return a list of options/prompts that the menu will have.
     * @param ItemList this is a list of items(all available items that are for trade from other users.)
     * @return this returns the list of options that the user can choose from.
     */
    public List<String> constructAvailableItemsMenu(List<Item> ItemList){
        // Making Option List
        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Item item : ItemList) {
            AvailableItemOptionList.add(" Item Name: " + item.getName() + " |  Item Description: " + item.getDescription());
        }
        return(AvailableItemOptionList);
    }

    /**
     * Construct methods like this will return a list of options/prompts that the menu will have.
     * @return This returns a list of all the possible ways to edit meeting options options.
     */

    public List<String> constructEditMeetingOptions(){
        List<String> OptionList = new ArrayList<>();
        OptionList.add("Edit Location");
        OptionList.add("Edit Time");
        OptionList.add("Edit Date");
        return(OptionList);
    }

    public List<String> constructTransactionList(List<Transaction> TransactionList){
        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Transaction transaction : TransactionList) {
            AvailableItemOptionList.add(transaction.toString());
        }
        return(AvailableItemOptionList);
    }

    public List<String> constructWishlistItemsList(List<Item> items) {
        List<String> wishlistItems = new ArrayList<>();
        for (Item item : items) {
            wishlistItems.add(item.toString());
        }
        return wishlistItems;
    }


    public List<String> constructInventoryItemsList(List<Item> UserInventory) {
        List<String> inventoryItems = new ArrayList<>();
        for (Item item : UserInventory) {
            inventoryItems.add(item.toString());
        }
        return inventoryItems;
    }

    public List<String> itemOptionList() {
        List<String> optionList = new ArrayList<>();
        optionList.add(removeItem);
        optionList.add(nextItem);
        return optionList;
    }

    public String enterWhatInFormat(String what, String format) {
        return "Please enter the " + what + " of your meeting in the format: " + format;
    }

    public String successfullyEditedMeeting(String what) {
        return "You have successfully edited your meeting to be at " + what;
    }

    public String whatTypeOfTransaction(String transactionType) {
        return "Would you like this transaction to be " + transactionType + " ?";
    }

    public String accountFrozen(boolean frozen) {
        if (frozen) { return "Your account is frozen."; }
        else { return "You account is not frozen"; }
    }

    public List<String> constructWhichMeetingList() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Edit first meeting");
        optionList.add("Edit second meeting");
        return optionList;
    }
}
