package Admins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Presenters.MenuPresenter;

public class AdminMenuPresenter extends MenuPresenter {

    public List<String> constructMainMenu() {
        List<String> AdminMenuOptions = new ArrayList<>(); // are lists ordered?
        AdminMenuOptions.add("Check Pending Items for Approval");
        AdminMenuOptions.add("Check Flagged Users");
        AdminMenuOptions.add("Create New Admin TradingUser");
        AdminMenuOptions.add("Add New Item to a TradingUser's Wishlist/Inventory");
        AdminMenuOptions.add("Change TradingUser Threshold");
        AdminMenuOptions.add("Check Unfreeze Account Requests");
        AdminMenuOptions.add("Log Out");
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