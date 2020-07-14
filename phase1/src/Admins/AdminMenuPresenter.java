package Admins;

import java.util.ArrayList;
import java.util.List;
import Presenters.MenuPresenter;

/**
 * The AdminMenuPresenter is a Presenter class that extends MenuPresenter that contains all option strings
 * printed/displayed by the AdminMenuController.
 *
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

    public String whichThreshold(String whichThreshold) {
        return "What would you like to change the " + whichThreshold + " threshold to?";
    }

    public String currentThreshold(String description, int threshold) {
        return "The current " + description + " is: " + threshold;
    }

    public String addItem(String approved) {
        return "Item has been " + approved;
    }

    public List<String> constructAddToListMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(addToWishlist);
        optionList.add(addToInventory);
        return optionList;
    }

    public List<String> constructPendingFrozenUsersMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(unfreezeAccount);
        optionList.add(nextUser);
        return optionList;
    }

    public List<String> constructFlaggedUsersMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(freezeAccount);
        optionList.add(unfreezeAccount);
        optionList.add(nextUser);
        return optionList;
    }

    public List<String> constructPendingItemsMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add(approveItem);
        optionList.add(declineItem);
        optionList.add(nextItem);
        return optionList;
    }

    public List<String> constructAllThresholds() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Borrow");
        optionList.add("Weekly");
        optionList.add("Incomplete");
        return optionList;
    }

    public List<String> constructUserLists() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Wishlist");
        optionList.add("Inventory");
        return optionList;
    }
}