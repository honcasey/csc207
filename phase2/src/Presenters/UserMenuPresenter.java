package Presenters;

public class UserMenuPresenter extends MenuPresenter {
    //user main menu strings
    public String TradingUserMenuTitle = "TradingUser Main Menu";
    public String requestAddItem = "Request Add Item";
    public String displayAvailableItems = "Display Available Items";
    public String viewActiveTrans = "View Active Transactions";
    public String viewTranHistory = "View Transaction History";
    public String viewWishlist = "View Wishlist";
    public String viewInventory = "View Inventory";
    public String changeEmail = "Change Email";
    public String changeCity = "Change City";
    public String requestUnfreeze = "Request unfreeze account";

    public String itemDetails = "Item Details";
    public String requestItem = "Request Item";
    public String remove = "Remove";
    public String submitForApproval = "has been submitted for admin approval.";
    public String offerItem = "Would you like to offer one of your items?";

    //meeting strings
    public String options = "Options";
    public String editMeeting = "Edit Meeting";
    public String location = "Location";
    public String updateMeeting = "Update Meeting";
    public String finalizeDetail = "Finalize Meeting Details";
    public String waitForConfirm = "Waiting for other user to confirm meeting.";
    public String meetingConfirmed = "Meeting has been confirmed.";
    public String confirmExchange = "Confirm exchange has taken place";
    public String meetupOccurrenceConfirmed = "Meetup occurrence confirmed";
    public String exchangeNotTakenPlace = "Claim the exchange has not taken place";

    //transaction strings
    public String requestTrans = "Request Transaction";
    public String createTrans = "Create Transaction";
    public String tranStatus = "Transaction Status";
    public String noCurrTrans = "No Current Transactions";
    public String cancelTrans = "Cancel Transaction";
    public String virtual = "Virtual";
    public String perm = "Permanent";
    public String temp = "Temporary";
    public String transCancelled = "Transaction has been cancelled.";
    public String itemReturned = "Confirm item has been returned";
    public String itemNotReturned = "Claim item has not been returned";
    public String itemReturnConfirmed = "Item return has been confirmed";
    public String mostRecentTwoWayTrans = "Most Recent Two Way Transactions";
    public String mostRecentOneWayTrans = "Most Recent One Way Transactions";
    public String mostTradedUser = "Most traded with Users";
    public String transaction = "Transaction: ";
    public String date = "Date: ";


    //message strings
    public String inWishlist = "Error: Already in your wishlist";
    public String addedToWishlist = "Added to wishlist";
    public String reachEditThreshold = "Edit Threshold Reached. You cannot make any more edits.";
    public String noItem = "No Available Items";

    public String optionPrompt(String what){
        return "Are you sure you want to "+what;
    }

}
