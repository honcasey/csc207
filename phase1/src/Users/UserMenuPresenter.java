package Users;

import Items.Item;
import Presenters.MenuPresenter;
import Transactions.Transaction;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.HashMap;
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
        String RequestItemForApproval = new String("Request Item for Approval");
        String BrowseAvailableItems = new String("Browse Available Items for Trade");
        String ViewActiveTransactions = new String("View Active Transactions");
        String ViewPastTransactionDetails = new String("View Past Transaction Details");
        String ViewWishList = new String("View Wishlist");
        MenuOptionList.add(RequestItemForApproval);
        MenuOptionList.add(BrowseAvailableItems);
        MenuOptionList.add(ViewActiveTransactions);
        MenuOptionList.add(ViewPastTransactionDetails);
        MenuOptionList.add(ViewWishList);
        MenuOptionList.add("View Inventory");
        MenuOptionList.add("Request Admin to Unfreeze Account");
        MenuOptionList.add("Log Out");
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
     * @return This returns a list of all the possible transaction type options.
     */

    public List<String> constructTransactionTypeMenu(){
        List<String> OptionList = new ArrayList<>();
        OptionList.add("One Way Temporary Trade");
        OptionList.add("One Way Permanent Trade");
        OptionList.add("Two Way Temporary Trade");
        OptionList.add("Two Way Permanent Trade");
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

}
