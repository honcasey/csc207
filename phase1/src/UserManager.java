import java.util.List;
import java.util.UUID;

/**
 * Change info in user and item entities.
 */
public class UserManager {

    /**
     * Add a new user with given info.
     * @param username online identifier of a User
     * @param password account password
     * @param email User's email address
     */
    public void addUser(String username, String password, String email){
        User user = new User(username, password, email);
    }

    /**
     * To retrieve a specific user.
     * @param userId id of a specific user
     * @return username and userId as string separated by comma
     */
    public User getUser(int userId){
        List<User>users;
        //TODO: get users(collection of users) from file once userSerializer is done.
        User user;
        for (user:users){
            int curUserId = user.userId;
            if (curUserId == userId) {
                String userName = user.getUsername();
                String userPassword = user.getPassword();
                String userEmail = user.getEmail();
                user = new User(userName,userPassword,userEmail);
            }
        }
        return user;
    }

    /**
     * Add an item with given name.
     * @param name the name of the item
     */
    public void addItem(String name){
        Item item = new Item(name);
    }

    /**
     * To remove a specific item with given itemId
     * @param itemId the Id of the item
     */
    public void removeItem(UUID itemId) {
        List<Item> items;
        //TODO: get items(collection of items) from file once userSerializer is done.
        Item item;
        for (item:
             items) {
            UUID curItemId = item.getId();
            if (curItemId == itemId) {
                items.remove(item);
            }
            //TODO: write to file

        }
    }

    /**
     * ????
     * @param itemId
     */

    public void pendingItem(UUID itemId){
        List<Item>pendingItems;
    }



    /**
     * To change the minimum number of Items that this User has to have lent before they can borrow an Item.
     * @param user A user in the trading system.
     * @param thresholdValue minimum number of Items that this User has to have lent before they can borrow an Item.
     */
    public void changeThreshold(User user, int thresholdValue){
        user.setThreshold(thresholdValue);
    }

    /**
     * To change the status of an user's account to frozen.
     * @param user A user in the trading system.
     */
    public void freezeAccount(User user){
        user.setStatus("frozen");
    }

    /**
     * Add a transaction to User's tradeHistory
     * @param user A user in the trading system.
     * @param transaction
     */
    public void addToTradeHistory(User user, Transaction transaction){
        List<Transaction> tradeHistory = user.getTradeHistory();
        tradeHistory.add(transaction);
        user.setTradeHistory(tradeHistory);
    }

}
