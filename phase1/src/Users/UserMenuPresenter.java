package Users;

import Items.Item;
import Presenters.MenuPresenter;
import Transactions.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    public String removeItem = "Remove item.";
    public String nextItem = "Go to next item.";
    public String requestItem = "Request Items for Approval";
    public String browseAvailableItems = "Browse Available Items for Trade";
    public String viewActiveTransactions = "View Active Transactions";
    public String viewPastTransactionDetails = "View Past Transaction Details";
    public String viewWishlist = "View Wishlist";
    public String viewInventory = "View Inventory";
    public String requestUnfreeze = "Request Admin to Unfreeze Account";

    /**
     * Construct methods like this return a list of options/prompts that the menu will have.
     *
     * This particular method constructs the option list that the user will be greeted with upon first logging into
     * the program.
     * @return this returns a list of options that the user can choose from.
     */
    // THIS METHOD NEEDS TO CONTINUE TO BE FINISHED.

    //NEED TO DEAL WITH HAVING VARIABLES FOR ALL THE STRING CONSTANTS SOME WAY
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
     * Construct methods like this will return a list of options/prompts that the menu will have.
     * @param ItemList this is a list of items(all available items that are for trade from other users.)
     * @return this returns the list of options that the user can choose from.
     */
    public List<String> constructAvailableItemsMenu(List<Item> ItemList){
        System.out.println("Available Items for Trade:");
        String ItemOutputName = " Item Name: ";
        String ItemOutputDescription = " |  Item Description: ";
        // Making Option List

        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Item item : ItemList) {
            AvailableItemOptionList.add(ItemOutputName + item.getName() + ItemOutputDescription + item.getDescription());
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

    //TODO: Finish
    public List<String> constructTransactionList(ArrayList<Transaction> TransactionList){
        System.out.println("Current Transactions:");
        // Making Option List

        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Transaction transaction : TransactionList) {
            AvailableItemOptionList.add(transaction.toString() + "\\n");
        }
        return(AvailableItemOptionList);
    }

    public List<String> constructWishlistItemsList(TradingUser user) {
        List<String> wishlistItems = new ArrayList<>();
        for (Item item : user.getWishlist()) {
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

}
