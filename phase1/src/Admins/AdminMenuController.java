package Admins;

import Exceptions.InvalidAdminException;
import Exceptions.InvalidUserException;
import Items.Item;
import Users.User;
import Users.UserManager;

import java.util.*;

public class AdminMenuController {
    public AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final UserManager um;
    private HashMap<Item, User> allPendingItems;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private int input;

    public AdminMenuController(AdminManager adminManager, UserManager userManager,
                               HashMap<Item, User> pendingItems, AdminUser admin) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = userManager;
        am = adminManager;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;

        while (userInteracting) {
            String input = amp.HandleOptions(amp.constructMainMenu(), false,"Admin Main Menu");

            switch (input) { //TO-DO: handleoptionsbyindex ?
                case "Check Pending Items for Approval":
                    checkPendingItems();
                    break;
                case "Check Flagged Users":
                    checkUsers("flaggedUsers");
                    break;
                case "Create New Admin User":
                    createAdmin();
                    break;
                case "Add New Item to a User's Wishlist/Inventory":
                    addItemToUser();
                    break;
                case "Change User Threshold":
                    changeUserThreshold();
                    break;
                case "Check Unfreeze Account Requests":
                    checkUsers("pendingFrozenUsers");
                    break;
                case "Log Out":
                    System.out.println(amp.logout());
                    // stop the while loop
                    userInteracting = false;
                    break;
                default:
                    System.out.println(amp.invalidOption());
                    break;
            }
        }
    }

    private void approveInventory(User user, Item item, boolean approved) { // helper method for checkPendingItems
        if (approved) { um.addItem(user, item, "inventory");
        System.out.println("Item has been approved.");}
        else { allPendingItems.remove(item);
        System.out.println("Item has been declined.");}
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
                    int optionChosen = amp.HandleOptionsByIndex(optionList, true, "Actions");
                    if (optionList.get(optionChosen).equals("Approve item for User's inventory.")) { // TO-DO: try catch block here?
                        approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                    }
                    else if (optionList.get(optionChosen).equals("Decline item.")) {
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
                System.out.println("Please enter new Administrative User's password: ");
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
            System.out.println("Username does not exist. Please enter an existing User's username.");
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
        String whichThreshold = amp.HandleOptions(amp.allThresholds, true, "User Thresholds");
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
            System.err.print("Username does not exist. Please enter an existing User's username."); // TO-DO: get exception to print this message
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
                        int optionChosen = amp.HandleOptionsByIndex(optionList, true, "Check Frozen Users");
                        if (optionChosen == optionList.size()) {
                            userInteracting = false;
                        } else if (optionList.get(optionChosen).equals("Unfreeze Account.")) {
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
                        List<String> optionList2 = new ArrayList<>();
                        optionList2.add("Freeze Account.");
                        optionList2.add("Unfreeze Account.");
                        optionList2.add("Go to next user.");
                        int optionChosen2 = amp.HandleOptionsByIndex(optionList2, true, "Check Flagged Users");
                        if (optionChosen2 == optionList2.size()) {
                            userInteracting = false;
                        } else if (optionList2.get(optionChosen2).equals("Freeze Account.")) {
                            um.freezeAccount(user);
                            am.getFrozenAccounts().add(user);
                            System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                        } else if (optionList2.get(optionChosen2).equals("Unfreeze Account.")) {
                            um.unfreezeAccount(user);
                            am.getFlaggedAccounts().remove(user);
                            System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                        }
                    }
                }
        }
    }
}}