/**
 * Controls the system responsible for AdminUsers and an Administrative User's abilities in the trading system.
 */
public class AdminMenu {
    private AdminUser currentAdmin;
    // TO-DO: fix loading serialized info from Serializer
    String serializedAdminInfo = "src/serializedAdmin.ser";
    String serializedUserInfo = "src/serializedUser.ser";
    AdminManager am = new AdminManager(serializedAdminInfo);
    UserManager um = new UserManager(serializedUserInfo);

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
    public void addItem(String user, int itemId, String whichList) {
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




}
