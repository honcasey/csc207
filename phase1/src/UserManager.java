import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages user.
 */
public class UserManager {
    private List<User> allUsers;
    private Exception InvalidUserException;
    private List<User> blackList;


    /**
     * Creates a list of users.
     */
    public UserManager(List<User> users) {
        allUsers = users;
    }

    /**
     * Add a new user with given info.
     * @param username online identifier of a User
     * @param password account password
     * @return username and userId as string separated by comma.
     */
    public User addUser(String username, String password) throws InvalidUserException {
        User newUser = new User(username, password);
        if (allUsers.size() == 0) {
            allUsers.add(newUser);
            return newUser;
        }
        else {
            for (User user : allUsers) {
                if (user.getUsername().equals(username)) {
                    throw new InvalidUserException();
                }
            }
        }
        allUsers.add(newUser);
        return newUser;
    }

    /**
     * To retrieve a specific user by username.
     * @param username online identifier of a User
     * @return username and userId as string separated by comma
     */
    public User getUser(String username) throws InvalidUserException {
        for (User user : allUsers) {
            if ((user.getUsername().equals(username))) {
                return user;
                }
            }
        throw new InvalidUserException();
    }

    /**
     * To add an item to user's specified list, which is either the User's wishlist or inventory.
     * @param user the user
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void addItem(User user, Item item, String listType){
        List<Item> userInventory = user.getInventory();
        List<Item> userWishlist = user.getWishlist();

        if(listType.equals("wishlist")){
            userWishlist.add(item);
        }
        else if(listType.equals("inventory")){
            userInventory.add(item);
        }

    }


    /**
     * To remove a item from user's specified list, which is either the User's wishlist or inventory.
     * @param user An user in the trading system.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void removeItem(User user, Item item, String listType) {
        if (listType.equals("wishlist")){
            user.getWishlist().remove(item);
        }
        else if (listType.equals("inventory")){
            user.getInventory().remove(item);
        }
    }


    /**
     * To change the user's specified threshold.
     * @param user A user in the trading system.
     * @param thresholdValue new value of threshold as an int
     * @param thresholdType either "borrow", "weekly", or "incomplete" as a String
     */
    public void changeThreshold(User user, int thresholdValue, String thresholdType){

        switch (thresholdType) {
            case "borrowThreshold":
                user.setBorrowThreshold(thresholdValue);
                break;
            case "weeklyThreshold":
                user.setWeeklyThreshold(thresholdValue);
                break;
            case "incompleteThreshold":
                user.setIncompleteThreshold(thresholdValue);
                break;
        }
    }

    /**
     * To change the status of an user's account to frozen.
     * @param user A user in the trading system.
     */
    public void freezeAccount(User user){
        user.setStatus("frozen");
    }

    /**
     * To change the status of an user's account to active.
     * @param user A user in the trading system.
     */
    public void unfreezeAccount(User user){
        user.setStatus("active");
    }

    /**
     * Add a transaction to User's transaction history.
     * @param user A user in the trading system.
     * @param transaction a meetup between 2 users.
     */
    public void addToTransactionHistory(User user, Transaction transaction) {
        TransactionHistory tH = user.getTransactionHistory();
        tH.setTransactionHistory(transaction);
        user.setTransactionHistory(tH);
        updateTransactionHistoryValues(user, transaction);
    }

    /**
     * A private helper method for addToTransactionHistory that updates UserNumTradeTimes, NumItemsBorrowed, and NumItemsLended
     * @param user A user in a trading system
     * @param transaction a transaction between two Users
     */

    // consider splitting into two methods. Reasoning for having one method, user1 == user is needed for both updating the UserNumTradeTimes and NumItemsBorrowed, NumItemsLended
     private void updateTransactionHistoryValues(User user, Transaction transaction){
            TransactionHistory tH = user.getTransactionHistory();
         if (transaction.getUser1() == user) {
             user.getTransactionHistory().setNumItemsLended();
             User u2 = transaction.getUser2();
             if (tH.getUsersNumTradeTimes().containsKey(u2)) {
                 tH.getUsersNumTradeTimes().put(transaction.getUser2(), tH.getUsersNumTradeTimes().get(u2) + 1);
             } else {
                 tH.getUsersNumTradeTimes().put(u2, 1);
             }
             if (!transaction.isOneWay()) {
                 user.getTransactionHistory().setNumItemsBorrowed();
             }
         } else {
             user.getTransactionHistory().setNumItemsBorrowed();
             User u1 = transaction.getUser1();
             if (tH.getUsersNumTradeTimes().containsKey(u1)) {
                 tH.getUsersNumTradeTimes().put(transaction.getUser2(), tH.getUsersNumTradeTimes().get(u1) + 1);
             } else {
                 tH.getUsersNumTradeTimes().put(u1, 1);
                 if (!transaction.isOneWay()) {
                     user.getTransactionHistory().setNumItemsLended();
                 }
             }
         }
        }


    /**
     * To check whether the username is valid.
     * @param username online identifier of a User
     * @return True or False as boolean
     */
    public boolean checkAvailableUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public boolean validUser(String username, String password) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a specific user to the blacklist.
     * @param username online identifier of a User
     * @throws Exception throws invalidUserException
     */
    public void addToBlackList(String username) throws Exception {
        blackList.add(getUser(username));
    }

}
