import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Controls the system responsible for AdminUsers and an Administrative User's abilities in the trading system.
 */
public class AdminMenu {
    private AdminUser currentAdmin;
    private AdminManager am;
    private UserManager um;
    public HashMap<Item, User> allPendingItems;
    public List<User> allPendingUsers;

    public AdminMenu(AdminManager adminManager, AdminUser admin) {
        currentAdmin = admin;
        am = adminManager;
    }

    /**
     * Creates and returns a new administrative user as an AdminUser.
     * @param username new username as a String
     * @param password new password as a String
     * @return a new AdminUser with given username and password
     */
    public AdminUser createNewAdmin(String username, String password) {
        return am.addAdmin(username, password);
    }

    /**
     * Adds the given Item to a User's specified list, which is either the User's wishlist or inventory.
     * @param user which User's list to add the item to as a User
     * @param itemId ID of the item to be added as an integer
     * @param whichList either "wishlist" or "inventory" as a String
     */
    public void addItem(String user, UUID itemId, String whichList) {
        if (whichList.equals("wishlist")) {
            um.addItem(user, itemId, "wishlist");
        }
        if (whichList.equals("inventory")) {
            um.addItem(user, itemId, "inventory");
        }
    }

    /**
     * Changes a User's specified threshold.
     * @param user which User's threshold to change
     * @param thresholdValue new value of threshold as an int
     * @param whichThreshold either "borrow", "weekly", or "incomplete" as a String
     */
    public void changeThreshold(User user, int thresholdValue, String whichThreshold) {
        if (whichThreshold.equals("borrow")) {
            um.changeThreshold(user, thresholdValue, "borrow");
        }
        if (whichThreshold.equals("weekly")) {
            um.changeThreshold(user, thresholdValue, "weekly");
        }
        if (whichThreshold.equals("incomplete")) {
            um.changeThreshold(user, thresholdValue, "incomplete");
        }
    }

    /**
     * Adds a pending Item to a User's inventory once an Admin User has approved it.
     * @param user which User has requested this item to be added
     * @param item what Item to be added to the inventory
     * @param approved whether this Item is approved by the Admin User or not
     */
    public void checkPendingItems(User user, Item item, boolean approved) {
        if (approved) { um.addItem(user.getUsername(), item.getId(), "inventory"); }
        else { allPendingItems.remove(item); }
    }

    /**
     * Freezes/unfreezes a User's account if it has been flagged by the system.
     * @param user which User's account has been flagged to be frozen/unfrozen
     */
    public void checkPendingUsers(User user, boolean freeze) {
        if (freeze) { um.freezeAccount(user); }
        else { um.unfreezeAccount(user); }
    }




}
