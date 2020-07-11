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

    public List<String> constructMainMenu() {
        List<String> AdminMenuOptions = new ArrayList<>(); // are lists ordered?
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


}