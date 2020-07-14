package Users;

import Items.Item;
import Presenters.MenuPresenter;
import Transactions.Transaction;
import java.util.*;

/**
 * Contains helper methods that will be used to format and output the prompts that a user sees.
 * Class variables store common strings that users will see as prompts.
 */
public class UserMenuPresenter extends MenuPresenter {

    /* main menu strings */
    protected String removeItem = "Remove item.";
    protected String requestItem = "Request Items for Approval";
    protected String browseAvailableItems = "Browse Available Items for Trade";
    protected String viewActiveTransactions = "View Active Transactions";
    protected String viewPastTransactionDetails = "Past Transactions";
    protected String viewWishlist = "View Wishlist";
    protected String viewInventory = "View Inventory";
    protected String requestUnfreeze = "Request Admin to Unfreeze Account";

    /* item strings */
    protected String offerItem = "Would you like to offer one of your items?";
    protected String addToWishlist = "Would you like to add this item to your wishlist?";
    protected String itemRequested = "Items has been requested and is now being reviewed by the administrators.";
    protected String requestAccountUnfreeze = "You cannot make an offer for this item. Please request to have your account unfrozen.";
    protected String selectItemToOffer = "Please select one of the items from your inventory that you want to offer:";

    /* transaction strings */
    protected String makeTransaction = "Would you like to make another transaction?";
    protected String meetingLocation = "Where do you want to have the meeting?";
    protected String scheduleMeeting = "Please schedule a meeting time with the other user.";

    protected String requestedUnfreeze = "You have successfully requested for your account to be unfrozen.";
    protected String editThresholdReached = "You have reached your edit threshold.";
    protected String viewThreeMostTraded = "View 3 most frequent trading partners.";

    /**
     * <h1>UserMenuPresenter</h1>
     * Constructs a list of options/prompts that the menu will have.
     * @return list of options that the user can choose from.
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

    /**
     * Constructs a list of all past transactions into a menu.
     * @return list of options that the user can choose from
     */
    protected List<String> constructPastTransactionMenu(){
        List<String> MenuOptionList = new ArrayList<>();
        MenuOptionList.add(viewRecentTransactions("one"));
        MenuOptionList.add(viewRecentTransactions("two"));
        MenuOptionList.add(viewThreeMostTraded);
        return(MenuOptionList);
    }

    /**
     * Constructs a list of options/prompts that the menu displaying available items will have.
     * @param ItemList list of all items that are available for trade from other users
     * @return list of options that the user can choose from
     */
    protected List<String> constructAvailableItemsMenu(List<Item> ItemList){
        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Item item : ItemList) {
            AvailableItemOptionList.add(" Item Name: " + item.getName() + " |  Item Description: " + item.getDescription());
        }
        return(AvailableItemOptionList);
    }

    /**
     * Constructs a list of options/prompts that the menu displaying process to edit a meeting will have.
     * @return list of all the possible ways to edit meeting options.
     */
    protected List<String> constructEditMeetingOptions(){
        List<String> OptionList = new ArrayList<>();
        OptionList.add("Edit Location");
        OptionList.add("Edit Time");
        OptionList.add("Edit Date");
        return(OptionList);
    }

    /**
     * Constructs a list of options/prompts that the menu displaying most recent transactions will have.
     * @return list of transactions.
     */
    protected List<String> constructTransactionList(List<Transaction> TransactionList){
        List<String> transactionList = new ArrayList<>();
        for (Transaction transaction : TransactionList) {
            transactionList.add(transaction.toString());
        }
        return(transactionList);
    }

    /**
     * Constructs and displays a list of the User's wishlist items in a menu format.
     * @param items a User's wishlist
     * @return list of User's wishlist items
     */
    protected List<String> constructWishlistItemsList(List<Item> items) {
        List<String> wishlistItems = new ArrayList<>();
        for (Item item : items) {
            wishlistItems.add(item.toString());
        }
        return wishlistItems;
    }

    /**
     * Constructs and displays a list of the User's inventory items in a menu format.
     * @param items a User's inventory
     * @return list of User's inventory items
     */
    protected List<String> constructInventoryItemsList(List<Item> items) {
        List<String> inventoryItems = new ArrayList<>();
        for (Item item : items) {
            inventoryItems.add(item.toString());
        }
        return inventoryItems;
    }

    /**
     * Constructs a list of actions that a User can do on a given item.
     * @return list of doable actions on an item
     */
    protected List<String> itemOptionList() {
        List<String> optionList = new ArrayList<>();
        optionList.add(removeItem);
        optionList.add(nextItem);
        return optionList;
    }

    /**
     * Constructs a list of actions that a User can do on a given meeting.
     * @return list of doable actions on a meeting
     */
    protected List<String> constructWhichMeetingList() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Edit first meeting");
        optionList.add("Edit second meeting");
        return optionList;
    }

    /**
     * Returns a list of the actions that can be done by a User depending on the status of the Transaction.
     * @param transaction the input transaction
     * @return a list of actions as strings
     */
    protected ArrayList<String> userTransactionActions(Transaction transaction){
        String status = transaction.getStatus();
        ArrayList<String> options = new ArrayList<>();
        switch (status) {
            case "pending": {
                String[] list = new String[]{"Edit Transactions Meeting(s)", "Confirm Transactions Meeting(s)", "Cancel transaction"};
                options.addAll(Arrays.asList(list));
                break;
            }
            case "confirmed": {
                String[] list = new String[]{"Confirm the exchange has taken place", "Claim that the exchange has not taken place"};
                options.addAll(Arrays.asList(list));
                break;
            }
            case "traded": {
                String[] list = new String[]{"Confirm the item has been returned", "Claim that the item has not been returned past due date"};
                options.addAll(Arrays.asList(list));
                break;
            }
            default: {
                String[] list = new String[]{"There are no actions that can be done to this transaction"};
                options.addAll(Arrays.asList(list));
                break;
            }
        }
        return options;
    }

    protected String enterWhatInFormat(String what, String format) {
        return "Please enter the " + what + " of your meeting in the format: " + format;
    }

    protected String successfullyEditedMeeting(String what) {
        return "You have successfully edited your meeting to be at " + what;
    }

    protected String whatTypeOfTransaction(String transactionType) {
        return "Would you like this transaction to be " + transactionType + " ?";
    }

    protected String accountFrozen(boolean frozen) {
        if (frozen) { return "Your account is frozen."; }
        else { return "You account is not frozen"; }
    }

    protected String viewRecentTransactions(String transactionType) {
        return "View 3 most recent " + transactionType + " way past transactions.";
    }
}