import java.util.*;

public class AdminMenuController {
    public AdminUser currentAdmin; // admin that's logged in
    private final AdminManager am;
    private final UserManager um;
    private HashMap<Item, User> allPendingItems;
    private final AdminMenuPresenter amp;
    private int input;

    public AdminMenuController(AdminManager adminManager, UserManager userManager,
                               HashMap<Item, User> pendingItems, AdminUser admin, AdminMenuPresenter adminPresenter) {
        currentAdmin = admin;
        allPendingItems = pendingItems;
        um = userManager;
        am = adminManager;
        amp = adminPresenter;
    }

    public void run() throws InvalidAdminException {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;

        while (userInteracting) {
            System.out.println(amp.mainMenu());
            input = scanner.nextInt();

            if (input == 1) {
                checkPendingItems();
            } else if (input == 2) {
                checkPendingUsers();
            } else if (input == 3) {
                createAdmin();
            } else if (input == 4) {
                addItemToUser();
            } else if (input == 5) {
                changeUserThreshold();
            } else if (input == 6) {
                checkFrozenAccounts("pendingFrozenUsers");
            } else if (input == 7) {
                checkFrozenAccounts("frozenUsers");
            } else if (input == 8) {
                System.out.println(amp.logout());
                // stop the while loop
                userInteracting = false;
            } else { System.out.println("Not a valid option. Please enter a valid option."); }
        }
    }

    private void approveInventory(User user, Item item, boolean approved) {
        if (approved) { um.addItem(user, item, "inventory");
        System.out.println("Item has been approved.");}
        else { allPendingItems.remove(item);
        System.out.println("Item has been declined.");}
    }

    private void checkPendingItems() {
        Scanner scanner = new Scanner(System.in);
        if (allPendingItems.isEmpty()) { amp.empty("Pending Items"); }
        else {
            Iterator<Item> itemIterator = allPendingItems.keySet().iterator();
            while (itemIterator.hasNext()) {
                System.out.println(itemIterator.next());
                System.out.println("1. Approve item for User's inventory. \n2. Decline item. \n3. Go to next item.");
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

    private void checkPendingUsers() {
        Scanner scanner = new Scanner(System.in);
        if (um.getFlaggedAccounts().isEmpty()) {
            System.out.println(amp.empty("Flagged Accounts"));
        }
        else {
            for (User user : um.getFlaggedAccounts()) {
                System.out.println(user);
                System.out.println("1. Freeze account. \n2. Unfreeze account. \n3. Go to next user.");
                input = scanner.nextInt();
                if (input == 1) {
                    um.freezeAccount(user);
                    System.out.println(amp.freeze(user.toString(), "frozen"));
                }
                else if (input == 2) {
                    um.unfreezeAccount(user);
                    System.out.println(amp.freeze(user.toString(), "active"));
                }
            }
        }
    }

    private boolean checkAvailableAdminUsername(String username) {
        for (AdminUser admin : am.getAllAdmins()) {
            if (admin.getUsername().equals(username)) {
                return false;
            }
        }
        for (User user : um.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private void createAdmin() throws InvalidAdminException {
        if (currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(amp.enterName("new Admin"));
            String username = scanner.nextLine();
            if (checkAvailableAdminUsername(username)) {
                System.out.println("Please enter new Administrative User's password: ");
                String password = scanner.nextLine();
                am.addAdmin(username, password);
                System.out.println("New Admin User " + username + " successfully created.");
            }
            else {
                System.out.println(amp.usernameTaken());
            }
        }
        else { System.out.println("Permission denied, only the first admin can create new administrative user accounts.");} // TO-DO: get exception to print message
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
            else { List<String> optionList = Arrays.asList("wishlist", "inventory");
                System.out.println(amp.validOptions(optionList));}
        } catch(InvalidUserException e) {
            System.out.println("Username does not exist. Please enter an existing User's username."); // TO-DO: change so exception prints message
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
        System.out.println(amp.enterName("User"));
        String username = scanner.nextLine();
        System.out.println(amp.validOptions(Arrays.asList("borrow", "weekly", "incomplete")));
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
                    System.out.println(amp.validOptions(Arrays.asList("borrow", "weekly", "incomplete")));
                    break;
            }
        } catch(InvalidUserException e) {
            System.err.print("Username does not exist. Please enter an existing User's username."); // TO-DO: get exception to print this message
        }
    }

    private void checkFrozenAccounts(String listType){ // TO-DO
        Scanner scanner = new Scanner(System.in);
        if (listType.equals("pendingFrozenUsers")){
            if(am.getPendingFrozenUsers().isEmpty()){
                System.out.println(amp.empty("Pending Frozen Users"));
            }
            else {
                for (User user: am.getPendingFrozenUsers()) {
                    System.out.println(user);
                    System.out.println("1. Unfreeze Account. \n2. Go to next user.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        um.unfreezeAccount(user);
                        System.out.println(amp.freeze(user.toString(), "active"));
                    }
                }
            }
        }
        else{
            if(am.getFrozenAccounts().isEmpty()){
                System.out.println(amp.empty("Frozen Users"));
            }
            else {
                for (User user: am.getFrozenAccounts()) {
                    System.out.println(user);
                    System.out.println("1. Unfreeze Account. \n2. Go to next user.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        um.unfreezeAccount(user);
                        System.out.println(amp.freeze(user.toString(), "active"));
                    }
                }
            }
        }
    }
}
