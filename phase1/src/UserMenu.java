import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMenu {
    private User currentUser; // user that's logged in
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
     * This helper method constructs a new instance of item from user input then adds the item to th pending items list.
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
     * To return the wishlist of currUser
     * @return list of items
     */
    public List<Item> getUserWishlist(){return currentUser.getWishlist();}

    /**
     * TO return the inventory of currUser
     * @return list of items
     */
    public List<Item> getUserInventory(){
        return currentUser.getInventory();
    }

    /**
     * To return a HashMap of all the available items in other user's inventory.
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
        }return availableItems;
    }

    //Transaction methods
    /**
     * To change a Transaction status to canceled
     * @param transaction A transaction to be canceled and to remove transaction from tra
     */
    public void cancelTransaction(Transaction transaction){
        currentUser.getTransactionDetails().getIncomingOffers().remove(transaction);
        transaction.setStatus("cancelled");
    }
    /**
     * creates a Transaction and adds it to users
     * adds the Transaction to transaction details of both users
     * @param targetUser The User to whom currUser sends a Transaction
     */
    public void createTransaction(User targetUser){
        //TODO: method body
    }

}
