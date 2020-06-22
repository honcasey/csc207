import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Change info in user and item entities.
 */
public class UserManager {
    //UserSerializer usersFromSerializer = new UserSerializer();
    //List<User> users = usersFromSerializer.getUsers();
    private List<User> allUsers = new ArrayList<>();

    /**
     * Creates a new empty UserManager.
     */
    public UserManager(String filePath) {
        allUsers = new ArrayList<User>();
        Serializer serializer = new Serializer();
        allUsers.addAll(serializer.getUsers());
    }

    /**
     * Add a new user with given info.
     * @param username online identifier of a User
     * @param password account password
     * @param email User's email address
     */
    public void addUser(String username, String password, String email){
        User user = new User(username, password, email);
        allUsers.add(user);
    }

    /**
     * To retrieve a specific user by userID.
     * @param userId id of a specific user
     * @return username and userId as string separated by comma
     */
    public User getUser(UUID userId){
        User desiredUser= new User(null,null,null);
        for (User user : allUsers) {
            if ((user.getUserId().equals(userId))) {
                    desiredUser = user;
                }
            }
            return desiredUser;
    }


    /**
     * To add an item to user's inventory.
     * @param user An user in the trading system.
     * @param itemId The id of an item.
     */
    public void addItem(User user, UUID itemId){
        ItemManager itemManager = new ItemManager();
        List<Item> userInventory = user.getInventory();
        userInventory.add(itemManager.getItem(itemId));
    }

    /**
     * To remove a item from user's inventory
     * @param user An user in the trading system.
     * @param itemId Id of an item.
     */
    public void removeItem(User user, UUID itemId) {

        List<Item> items = user.getInventory();
        for (Item item:items) {
            if (item.getId().equals(itemId)) {
                items.remove(item);
            }
            //write to file??

        }
    }

    /**
     * List of pending items.
     * @param itemId id of an item.
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
     * @param transaction a meetup between 2 users.
     */
    public void addToTradeHistory(User user, Transaction transaction){
        List<Transaction> tradeHistory = user.getTradeHistory();
        tradeHistory.add(transaction);
        user.setTradeHistory(tradeHistory);
    }

}
