package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidUserException;
import Items.Item;
import Users.TradingUser;
import Users.UserManager;

import java.util.*;

public class AdminMenuController {
    private final AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final UserManager um;
    private final Map<Item, TradingUser> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private int input; // do we need this?

    public AdminMenuController(AdminManager adminManager, UserManager userManager,
                               Map<Item, TradingUser> pendingItems, AdminUser admin) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = userManager;
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
        Scanner scanner = new Scanner(System.in); // do we need this?
        while (userInteracting) {
            if (allPendingItems.isEmpty()) {
                amp.empty("Pending Items");
                userInteracting = false;
            }
            else {
                Iterator<Item> itemIterator = allPendingItems.keySet().iterator();
                List<String> optionList = new ArrayList<>();
                optionList.add("Approve item for TradingUser's inventory.");
                optionList.add("Decline item.");
                optionList.add("Go to next item.");

                while (itemIterator.hasNext()) {
                    System.out.println(itemIterator.next().toString()); //prints the current item + the options
                    int optionChosen = amp.handleOptionsByIndex(optionList, true, "Actions");
                    if (amp.indexToOption(optionChosen, optionList, "Approve item for TradingUser's inventory.")) { // TO-DO: try catch block here?
                        approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                    }
                    else if (amp.indexToOption(optionChosen, optionList, "Decline item.")) {
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
                System.out.println("New Admin TradingUser " + username + " successfully created.");
            } catch (InvalidAdminException e) {
                System.out.println(amp.usernameTaken());
            }
        } else {
            System.out.println("Permission denied, only the first admin can create new administrative user accounts.");
        }
    }

    private void addItemToUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("new Item"));
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName);
        System.out.println(amp.enterName("TradingUser"));
        String username = scanner.nextLine();
        System.out.println("Would you like to add this item to the user's wishlist or inventory?");
        String whichList = scanner.nextLine();
        try {
            if (whichList.equals("wishlist")) {
                um.addItem(um.getUser(username), newItem, "wishlist");
                System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
            }
            else if (whichList.equals("inventory")) {
                um.addItem(um.getUser(username), newItem, "wishlist");
                System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
            }
            else { System.out.println(amp.validOptions(amp.userLists));}
        } catch(InvalidUserException e) {
            System.err.println(amp.usernameInvalid());
        }
    }

    private void helperChangeThreshold(String username, String whichThreshold) throws InvalidUserException { // helper method for changeUserThreshold
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.whichThreshold(whichThreshold));
        int newThreshold = scanner.nextInt();
        um.changeThreshold(um.getUser(username), newThreshold, whichThreshold);
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
                    System.out.println(amp.currentThreshold("minimum number of times that this user must lend something before they can borrow/trade",
                            um.getUser(username).getBorrowThreshold()));
                    helperChangeThreshold(username, whichThreshold);
                    break;
                }
                case "weekly": {
                    System.out.println(amp.currentThreshold("maximum number of transactions that this user can participate in a week",
                            um.getUser(username).getWeeklyThreshold()));
                    helperChangeThreshold(username, whichThreshold);
                    break;
                }
                case "incomplete": {
                    System.out.println(amp.currentThreshold("maximum number of incomplete transactions before this user's account is frozen",
                            um.getUser(username).getIncompleteThreshold()));
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
        Scanner scanner = new Scanner(System.in); // do we need this?
        while (userInteracting) {
            if (listType.equals("pendingFrozenUsers")) {
                if (am.getPendingFrozenTradingUsers().isEmpty()) {
                    System.out.println(amp.empty("Frozen TradingUser Requests"));
                } else {
                    for (TradingUser tradingUser : am.getPendingFrozenTradingUsers()) {
                        System.out.println(tradingUser.toString());
                        List<String> optionList = new ArrayList<>();
                        optionList.add("Unfreeze Account."); // should i add a "leave tradingUser frozen" option, or just assume the admin knows by skipping the tradingUser, they're leaving that tradingUser frozen?
                        optionList.add("Go to next tradingUser."); // or change this option to "Leave TradingUser Frozen"?
                        int optionChosen = amp.handleOptionsByIndex(optionList, true, "Check Frozen Users");
                        if (optionChosen == optionList.size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen, optionList, "Unfreeze Account.")) {
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
                } else {
                    for (TradingUser tradingUser : am.getFlaggedAccounts()) {
                        System.out.println(tradingUser.toString());
                        List<String> optionList2 = new ArrayList<>(); // these 4 lines could be moved to before the while loop to shorten
                        optionList2.add("Freeze Account.");
                        optionList2.add("Unfreeze Account.");
                        optionList2.add("Go to next tradingUser.");
                        int optionChosen2 = amp.handleOptionsByIndex(optionList2, true, "Check Flagged Users");
                        if (optionChosen2 == optionList2.size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen2, optionList2, "Freeze Account.")) {
                            um.freezeAccount(tradingUser);
                            am.getFrozenAccounts().add(tradingUser);
                            System.out.println(amp.accountFrozen(tradingUser.toString(), tradingUser.getStatus()));
                        } else if (amp.indexToOption(optionChosen2, optionList2, "Unfreeze Account.")) {
                            um.unfreezeAccount(tradingUser);
                            am.getFlaggedAccounts().remove(tradingUser);
                            System.out.println(amp.accountFrozen(tradingUser.toString(), tradingUser.getStatus()));
                        }
                    }
                }
        }
    }
}}