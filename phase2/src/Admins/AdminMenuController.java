package Admins;

import Actions.ActionManager;
import Actions.AddOrDeleteAction;
import Exceptions.InvalidAdminException;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Items.ItemManager;
import Users.TradingUser;
import Users.TradingUserManager;

import java.util.*;

/**
 * <h1>AdminMenuController</h1>
 * Decides which use case/manager methods to call depending on the user input taken from the presenter.
 * <p>It stores instances of all use cases/managers (AdminManager, TradingUserManager, ItemManager), the AdminMenuPresenter,
 *  * and a list of allPendingItems (which is the list of all items that have been requested by a TradingUser to be added
 *  * to their inventory). <p/>
 */
public class AdminMenuController {
    protected AdminUser currentAdmin = null; // admin that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    protected final Map<Item, TradingUser> allPendingItems;
    private final ItemManager im;
    private final ActionManager acm;

    /**
     * Constructs an instance an AdminMenuController.
     * @param adminManager manager of all AdminUsers
     * @param tradingUserManager manager of all TradingUsers
     * @param pendingItems list of all pending items that have been requested by users to be approved
     * @param items manager of all Items
     */
    public AdminMenuController(AdminManager adminManager, TradingUserManager tradingUserManager,
                               Map<Item, TradingUser> pendingItems, ItemManager items, ActionManager actionManager) {
        allPendingItems = pendingItems;
        um = tradingUserManager;
        am = adminManager;
        im = items;
        acm = actionManager;
    }

    public TradingUserManager getTUM(){
        return this.um;
    }

    public void approvePendingItem(Item item) {
        try {
            String username = allPendingItems.get(item).getUsername();
            TradingUser user = um.getTradingUser(username);
            im.addItem(item); // add to allItems master list of all existing items
            um.addItem(user, item, "inventory"); // add item to the TradingUser's inventory
            allPendingItems.remove(item);
            AddOrDeleteAction action = new AddOrDeleteAction(user, "inventory");
            action.setAdded(item);
            acm.addAction(action);
        } catch (InvalidTradingUserException e) {
            //
        }
    }

    public void rejectPendingItem(Item item) {
        allPendingItems.remove(item);
    }

    /**
     * creates a new admin which can only be done by the first admin
     * @param username user's account name identifier
     * @param password user's account password
     * @return true iff admin user is successfully created
     */
    public boolean createAdmin(String username, String password){
        boolean isSuccessful = false;
        if (currentAdmin.isFirstAdmin()) {
            try{
                am.addAdmin(username, password);// adds the new admin to the list of all AdminUsers
                isSuccessful = true;
            } catch (InvalidAdminException e) {//username taken
                }
        }
        return isSuccessful;
    }

    /**
     * Manually adds an item to any TradingUser's inventory or wishlist
     * @param tradingUser trading user in the system
     * @param item an item in the system
     * @param listType either "wishlist" or "inventory" as a stirng
     * @return true iff item has been added successfully
     * @throws NullPointerException
     */
    public boolean addItemToUser(TradingUser tradingUser, Item item, String listType) throws NullPointerException{
        boolean isSuccessful = false;
        if(listType.equals("wishlist")) {
            AddOrDeleteAction action = new AddOrDeleteAction(tradingUser, "wishlist");
            action.setAdded(item);
            acm.addAction(action);
            um.addItem(tradingUser, item, "wishlist");
            //im.addItem(item);//TODO item isn't actually added, need to handle when allItems in ItemManager is null
            isSuccessful = true;
        }else if (listType.equals("inventory")){
            AddOrDeleteAction action = new AddOrDeleteAction(tradingUser, "inventory");
            action.setAdded(item);
            acm.addAction(action);
            um.addItem(tradingUser, item, "inventory");
            //im.addItem(item);//TODO item isn't actually added, need to handle when allItems in ItemManager is null
            isSuccessful = true;
        }
        return isSuccessful;
    }

    /* helper method for changeUserThreshold */
    public void updateThreshold(String username, int newThreshold, String whichThreshold) throws InvalidTradingUserException {
        um.changeThreshold(um.getTradingUser(username), newThreshold, whichThreshold);
    }

    public boolean validAdmin(String username, String password) {
        return am.validAdmin(username, password);
    }

    public void setCurrentAdmin(String username) {
        try {
            currentAdmin = am.getAdmin(username);
        } catch (InvalidAdminException e) {
            // TODO
        }
    }

    public List<TradingUser> getAllTradingUsers() {
        return um.getAllTradingUsers();
    }

    public AdminUser getCurrentAdmin() {
        return currentAdmin;
    }

    public Map<Item, TradingUser> getAllPendingItems() {
        return allPendingItems;
    }

    public AdminManager getAm() {
        return am;
    }

    public ItemManager getIm() {
        return im;
    }


}