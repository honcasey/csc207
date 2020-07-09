package Users;

import Admins.AdminManager;
import Items.Item;
import Transactions.Transaction;
import Transactions.TransactionManager;
import Users.User;
import Users.UserManager;

import java.util.HashMap;
import java.util.List;

public class UserMenu {

    /**
     * This class will be responsible for the main flows and keeping track of what menu the user is in.
     * No logic will be here(except for location tracking).
     *
     * UserLocationChoices = Hashmap mapping (menu --> List(choices)) that represents the current menu out of the many menus the user can be in.
     *(The "many menus above will be kept track with static final variables kept in Users.UserMenu class
     * so that there aren't capitalization problems")
     *
     *
     * (The Menus that UserLocation will represent are the sub-methods in the old UserMenuViewer class that was wrote.)
     * This will make calls to the methods
     * in Users.UserMenuController and Users.UserMenuPresenter classes that do all the work. The only thing this class will do
     * is make the calls to these methods in an order that depends on the structure of our program.
     *
     * For example: If a user wants to make a transaction the flow will be: (method names are just to tell you what is
     * happening, they are less important)
     *
     * UsermenuPresenter.displayOptions(Static class variable representing list of options.);
     * Input_String = Users.UserMenuController.HandleOptionInput(); <-- this will return the actual option name the user selected.
     * Users.UserMenu.UserLocation = Input_String <- this updates UserLocation
     *
     *
     */

    private User currentUser; // user that's logged in
    private AdminManager am;
    private UserManager um;
    private TransactionManager tm;
    private HashMap<Item, User> allPendingItems;

    public UserMenu(UserManager userManager, AdminManager adminManager, TransactionManager transactionManager,
                    HashMap<Item, User> pendingItems, User currentUser) {
        this.currentUser = currentUser;
        allPendingItems = pendingItems;
        am = adminManager;
        um = userManager;
        tm = transactionManager;
    }

    /**
     * This helper method constructs a new instance of item from user input then adds the item to the pending items list.
     * @param itemName the name of the item to be requested.
     * @param itemDescription this is the description of the item.
     */
    public void requestAddItemInput(String itemName, String itemDescription){
        Item RequestedItem = new Item(itemName);
        RequestedItem.setDescription(itemDescription);
        allPendingItems.put(RequestedItem,this.currentUser);
    }

    /**
     * To withdraw item from user's specified list, which is either the Users.User's wishlist or inventory.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void withdrawItem(Item item, String listType){
        if(listType.equals("wishlist")){
            um.removeItem(currentUser, item, "wishlist");
        }else if (listType.equals("inventory")){
            um.removeItem(currentUser,item,"inventory");
        }
    }

    /**
     * To add an given item to user's wishlist
     * @param item An item in the trading system
     */
    public void addToWishlist(Item item){
        um.addItem(currentUser, item, "wishlist");
    }

    /**
     * Returns a HashMap of all the available items in other user's inventory.
     * @return HashMap of items that are available in other user's inventory.
     */
    public HashMap<Item,User> getAvailableItems(){
        List<User> allUsers = um.getAllUsers();
        HashMap<Item,User> availableItems = new HashMap<>();
        for (User user:allUsers) {
            if(!user.equals(currentUser)) {
                for (Item item : user.getInventory()) {
                    availableItems.put(item, user);
                }
            }
        }
        return availableItems;
    }

    /**
     * Changes a Transactions.Transaction status to cancelled
     * @param transaction A transaction to be cancelled and to remove transaction from tra
     */
    public void cancelTransaction(Transaction transaction)  {
        currentUser.getTransactionDetails().getIncomingOffers().remove(transaction);
       User u =  um.getUserById(transaction.getUser1());
       if (u == currentUser){
           transaction.setStatusUser1("cancel");
       }
       else{
           transaction.setStatusUser2("cancel");
       }
       tm.updateStatus(transaction);
    }

    /**
     * Creates a Transactions.Transaction and adds it to users
     * adds the Transactions.Transaction to transaction details of both users
     * @param targetUser The Users.User to whom currUser sends a Transactions.Transaction
     */
    public void createTransaction(User targetUser){
        //TODO: method body
    }

    /**
     * Changes status of a Transactions.Transaction to confirmed, when details of all meetings have been confirmed by both users.
     * @param transaction the transaction to be confirmed
     */
    public void acceptTransaction(Transaction transaction) {
        transaction.setStatus("confirmed");
    }

    /**
     * Changes status of a Transactions.Transaction to completed, when the last meeting of the transaction has occurred and been completed by both users.
     * @param transaction the transaction to be completed
     */
    public void confirmTransaction(Transaction transaction) {
        transaction.setStatus("completed");
        User user1 = um.getUserById(transaction.getUser1());
        User user2 = um.getUserById(transaction.getUser2());
        um.addToTransactionHistory(user1, transaction);
        um.addToTransactionHistory(user2, transaction);
        user1.getTransactionDetails().getSentOffers().remove(transaction); // Is the transaction in both user's "sent offers"?
        user2.getTransactionDetails().getSentOffers().remove(transaction);
    }

    /**
     * Requests the admin user to unfreeze the current user's account, if it's status is already frozen.
     */
    public void requestUnfreezeAccount() {
        am.getPendingFrozenUsers().add(currentUser);
        am.getFrozenAccounts().remove(currentUser);
    }

    /**
     * Deletes a transaction that is in progress
     */
    public void deleteTransaction(Transaction transaction){
    // TODO: Method Body once I confirm some things about the details of this method
    }


}
