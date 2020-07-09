package Admins;

import Admins.AdminManager;
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
            System.out.println(amp.mainMenu());
            input = scanner.nextInt();

            if (input == 1) {
                checkPendingItems();
            } else if (input == 2) {
                checkUsers("flaggedUsers");
            } else if (input == 3) {
                createAdmin();
            } else if (input == 4) {
                addItemToUser();
            } else if (input == 5) {
                changeUserThreshold();
            } else if (input == 6) {
                checkUsers("pendingFrozenUsers");
            } else if (input == 7) {
                System.out.println(amp.logout());
                // stop the while loop
                userInteracting = false;
            } else { System.out.println(amp.invalidOption()); }
        }
    }

    private void approveInventory(User user, Item item, boolean approved) {
        if (approved) { um.addItem(user, item, "inventory");
        System.out.println("Items.Item has been approved.");}
        else { allPendingItems.remove(item);
        System.out.println("Items.Item has been declined.");}
    }

    private void checkPendingItems() {
        Scanner scanner = new Scanner(System.in);
        if (allPendingItems.isEmpty()) { amp.empty("Pending Items"); }
        else {
            Iterator<Item> itemIterator = allPendingItems.keySet().iterator();
            while (itemIterator.hasNext()) {
                System.out.println(itemIterator.next());
                System.out.println("1. Approve item for Users.User's inventory. \n2. Decline item. \n3. Go to next item.");
                input = scanner.nextInt();
                if (input == 1) {
                    approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                }
                else if (input == 2) {
                    approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), false);
                }
            }
        }
    }

    private void createAdmin() {
        if (currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(amp.enterName("new Admin"));
            String username = scanner.nextLine();
            try{
                System.out.println("Please enter new Administrative Users.User's password: ");
                String password = scanner.nextLine();
                am.addAdmin(username, password);
                System.out.println("New Admin Users.User " + username + " successfully created.");
            } catch (InvalidAdminException e) {
                System.out.println(amp.usernameTaken());
            }
        }
        else { System.out.println("Permission denied, only the first admin can create new administrative user accounts.");} // TO-DO: get exception to print message
    }

    private void addItemToUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("new Items.Item"));
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName);
        System.out.println(amp.enterName("Users.User"));
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
            System.out.println("Username does not exist. Please enter an existing Users.User's username."); // TO-DO: change so exception prints message
        }
    }

    private void helperChangeThreshold(String username, String whichThreshold) throws InvalidUserException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.whichThreshold(whichThreshold));
        int newThreshold = scanner.nextInt();
        um.changeThreshold(um.getUser(username), newThreshold, whichThreshold);
        System.out.println(amp.successfullyChanged(whichThreshold, username));
    }

    private void changeUserThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("Users.User"));
        String username = scanner.nextLine();
        System.out.println(amp.validOptions(amp.allThresholds));
        String whichThreshold = scanner.nextLine();
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
            System.err.print("Username does not exist. Please enter an existing Users.User's username."); // TO-DO: get exception to print this message
        }
    }

    private void checkUsers(String listType){
        Scanner scanner = new Scanner(System.in);
        if (listType.equals("pendingFrozenUsers")) {
            if (am.getPendingFrozenUsers().isEmpty()) {
                System.out.println(amp.empty("Frozen Users.User Requests"));
            }
            else {
                for (User user: am.getPendingFrozenUsers()) {
                    System.out.println(user);
                    System.out.println("1. Unfreeze Account. \n2. Go to next user.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        um.unfreezeAccount(user);
                        am.getPendingFrozenUsers().remove(user);
                        System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                    }
                }
        }
        if (listType.equals("flaggedUsers")) {
            if (am.getFlaggedAccounts().isEmpty()) {
                System.out.println(amp.empty("Flagged Users"));
            }
            else {
                for (User user: am.getFlaggedAccounts()) {
                    System.out.println(user);
                    System.out.println("1. Freeze Account. \n2.Unfreeze Account. \n3. Go to next user.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        um.freezeAccount(user);
                        am.getFrozenAccounts().add(user);
                        System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                    }
                    if (input == 2) {
                        um.unfreezeAccount(user);
                        am.getFlaggedAccounts().remove(user);
                        System.out.println(amp.accountFrozen(user.toString(), user.getStatus()));
                    }
                }
            }
    }
}}}