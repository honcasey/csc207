package Presenters;

public class AdminMenuPresenter extends MenuPresenter {
    //admin main menu strings
    public String adminMenuTitle = "Admin Menu";
    public String permissionDenied = "Permission Denied";
    public String checkPendingItem = "Check Pending Items for Approval";
    public String checkFlaggedUser = "Check Flagged Users";
    public String createNewAdmin = "Create New Admin User";
    public String addNewItem = "Add a New Item to a User's Wishlist/Inventory";
    public String changeThreshold = "Change a Trading User's Threshold";
    public String unfreezeRequest = "Check Unfreeze Account Requests";

    public String username = "New Admin Username:";
    public String password = "New Admin Password:";
    public String addToInventory = "Add to Inventory";
    public String approve = "Approve";
    public String reject = "Reject";

    //warning message string
    public String selectUser = "Error: Please select an user.";
    public String itemAddedError = "Error: Item has been added already.";
    public String updateThresholdError = "Error: UpdateThreshold method in adminController failed, so the threshold wasn't changed.";

    public String enter(String what){
        return "Error: Please enter the " + what;
    }

}


