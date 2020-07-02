import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMenu {
    public User currentUser; // user that's logged in
    private AdminManager am;
    private UserManager um;
    private HashMap<Item, User> allPendingItems;

    public UserMenu(UserManager userManager, AdminManager adminManager,
                    HashMap<Item, User> pendingItems, User currentUser) {
        this.currentUser = currentUser;
        allPendingItems = pendingItems;
        am = adminManager;
        um = userManager;
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
     * To withdraw item from user's specified list, which is either the User's wishlist or inventory.
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
     * Returns the wishlist of the current user.
     * @return list of items
     */
    public List<Item> getUserWishlist(){return currentUser.getWishlist();}

    /**
     * Returns the inventory of the current user.
     * @return list of items
     */
    public List<Item> getUserInventory(){
        return currentUser.getInventory();
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
     * Changes a Transaction status to cancelled
     * @param transaction A transaction to be cancelled and to remove transaction from tra
     */
    public void cancelTransaction(Transaction transaction){
        currentUser.getTransactionDetails().getIncomingOffers().remove(transaction);
        transaction.setStatus("cancelled");
    }

    /**
     * Creates a Transaction and adds it to users
     * adds the Transaction to transaction details of both users
     * @param targetUser The User to whom currUser sends a Transaction
     */
    public void createTransaction(User targetUser){
        //TODO: method body
    }

    /**
     * Changes status of a Transaction to confirmed, when details of all meetings have been confirmed by both users.
     * @param transaction the transaction to be confirmed
     */
    public void acceptTransaction(Transaction transaction) {
        transaction.setStatus("confirmed");
    }

    /**
     * Changes status of a Transaction to completed, when the last meeting of the transaction has occurred and been completed by both users.
     * @param transaction the transaction to be completed
     */
    public void confirmTransaction(Transaction transaction) {
        transaction.setStatus("completed");
        um.addToTransactionHistory(transaction.getUser1(), transaction);
        um.addToTransactionHistory(transaction.getUser2(), transaction);
        transaction.getUser1().getTransactionDetails().getSentOffers().remove(transaction); // Is the transaction in both user's "sent offers"?
        transaction.getUser2().getTransactionDetails().getSentOffers().remove(transaction);
    }

    /**
     * Requests the admin user to unfreeze the current user's account, if it's status is already frozen.
     */
    public void requestUnfreezeAccount() { am.getPendingFrozenUsers().add(currentUser); }

    /**
     * Deletes a transaction that is in progress
     */
    public void deleteTransaction(Transaction transaction){
    // TODO: Method Body once I confirm some things about the details of this method
    }


}
