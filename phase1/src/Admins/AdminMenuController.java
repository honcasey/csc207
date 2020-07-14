package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Items.ItemManager;
import Users.TradingUser;
import Users.TradingUserManager;

import java.util.*;

/**
 * Decides which use case/manager methods to call depending on the user input taken from the presenter.
 * <p>It stores instances of all use cases/managers (AdminManager, TradingUserManager, ItemManager), the AdminMenuPresenter,
 *  * and a list of allPendingItems (which is the list of all items that have been requested by a TradingUser to be added
 *  * to their inventory). <p/>
 */
public class AdminMenuController {
    private final AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final Map<Item, TradingUser> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private final ItemManager im;

    /**
     * Constructs an instance an AdminMenuController.
     * @param adminManager manager of all AdminUsers
     * @param tradingUserManager manager of all TradingUsers
     * @param pendingItems list of all pending items that have been requested by users to be approved
     * @param admin currently logged-in AdminUser
     * @param items manager of all Items
     */
    public AdminMenuController(AdminManager adminManager, TradingUserManager tradingUserManager,
                               Map<Item, TradingUser> pendingItems, AdminUser admin, ItemManager items) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = tradingUserManager;
        am = adminManager;
        im = items;
    }

    /**
     * Calls to different helper methods depending on admin's input choice in the main menu.
     */
    public void run() {
        boolean userInteracting = true;
        while (userInteracting) {
            List<String> menu = amp.constructMainMenu();
            int input = amp.handleOptionsByIndex(menu, false,"Admin Main Menu");

            if (amp.indexToOption(input, menu, amp.checkPendingItems))
                checkPendingItems();
            if (amp.indexToOption(input, menu, amp.checkFlaggedUsers))
                checkFlaggedUsers();
            if (amp.indexToOption(input, menu, amp.createNewAdmin))
                createAdmin();
            if (amp.indexToOption(input, menu, amp.addItem))
                addItemToUser();
            if (amp.indexToOption(input, menu, amp.changeThreshold)) {
                changeUserThreshold();}
            if (amp.indexToOption(input, menu, amp.checkUnfreezeAccounts)) {
                checkFrozenUsers(); }
            if (amp.indexToOption(input, menu, amp.logout)) {
                System.out.println(amp.successfulLogout);
                userInteracting = false; // stop the while loop
            }
        }
    }

    /* check all pending items that have been requested by users to be approved for their inventory */
    private void checkPendingItems() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (allPendingItems.isEmpty()) {
                System.out.println(amp.empty("Pending Items"));
                userInteracting = false;
            } else {
                Iterator<Item> itemIterator = allPendingItems.keySet().iterator();

                // makes list of items to be removed from the allPendingItems list once they are approved
                ArrayList<Item> keysToDelete = new ArrayList<>();

                while (itemIterator.hasNext()) {
                    Item curr = itemIterator.next();
                    System.out.println("Current Item Name:" + curr.toString()); // prints the current item + the options

                    /* prints actions that the admin can do on this pending item (approve, decline, go to next) */
                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingItemsMenu(), true, "Actions");
                    if (optionChosen == amp.constructPendingItemsMenu().size()) { // if "go back" is chosen
                        System.out.println(amp.previousMenu);
                        userInteracting = false;
                    } else {
                        if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.approveItem)) {
                            try {
                                TradingUser user = um.getTradingUser(allPendingItems.get(curr).getUsername());
                                im.addItem(curr); // add to allItems master list of all existing items
                                um.addItem(user, curr, "inventory"); // add item to the TradingUser's inventory
                                keysToDelete.add(curr);
                                System.out.println(amp.addItem("approved"));
                            } catch (InvalidTradingUserException e) {
                                //
                            }
                        } // if admin decides to decline this item for this user
                        else if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.declineItem)) {
                            keysToDelete.add(curr);
                            System.out.println(amp.addItem("declined"));
                        }
                    }
                }
                allPendingItems.keySet().removeAll(keysToDelete);  // deletes all of the items that were approved/rejected
            }
        }
    }

    /* creates a new admin which can only be done by the first admin */
    private void createAdmin() {
        if (currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(amp.enterName("new Admin"));
            String username = scanner.nextLine();
            try {
                System.out.println(amp.enterPassword("new Admin"));
                String password = scanner.nextLine();
                am.addAdmin(username, password); // adds the new admin to the list of all AdminUsers
                System.out.println(amp.successfullyCreated("New Admin TradingUser " + username));
            } catch (InvalidAdminException e) {
                System.out.println(amp.usernameTaken);
            }
        } else {
            /* the logged in Admin is not the first admin and doesn't have the ability to create new Admin Users */
            System.out.println(amp.permissionDenied);
        }
    }

    /* manually adds an item to any TradingUser's inventory or wishlist */
    private void addItemToUser() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("new Item"));
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName);
        System.out.println(amp.enterName("TradingUser"));
        String username = scanner.nextLine();
        int optionChosen = amp.handleOptionsByIndex(amp.constructAddToListMenu(), true,
                "Which list do you want to add this Item to?");
        while (userInteracting) {
            try {
                if (optionChosen == amp.constructAddToListMenu().size()) {
                    System.out.println(amp.previousMenu);
                    userInteracting = false;
                }
                /* if admin chooses to add this item to the wishlist */
                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToWishlist)) {
                    um.addItem(um.getTradingUser(username), newItem, "wishlist");
                    im.addItem(newItem); //add this new item
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
                    userInteracting = false;
                }
                /* if admin chooses to add this item to the inventory */
                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToInventory)) {
                    um.addItem(um.getTradingUser(username), newItem, "inventory");
                    im.addItem(newItem);
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
                    userInteracting = false;
                }
                // if admin chose an invalid option
                else { System.out.println(amp.validOptions(amp.constructUserLists()));}
            } catch(InvalidTradingUserException e) {
                System.err.println(amp.usernameInvalid);
                userInteracting = false;
            }
        }
    }

    /* helper method for changeUserThreshold */
    private void helperChangeThreshold(String username, String whichThreshold) throws InvalidTradingUserException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.whichThreshold(whichThreshold));
        int newThreshold = scanner.nextInt();
        um.changeThreshold(um.getTradingUser(username), newThreshold, whichThreshold);
        System.out.println(amp.successfullyChanged(whichThreshold, username));
    }

    /* changes one of the TradingUser's thresholds */
    private void changeUserThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("TradingUser"));
        String username = scanner.nextLine();
        String whichThreshold = amp.handleOptions(amp.constructAllThresholds(), true, "TradingUser Thresholds");
        try {
            switch (whichThreshold) {
                case "borrow": {
                    System.out.println(amp.currentThreshold(amp.borrowThresholdDescription,
                            um.getTradingUser(username).getBorrowThreshold()));
                    helperChangeThreshold(username, whichThreshold);
                    break;
                }
                case "weekly": {
                    System.out.println(amp.currentThreshold(amp.weeklyThresholdDescription,
                            um.getTradingUser(username).getWeeklyThreshold()));
                    helperChangeThreshold(username, whichThreshold);
                    break;
                }
                case "incomplete": {
                    System.out.println(amp.currentThreshold(amp.incompleteThresholdDescription,
                            um.getTradingUser(username).getIncompleteThreshold()));
                    helperChangeThreshold(username, whichThreshold);
                    break;
                }
                default: // if the input isn't either borrow, weekly, or incomplete
                    System.out.println(amp.validOptions(amp.constructAllThresholds()));
                    break;
            }
        } catch(InvalidTradingUserException e) {
            System.err.print(amp.usernameInvalid);
        }
    }

    /* checks all TradingUsers that are frozen and have requested their account to be unfrozen */
    private void checkFrozenUsers() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (am.getFrozenAccounts().isEmpty()) {
                System.out.println(amp.empty("Frozen TradingUser request."));
                userInteracting = false;
            }
            else {
                Iterator<TradingUser> frozenUserIterator = am.getFrozenAccounts().iterator();

                /* makes a list of users to delete from the pendingFrozenTradingUsers list if they're unfrozen */
                ArrayList<TradingUser> usersToDelete = new ArrayList<>();

                while (frozenUserIterator.hasNext()) {
                    TradingUser curr = frozenUserIterator.next();
                    System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingFrozenUsersMenu(), true, "Check Frozen Users");
                    if (optionChosen == amp.constructPendingFrozenUsersMenu().size()) { // if "go back" is chosen
                        System.out.println(amp.previousMenu);
                        userInteracting = false;
                        break;
                    } else {
                        // if an admin chooses to unfreeze
                        if (amp.indexToOption(optionChosen, amp.constructPendingFrozenUsersMenu(), amp.unfreezeAccount)) {
                            um.unfreezeAccount(curr); // status of TradingUser now set from frozen back to active
                            usersToDelete.add(curr);
                            System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                            userInteracting = false;
                        }
                        // else it goes to the next user
                    }
                }

                /* remove all Trading Users that have been unfrozen from the pendingFrozenTradingUsers list */
                am.getFrozenAccounts().removeAll(usersToDelete);
            }
        }
    }

    /* checks all TradingUsers that have been flagged by the system to have their account frozen */
    private void checkFlaggedUsers() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (am.getFlaggedAccounts().isEmpty()) {
                System.out.println(amp.empty("Flagged Users."));
                userInteracting = false;
            }
            else {
                Iterator<TradingUser> flaggedUserIterator = am.getFlaggedAccounts().iterator();

                /* makes a list of users to delete from the flaggedAccounts list once they're frozen/unfrozen */
                ArrayList<TradingUser> usersToDelete = new ArrayList<>();

                while (flaggedUserIterator.hasNext()) {
                    TradingUser curr = flaggedUserIterator.next();
                    System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                    int optionChosen = amp.handleOptionsByIndex(amp.constructFlaggedUsersMenu(), true, "Check Flagged Users");
                    if (optionChosen == amp.constructFlaggedUsersMenu().size()) {
                        System.out.println(amp.previousMenu);
                        userInteracting = false;
                        break;
                    } else {
                        // admin wants to freeze this account
                        if (amp.indexToOption(optionChosen, amp.constructFlaggedUsersMenu(), amp.freezeAccount)) {
                            um.freezeAccount(curr);
                            usersToDelete.add(curr);
                            System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen, amp.constructFlaggedUsersMenu(), amp.unfreezeAccount)) {
                            // admin decides not to freeze this account
                            um.unfreezeAccount(curr);
                            usersToDelete.add(curr);
                            System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                            userInteracting = false;
                        } // else it goes to the next flagged user
                    }
                }
                /* remove all Trading Users that have been frozen/unfrozen from the flaggedAccount list */
                am.getFlaggedAccounts().removeAll(usersToDelete);
            }
        }
    }
}
