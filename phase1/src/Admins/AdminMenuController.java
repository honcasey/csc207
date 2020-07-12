package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidUserException;
import Items.Item;
import Users.TradingUser;
import Users.TradingUserManager;

import java.util.*;

public class AdminMenuController {
    private final AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final TradingUserManager um;
    private final Map<Item, TradingUser> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();

    public AdminMenuController(AdminManager adminManager, TradingUserManager tradingUserManager,
                               Map<Item, TradingUser> pendingItems, AdminUser admin) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = tradingUserManager;
        am = adminManager;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;

        while (userInteracting) {
            List<String> menu = amp.constructMainMenu();
            int input = amp.handleOptionsByIndex(menu, false,"Admin Main Menu");

            if (amp.indexToOption(input, menu, amp.checkPendingItems))
                checkPendingItems();
            if (amp.indexToOption(input, menu, amp.checkFlaggedUsers))
                checkUsers("flaggedUsers");
            if (amp.indexToOption(input, menu, amp.createNewAdmin))
                createAdmin();
            if (amp.indexToOption(input, menu, amp.addItem))
                addItemToUser();
            if (amp.indexToOption(input, menu, amp.changeThreshold)) {
                changeUserThreshold();}
            if (amp.indexToOption(input, menu, amp.checkUnfreezeAccounts)) {
                checkUsers("pendingFrozenUsers"); }
            if (amp.indexToOption(input, menu, amp.logout)) {
                System.out.println(amp.successfulLogout());
                userInteracting = false; // stop the while loop
            }
        }
    }

    private void approveInventory(TradingUser tradingUser, Item item, boolean approved) { // helper method for checkPendingItems
        if (approved) { um.addItem(tradingUser, item, "inventory");
        System.out.println(amp.addItem("approved"));}
        else { allPendingItems.remove(item);
        System.out.println(amp.addItem("declined"));}
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

                while (itemIterator.hasNext()) {
                    System.out.println(itemIterator.next().toString()); //prints the current item + the options
                    int optionChosen = amp.handleOptionsByIndex(amp.constructPendingItemsMenu(), true, "Actions");
                    if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.approveItem)) { // TO-DO: try catch block here?
                        approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                    }
                    else if (amp.indexToOption(optionChosen, amp.constructPendingItemsMenu(), amp.declineItem)) {
                        approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), false);
                    }
                }
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
                System.out.println(amp.usernameTaken());
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
        Item newItem = new Item(itemName);
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
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
                }
                else if (amp.indexToOption(optionChosen, amp.constructAddToListMenu(), amp.addToInventory)) {
                    um.addItem(um.getTradingUser(username), newItem, "wishlist");
                    System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
                }
                else { System.out.println(amp.validOptions(amp.userLists));}
            } catch(InvalidUserException e) {
                System.err.println(amp.usernameInvalid());
                userInteracting = false;
            }
        }

    }

    private void helperChangeThreshold(String username, String whichThreshold) throws InvalidUserException { // helper method for changeUserThreshold
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
        String whichThreshold = amp.handleOptions(amp.allThresholds, true, "TradingUser Thresholds");
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
                    System.out.println(amp.validOptions(amp.allThresholds));
                    break;
            }
        } catch(InvalidUserException e) {
            System.err.print(amp.usernameInvalid());
        }
    }

    private void checkUsers(String listType){
        boolean userInteracting = true;
        while (userInteracting) {
            if (listType.equals("pendingFrozenUsers")) {
                if (am.getPendingFrozenTradingUsers().isEmpty()) {
                    System.out.println(amp.empty("Frozen TradingUser Requests"));
                    userInteracting = false;
                } else {
                    for (TradingUser tradingUser : am.getPendingFrozenTradingUsers()) {
                        System.out.println(tradingUser.toString());
                        int optionChosen = amp.handleOptionsByIndex(amp.constructPendingFrozenUsersMenu(), true, "Check Frozen Users");
                        if (optionChosen == amp.constructPendingFrozenUsersMenu().size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen, amp.constructPendingFrozenUsersMenu(), amp.unfreezeAccount)) {
                            um.unfreezeAccount(tradingUser);
                            am.getPendingFrozenTradingUsers().remove(tradingUser);
                            System.out.println(amp.accountFrozen(tradingUser.toString(), tradingUser.getStatus()));
                        }
                    }
                }
            }
            if (listType.equals("flaggedUsers")) {
                if (am.getFlaggedAccounts().isEmpty()) {
                    System.out.println(amp.empty("Flagged Users"));
                    userInteracting = false;
                } else {
                    for (TradingUser tradingUser : am.getFlaggedAccounts()) {
                        System.out.println(tradingUser.toString());
                        int optionChosen2 = amp.handleOptionsByIndex(amp.constructFlaggedUsersMenu(), true, "Check Flagged Users");
                        if (optionChosen2 == amp.constructFlaggedUsersMenu().size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen2, amp.constructFlaggedUsersMenu(), amp.freezeAccount)) {
                            um.freezeAccount(tradingUser);
                            am.getFrozenAccounts().add(tradingUser);
                            System.out.println(amp.accountFrozen(tradingUser.toString(), tradingUser.getStatus()));
                        } else if (amp.indexToOption(optionChosen2, amp.constructFlaggedUsersMenu(), amp.unfreezeAccount)) {
                            um.unfreezeAccount(tradingUser);
                            am.getFlaggedAccounts().remove(tradingUser);
                            System.out.println(amp.accountFrozen(tradingUser.toString(), tradingUser.getStatus()));
                        }
                    }
                }
            }
        }
    }
}