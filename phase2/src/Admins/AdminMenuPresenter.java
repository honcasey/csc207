package Admins;

import java.util.ArrayList;
import java.util.List;

import Items.ItemManager;
import Presenters.MenuPresenter;
import Transactions.TransactionManager;
import Users.TradingUserManager;

/**
 * <h1>AdminMenuPresenter</h1>
 * Contains all option strings printed/displayed by the AdminMenuController.
 * Class variables represent commonly used strings.
 */
public class AdminMenuPresenter extends MenuPresenter {

    // option strings
    protected String checkPendingItems = "Check Pending Items for Approval";
    protected String checkFlaggedUsers = "Check Flagged Users";
    protected String createNewAdmin = "Create New Admin User";
    protected String addItem = "Add New Item to a TradingUser's Wishlist/Inventory";
    protected String changeThreshold = "Change TradingUser Threshold";
    protected String checkUnfreezeAccounts = "Check Unfreeze TradingUser Account Requests";

    protected String addToWishlist = "Add Item to Wishlist";
    protected String addToInventory = "Add Item to Inventory";
    protected String freezeAccount = "Freeze Account.";
    protected String unfreezeAccount = "Unfreeze Account.";
    protected String nextUser = "Go to next TradingUser.";

    protected String approveItem = "Approve item for TradingUser's inventory.";
    protected String declineItem = "Decline item.";

    protected String permissionDenied = "Permission denied, only the first admin can create new administrative user accounts.";


    public AdminMenuPresenter(TransactionManager tm, TradingUserManager um, ItemManager im){
        super(tm,um,im);
    }
    /**
     * Creates main menu displayed for all Admin Users.
     * @return list of strings of all options available for an admin user
     */
    public List<String> constructMainMenu() {
        List<String> AdminMenuOptions = new ArrayList<>();
        AdminMenuOptions.add(checkPendingItems);
        AdminMenuOptions.add(checkFlaggedUsers);
        AdminMenuOptions.add(createNewAdmin);
        AdminMenuOptions.add(addItem);
        AdminMenuOptions.add(changeThreshold);
        AdminMenuOptions.add(checkUnfreezeAccounts);
        AdminMenuOptions.add(logout);
        return AdminMenuOptions;
    }

    /**
     * Constructs a list of doable actions that can be done on an item by an Admin User.
     * @return list of doable actions
     */
    protected List<String> constructAddToListMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(addToWishlist);
        optionList.add(addToInventory);
        return optionList;
    }

    /**
     * Constructs a list of doable actions that can be done to a Trading User that is frozen and requested to be unfrozen.
     * @return list of doable actions
     */
    protected List<String> constructPendingFrozenUsersMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(unfreezeAccount);
        optionList.add(nextUser);
        return optionList;
    }

    /**
     * Constructs a list of doable actions that can be done to a Trading User that has been flagged by the system.
     * @return list of doable actions
     */
    protected List<String> constructFlaggedUsersMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(freezeAccount);
        optionList.add(unfreezeAccount);
        optionList.add(nextUser);
        return optionList;
    }

    /**
     * Constructs a list of doable actions that can be done to a pending Item by an Admin User.
     * @return list of doable actions
     */
    protected List<String> constructPendingItemsMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(approveItem);
        optionList.add(declineItem);
        optionList.add(nextItem);
        return optionList;
    }

    /**
     * Constructs a list of thresholds that can be adjusted by an Admin User.
     * @return list of thresholds
     */
    protected List<String> constructAllThresholds() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Borrow");
        optionList.add("Weekly");
        optionList.add("Incomplete");
        return optionList;
    }

    /**
     * Constructs the Trading User lists.
     * @return list of TradingUser lists
     */
    protected List<String> constructUserLists() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Wishlist");
        optionList.add("Inventory");
        return optionList;
    }

    protected String whichThreshold(String whichThreshold) {
        return "What would you like to change the " + whichThreshold + " threshold to?";
    }

    protected String currentThreshold(String description, int threshold) {
        return "The current " + description + " is: " + threshold;
    }

    protected String addItem(String approved) {
        return "Item has been " + approved;
    }
}