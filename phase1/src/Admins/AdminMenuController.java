package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Items.ItemManager;
import Users.TradingUser;
import Users.TradingUserManager;

import java.util.*;

public class AdminMenuController {
    private final AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final Map<Item, TradingUser> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private final ItemManager im;

    public AdminMenuController(AdminManager adminManager, TradingUserManager tradingUserManager,
                               Map<Item, TradingUser> pendingItems, AdminUser admin, ItemManager items) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = tradingUserManager;
        am = adminManager;
        im = items;
    }

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

    private void checkPendingItems() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (allPendingItems.isEmpty()) {
                System.out.println(amp.empty("Pending Items"));
                userInteracting = false;
            }
            else {
                Iterator<Item> itemIterator = allPendingItems.keySet().iterator();
                ArrayList<Item> keysToDelete = new ArrayList<>();

                while (itemIterator.hasNext()) {
                    Item curr = itemIterator.next();
                    System.out.println("Current Item Name:" + curr.toString()); //prints the current item + the options
                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingItemsMenu(), true, "Actions");
                    if (optionChosen == amp.constructPendingItemsMenu().size()) {
                        System.out.println(amp.previousMenu);
                        userInteracting = false;
                    } else {
                        if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.approveItem)) {
                            try {
                                TradingUser user = um.getTradingUser(allPendingItems.get(curr).getUsername());
                                im.addItem(curr);
                                um.addItem(user, curr, "inventory");
                               // itemIterator.remove();
                                keysToDelete.add(curr);
                                System.out.println(amp.addItem("approved"));
                            } catch (InvalidTradingUserException e) {
                                //
                            }

                        }
                        else if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.declineItem)) {
                            //itemIterator.remove();
                            keysToDelete.add(curr);
                            System.out.println(amp.addItem("declined"));
                        }
                    }
                }
                allPendingItems.keySet().removeAll(keysToDelete);  //we deleted all of the items that were approved or rejected
            }
        }
    }

    private void createAdmin() {
        if (currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(amp.enterName("new Admin"));
            String username = scanner.nextLine();
            try {
                System.out.println(amp.enterPassword("new Admin"));
                String password = scanner.nextLine();
                am.addAdmin(username, password);
                System.out.println(amp.successfullyCreated("New Admin TradingUser " + username));
            } catch (InvalidAdminException e) {
                System.out.println(amp.usernameTaken);
            }
        } else {
            System.out.println(amp.permissionDenied);
        }
    }

    private void addItemToUser() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("new Item"));
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName); // TO-DO: prompt admin to enter a description for this item
        System.out.println(amp.enterName("TradingUser"));
        String username = scanner.nextLine();
        int optionChosen = amp.handleOptionsByIndex(amp.constructAddToListMenu(), true,
                "Which List do you want to add this Item to?");
        while (userInteracting) {
            try {
                if (optionChosen == amp.constructAddToListMenu().size()) {
                    System.out.println(amp.previousMenu);
                    userInteracting = false;
                }
                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToWishlist)) {
                    um.addItem(um.getTradingUser(username), newItem, "wishlist");
                    im.addItem(newItem);
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
                    userInteracting = false;
                }
                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToInventory)) {
                    um.addItem(um.getTradingUser(username), newItem, "inventory");
                    im.addItem(newItem);
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
                    userInteracting = false;
                }
                else { System.out.println(amp.validOptions(amp.constructUserLists()));}
            } catch(InvalidTradingUserException e) {
                System.err.println(amp.usernameInvalid);
                userInteracting = false;
            }
        }
    }

    private void helperChangeThreshold(String username, String whichThreshold) throws InvalidTradingUserException { // helper method for changeUserThreshold
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.whichThreshold(whichThreshold));
        int newThreshold = scanner.nextInt();
        um.changeThreshold(um.getTradingUser(username), newThreshold, whichThreshold);
        System.out.println(amp.successfullyChanged(whichThreshold, username));
    }

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
                default:
                    System.out.println(amp.validOptions(amp.constructAllThresholds()));
                    break;
            }
        } catch(InvalidTradingUserException e) {
            System.err.print(amp.usernameInvalid);
        }
    }

    private void checkFrozenUsers() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (am.getPendingFrozenTradingUsers().isEmpty()) {
                System.out.println(amp.empty("Frozen TradingUser request."));
                userInteracting = false;
            }
            else {
                Iterator<TradingUser> frozenUserIterator = am.getPendingFrozenTradingUsers().iterator();
                ArrayList<TradingUser> usersToDelete = new ArrayList<>();

                while (frozenUserIterator.hasNext()) {
                    TradingUser curr = frozenUserIterator.next();
                    System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingFrozenUsersMenu(), true, "Check Frozen Users");
                    if (optionChosen == amp.constructPendingFrozenUsersMenu().size()) {
                        System.out.println(amp.previousMenu);
                        userInteracting = false;
                        break;
                    } else {
                        // if an admin chooses to unfreeze
                        if (amp.indexToOption(optionChosen, amp.constructPendingFrozenUsersMenu(), amp.unfreezeAccount)) {
                            um.unfreezeAccount(curr);
                            usersToDelete.add(curr);
                            System.out.println(amp.accountFrozen(curr.toString(), curr.getStatus()));
                            userInteracting = false;
                        }
                        // else it goes to the next user
                    }
                }
                am.getPendingFrozenTradingUsers().removeAll(usersToDelete);
            }
        }
    }

    private void checkFlaggedUsers() {
        boolean userInteracting = true;
        while (userInteracting) {
            if (am.getFlaggedAccounts().isEmpty()) {
                System.out.println(amp.empty("Flagged Users."));
                userInteracting = false;
            }
            else {
                Iterator<TradingUser> flaggedUserIterator = am.getFlaggedAccounts().iterator();
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
                            am.getFrozenAccounts().add(curr);
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
                am.getFlaggedAccounts().removeAll(usersToDelete);
            }
        }
    }
}
