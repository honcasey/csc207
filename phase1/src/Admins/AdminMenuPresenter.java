package Admins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminMenuPresenter extends MenuPresenter implements PresenterStrings {

    public List<String> allThresholds = Arrays.asList("borrow", "weekly", "incomplete");
    public List<String> userLists = Arrays.asList("wishlist", "inventory");

    @Override
    public List<String> mainMenu() {
        List<String> AdminMenuOptions = new ArrayList<>(); // are lists ordered?
        AdminMenuOptions.add("Check Pending Items for Approval");
        AdminMenuOptions.add("Check Flagged Users");
        AdminMenuOptions.add("Create New Admin User");
        AdminMenuOptions.add("Add New Item to a User's Wishlist/Inventory");
        AdminMenuOptions.add("Change User Threshold");
        AdminMenuOptions.add("Check Unfreeze Account Requests");
        AdminMenuOptions.add("Log Out");
        return AdminMenuOptions;
    }

    @Override
    public String empty(String which) { return which + " list is empty. Nothing to be checked."; }

    @Override
    public String enterName(String name) {
        return "Please enter name for this " + name;
    }

    @Override
    public String successfullyAdded(String what, String who, String where) {
        return what + "has been successfully added to " + who + "'s " + where;
    }

    @Override
    public String successfullyChanged(String what, String who) {
        return who + "'s " + what + "has been successfully changed.";
    }

    @Override
    public String validOptions(List<String> optionList) {
        return "Valid options include: " + optionList.toString();
    }

    @Override
    public String whichThreshold(String whichThreshold) {
        return "What would you like to change the " + whichThreshold + " threshold to?";
    }

    @Override
    public String usernameTaken() { return "Username already taken. Please enter a different one."; }

    @Override
    public String currentThreshold(String description, int threshold) {
        return "The current " + description + " is: " + threshold;
    }

    @Override
    public String accountFrozen(String who, String frozen) {
        return who + "'s account has been set to " + frozen;
    }

    @Override
    public String logout() { return "You have successfully logged out."; }

    @Override
    public String invalidOption() { return "Not a valid option. Please enter a valid option."; }

}