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
        um.addItem(currentUser.getUsername(), item, "wishlist");
    }

}
