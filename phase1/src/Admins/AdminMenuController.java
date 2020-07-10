package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidUserException;
import Items.Item;
import Users.User;
import Users.UserManager;

import java.util.*;

public class AdminMenuController {
    private final AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final UserManager um;
    private final Map<Item, User> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private int input; // do we need this?

    public AdminMenuController(AdminManager adminManager, UserManager userManager,
                               Map<Item, User> pendingItems, AdminUser admin) {
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

            if (amp.indexToOption(input, menu, "Check Pending Items for Approval"))
                checkPendingItems();
            if (amp.indexToOption(input, menu, "Check Flagged Users"))
                checkUsers("flaggedUsers");
            if (amp.indexToOption(input, menu, "Create New Admin User"))
                createAdmin();
            if (amp.indexToOption(input, menu, "Add New Item to a User's Wishlist/Inventory"))
                addItemToUser();
            if (amp.indexToOption(input, menu, "Change User Threshold")) {
                changeUserThreshold();}
            if (amp.indexToOption(input, menu, "Check Unfreeze Account Requests")) {
                checkUsers("pendingFrozenUsers"); }
            if (amp.indexToOption(input, menu, "Log Out")) {
                System.out.println(amp.logout());
                userInteracting = false; // stop the while loop
            }
        }
    }

    private void approveInventory(User user, Item item, boolean approved) { // helper method for checkPendingItems
        if (approved) { um.addItem(user, item, "inventory");
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
                optionList.add("Approve item for User's inventory.");
                optionList.add("Decline item.");
                optionList.add("Go to next item.");

                while (itemIterator.hasNext()) {
                    System.out.println(itemIterator.next().toString()); //prints the current item + the options
                    int optionChosen = amp.handleOptionsByIndex(optionList, true, "Actions");
                    if (amp.indexToOption(optionChosen, optionList, "Approve item for User's inventory.")) { // TO-DO: try catch block here?
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
                System.out.println("New Admin User " + username + " successfully created.");
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
        System.out.println(amp.enterName("User"));
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
        System.out.println(amp.enterName("User"));
        String username = scanner.nextLine();
        String whichThreshold = amp.handleOptions(amp.allThresholds, true, "User Thresholds");
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
                if (am.getPendingFrozenUsers().isEmpty()) {
                    System.out.println(amp.empty("Frozen User Requests"));
                } else {
                    for (User user : am.getPendingFrozenUsers()) {
                        System.out.println(user.toString());
                        List<String> optionList = new ArrayList<>();
                        optionList.add("Unfreeze Account."); // should i add a "leave user frozen" option, or just assume the admin knows by skipping the user, they're leaving that user frozen?
                        optionList.add("Go to next user."); // or change this option to "Leave User Frozen"?
                        int optionChosen = amp.handleOptionsByIndex(optionList, true, "Check Frozen Users");
                        if (optionChosen == optionList.size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen, optionList, "Unfreeze Account.")) {
                            um.unfreezeAccount(user);
                            am.getPendingFrozenUsers().remove(user);
                            System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                        }
                    }
                }
            }
            if (listType.equals("flaggedUsers")) {
                if (am.getFlaggedAccounts().isEmpty()) {
                    System.out.println(amp.empty("Flagged Users"));
                } else {
                    for (User user: am.getFlaggedAccounts()) {
                        System.out.println(user.toString());
                        List<String> optionList2 = new ArrayList<>(); // these 4 lines could be moved to before the while loop to shorten
                        optionList2.add("Freeze Account.");
                        optionList2.add("Unfreeze Account.");
                        optionList2.add("Go to next user.");
                        int optionChosen2 = amp.handleOptionsByIndex(optionList2, true, "Check Flagged Users");
                        if (optionChosen2 == optionList2.size()) {
                            userInteracting = false;
                        } else if (amp.indexToOption(optionChosen2, optionList2, "Freeze Account.")) {
                            um.freezeAccount(user);
                            am.getFrozenAccounts().add(user);
                            System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                        } else if (amp.indexToOption(optionChosen2, optionList2, "Unfreeze Account.")) {
                            um.unfreezeAccount(user);
                            am.getFlaggedAccounts().remove(user);
                            System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                        }
                    }
                }
        }
    }
}}