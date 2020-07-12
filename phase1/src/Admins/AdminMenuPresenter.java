package Admins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Presenters.MenuPresenter;

public class AdminMenuPresenter extends MenuPresenter {

    // option strings
    public String checkPendingItems = "Check Pending Items for Approval";
    public String checkFlaggedUsers = "Check Flagged Users";
    public String createNewAdmin = "Create New Admin User";
    public String addItem = "Add New Item to a TradingUser's Wishlist/Inventory";
    public String changeThreshold = "Change TradingUser Threshold";
    public String checkUnfreezeAccounts = "Check Unfreeze TradingUser Account Requests";

    public String addToWishlist = "Add Item to Wishlist";
    public String addToInventory = "Add Item to Inventory";
    public String freezeAccount = "Freeze Account.";
    public String unfreezeAccount = "Unfreeze Account.";
    public String nextUser = "Go to next TradingUser.";

    public String approveItem = "Approve item for TradingUser's inventory.";
    public String declineItem = "Decline item.";
    public String nextItem = "Go to next item.";

    public String permissionDenied = "Permission denied, only the first admin can create new administrative user accounts.";

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
        optionList.add("borrow");
        optionList.add("weekly");
        optionList.add("incomplete");
        return optionList;
    }

    public List<String> constructUserLists() {
        List<String> optionList = new ArrayList<>();
        optionList.add("wishlist");
        optionList.add("inventory");
        return optionList;
    }
}