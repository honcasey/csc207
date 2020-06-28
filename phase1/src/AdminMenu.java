import java.util.*;

/**
 * Controls the system responsible for AdminUsers and an Administrative User's abilities in the trading system.
 */
public class AdminMenu {
    private AdminUser currentAdmin; // admin that's logged in
    private AdminManager am;
    private UserManager um;
    private HashMap<Item, User> allPendingItems;
    private List<User> flaggedAccounts;

    public AdminMenu(AdminManager adminManager, UserManager userManager, HashMap<Item, User> pendingItems,
                     List<User> flaggedAccounts, AdminUser admin) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        this.flaggedAccounts = flaggedAccounts;
        um = userManager;
        am = adminManager;
    }

    /**
     * Checks if this admin username has been taken or not.
     * @param username online identifier of an AdminUser
     * @return True or False as boolean
     */
    public boolean checkAvailableAdminUsername(String username) {
        for (AdminUser admin : am.getAllAdmins()) {
            if (admin.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates and returns a new administrative user as an AdminUser.
     * @param username new username as a String
     * @param password new password as a String
     */
    public void createNewAdmin(String username, String password) {
        am.addAdmin(username, password);
    }

    /**
     * Adds the given Item to a User's specified list, which is either the User's wishlist or inventory.
     * @param username which User's list to add the item to as a User
     * @param item the item to be added as an integer
     * @param whichList either "wishlist" or "inventory" as a String
     */
    public void addItem(String username, Item item, String whichList) throws InvalidUserException {
        if (whichList.equals("wishlist")) {
            um.addItem(um.getUser(username), item, "wishlist");
        }
        if (whichList.equals("inventory")) {
            um.addItem(um.getUser(username), item, "inventory");
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
        if (approved) { um.addItem(user, item, "inventory"); }
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

    /**
     * Getter for this AdminMenu's UserManager.
     * @return um UserManager
     */
    public UserManager getUm() { return um; }

    public HashMap<Item, User> getAllPendingItems() {
        return allPendingItems;
    }

    public List<User> getFlaggedAccounts() {
        return flaggedAccounts;
    }
}
