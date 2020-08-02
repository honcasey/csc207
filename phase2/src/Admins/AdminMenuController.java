package Admins;

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

    /**
     * Constructs an instance an AdminMenuController.
     * @param adminManager manager of all AdminUsers
     * @param tradingUserManager manager of all TradingUsers
     * @param pendingItems list of all pending items that have been requested by users to be approved
     * @param items manager of all Items
     */
    public AdminMenuController(AdminManager adminManager, TradingUserManager tradingUserManager,
                               Map<Item, TradingUser> pendingItems, ItemManager items) {
        allPendingItems = pendingItems;
        um = tradingUserManager;
        am = adminManager;
        im = items;
    }

    public TradingUserManager getTUM(){
        return this.um;
    }
//    /**
//     * Calls to different helper methods depending on admin's input choice in the main menu.
//     */
//    public void run() {
//        boolean userInteracting = true;
//        while (userInteracting) {
//            List<String> menu = amp.constructMainMenu();
//            int input = amp.handleOptionsByIndex(menu, false,"Admin Main Menu");
//
//            if (amp.indexToOption(input, menu, amp.checkPendingItems))
//                checkPendingItems();
//            if (amp.indexToOption(input, menu, amp.checkFlaggedUsers))
//                checkFlaggedUsers();
//            if (amp.indexToOption(input, menu, amp.createNewAdmin))
//                createAdmin();
//            if (amp.indexToOption(input, menu, amp.addItem))
//                addItemToUser();
//            if (amp.indexToOption(input, menu, amp.changeThreshold))
//                changeUserThreshold();
//            if (amp.indexToOption(input, menu, amp.checkUnfreezeAccounts))
//                checkFrozenUsers();
//            if (amp.indexToOption(input, menu, amp.logout)) {
//                System.out.println(amp.successfulLogout);
//                userInteracting = false; // stop the while loop
//            }
//        }
//    }

    protected void approvePendingItem(Item item) {
        try {
            TradingUser user = um.getTradingUser(allPendingItems.get(item).getUsername());
            im.addItem(item); // add to allItems master list of all existing items
            um.addItem(user, item, "inventory"); // add item to the TradingUser's inventory
            allPendingItems.remove(item);
        } catch (InvalidTradingUserException e) {
            //
        }
    }

    protected void rejectPendingItem(Item item) {
        allPendingItems.remove(item);
    }

    /* creates a new admin which can only be done by the first admin */
//    private void createAdmin() {
//        if (currentAdmin.isFirstAdmin()) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println(amp.enterName("new Admin"));
//            String username = scanner.nextLine();
//            try {
//                System.out.println(amp.enterPassword("new Admin"));
//                String password = scanner.nextLine();
//                am.addAdmin(username, password); // adds the new admin to the list of all AdminUsers
//                System.out.println(amp.successfullyCreated("New Admin User " + username));
//            } catch (InvalidAdminException e) {
//                System.out.println(amp.usernameTaken);
//            }
//        } else {
//            /* the logged in Admin is not the first admin and doesn't have the ability to create new Admin Users */
//            System.out.println(amp.permissionDenied);
//        }
//    }
    protected boolean createAdmin(String username, String password){
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

    /* manually adds an item to any TradingUser's inventory or wishlist */
//    private void addItemToUser() { // 2 text fields for items and Jlist of all users and 2 buttons inventory/wishlist
//        boolean userInteracting = true;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(amp.enterName("new Item"));
//        String itemName = scanner.nextLine();
//        Item newItem = new Item(itemName);
//        System.out.println(amp.enterName("TradingUser"));
//        String username = scanner.nextLine();
//        int optionChosen = amp.handleOptionsByIndex(amp.constructAddToListMenu(), true,
//                "Which list do you want to add this Item to?");
//        while (userInteracting) {
//            try {
//                if (optionChosen == amp.constructAddToListMenu().size()) {
//                    System.out.println(amp.previousMenu);
//                    userInteracting = false;
//                }
//                /* if admin chooses to add this item to the wishlist */
//                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToWishlist)) {
//                    um.addItem(um.getTradingUser(username), newItem, "wishlist");
//                    im.addItem(newItem); //add this new item
//                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
//                    userInteracting = false;
//                }
//                /* if admin chooses to add this item to the inventory */
//                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToInventory)) {
//                    um.addItem(um.getTradingUser(username), newItem, "inventory");
//                    im.addItem(newItem);
//                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
//                    userInteracting = false;
//                }
//                // if admin chose an invalid option
//                else { System.out.println(amp.validOptions(amp.constructUserLists()));}
//            } catch(InvalidTradingUserException e) {
//                System.err.println(amp.usernameInvalid);
//                userInteracting = false;
//            }
//        }
//    }

    /* helper method for changeUserThreshold */
    public void updateThreshold(String username, int newThreshold, String whichThreshold) throws InvalidTradingUserException {
        um.changeThreshold(um.getTradingUser(username), newThreshold, whichThreshold);
    }


    /* checks all TradingUsers that are frozen and have requested their account to be unfrozen */
    // Jcardlayout
//////    private void checkFrozenUsers() {
//////        boolean userInteracting = true;
//////        while (userInteracting) {
//////            if (am.getFrozenAccounts().isEmpty()) {
//////                System.out.println(amp.empty("Frozen TradingUser request"));
//////                userInteracting = false;
//////            }
//////            else {
//////                Iterator<TradingUser> frozenUserIterator = am.getFrozenAccounts().iterator();
//////
//////                /* makes a list of users to delete from the pendingFrozenTradingUsers list if they're unfrozen */
//////                ArrayList<TradingUser> usersToDelete = new ArrayList<>();
//////
//////                while (frozenUserIterator.hasNext()) {
//////                    TradingUser curr = frozenUserIterator.next();
//////                    System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
//////                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingFrozenUsersMenu(), true, "Check Frozen Users");
//////                    if (optionChosen == amp.constructPendingFrozenUsersMenu().size()) { // if "go back" is chosen
//////                        System.out.println(amp.previousMenu);
//////                        userInteracting = false;
//////                        break;
//////                    } else {
//////                        // if an admin chooses to unfreeze
//////                        if (amp.indexToOption(optionChosen, amp.constructPendingFrozenUsersMenu(), amp.unfreezeAccount)) {
//////                            um.unfreezeAccount(curr); // status of TradingUser now set from frozen back to active
//////                            usersToDelete.add(curr);
//////                            System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
//////                            userInteracting = false;
//////                        }
//////                        // else it goes to the next user
//////                    }
//////                }
////
////                /* remove all Trading Users that have been unfrozen from the pendingFrozenTradingUsers list */
////                am.getFrozenAccounts().removeAll(usersToDelete);
////            }
//        }
//    }



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
}