import java.util.HashMap;
import java.util.UUID;

public class UserMenu {
    private User currentUser;
    private AdminManager am;
    private UserManager um;
    private HashMap<Item, User> allPendingItems;

    public UserMenu(UserManager userManager, AdminManager adminManager,
                    HashMap<Item, User> pendingItems, User currentUser) {
        this.currentUser = currentUser;
        HashMap<Item, User> allPendingItems;
        am = adminManager;
        um = userManager;
    }

    public String requestAddItem(String itemName){

    }

    /**
     * To withdraw item from user's specified list, which is either the User's wishlist or inventory.
     * @param itemId the id of an item
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void withdrawItem(UUID itemId, String listType){
        if(listType.equals("wishlist")){
            um.removeItem(currentUser, itemId, "wishlist");
        }else if (listType.equals("inventory")){
            um.removeItem(currentUser,itemId,"inventory");
        }
    }

    /**
     * To add an given item to user's wishlist
     * @param itemId the id of an item
     */
    public void addToWishlist(UUID itemId){
        um.addItem(currentUser.getUsername(), itemId, "wishlist");
    }

}
