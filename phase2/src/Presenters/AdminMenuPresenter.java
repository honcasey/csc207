package Presenters;

public class AdminMenuPresenter extends MenuPresenter {
    //admin main menu strings
    public String adminMenuTitle = "Admin Menu";
    public String permissionDenied = "Permission Denied";
    public String checkPendingItem = "Check Pending Items for Approval";
    public String checkFlaggedUser = "Check Flagged Users";
    public String createNewAdmin = "Create New Admin User";
    public String addNewItem = "Add New Item to a TradingUser's Wishlist/Inventory";
    public String changeThreshold = "Change TradingUser Threshold";
    public String unfreezeRequest = "Check Unfreeze TradingUser Account Requests";

    public String username = "New Admin Username:";
    public String password = "New Admin Password:";
    public String addToInventory = "Add to Inventory";

    //message string
    public String selectUser = "Error: Please select an user.";
    public String itemAddedError = "Error: Item has been added already.";
    public String updateThresholdError = "Error: UpdateThreshold method in adminController failed, so the threshold wasn't changed.";

    public String enter(String what){
        return "Error: Please enter the " + what;
    }
    public String successfully(String what){
        return what + " successfully!";
    }
}


