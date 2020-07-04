import java.util.*;

public class AdminMenuController {
    public AdminUser currentAdmin; // admin that's logged in
    private AdminManager am;
    public UserManager um;
    private HashMap<Item, User> allPendingItems;
    private List<User> pendingFrozenUsers;
    private AdminMenuPresenter amp;
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
            amp.mainMenu();
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
                System.out.println("You have successfully logged out.");
                // stop the while loop
                userInteracting = false;
            } else { System.out.println("Not a valid option. Please enter a valid option."); }
        }
    }

    private void approveInventory(User user, Item item, boolean approved) {
        if (approved) { um.addItem(user, item, "inventory"); }
        else { allPendingItems.remove(item); }
    }

    private void checkPendingItems() {
        Scanner scanner = new Scanner(System.in);
        if (allPendingItems.isEmpty()) {
            amp.empty("Pending Items");
        }
        else {
            Iterator<Item> itemIterator = allPendingItems.keySet().iterator();
            while (itemIterator.hasNext()) {
                System.out.println(itemIterator.next());
                System.out.println("1. Approve item for User's inventory. \n2. Decline item. \n3. Go to next item.");
                input = scanner.nextInt();
                if (input == 1) {
                    approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                    System.out.println("Item has been approved."); // TO-DO optionally: make this print the item and user name
                }
                else if (input == 2) {
                    approveInventory(allPendingItems.get(itemIterator.next()), itemIterator.next(), false);
                    System.out.println("Item has been declined.");
                }
            }
        }
    }

    public void freezeUser(User user, boolean freeze) {
        if (freeze) { um.freezeAccount(user); }
        else { um.unfreezeAccount(user); }
    }

    private void checkPendingUsers() {
        Scanner scanner = new Scanner(System.in);
        if (um.getFlaggedAccounts().isEmpty()) {
            amp.empty("Flagged Accounts");
        }
        else {
            for (User user : um.getFlaggedAccounts()) {
                System.out.println(user); // TO-DO: how can we print why this user's account has been flagged?
                System.out.println("1. Freeze account. \n2. Unfreeze account. \n3. Go to next user.");
                input = scanner.nextInt();
                if (input == 1) {
                    freezeUser(user, true);
                    System.out.println(user.getUsername() + "'s account has been set to frozen.");
                }
                else if (input == 2) {
                    freezeUser(user, false);
                    System.out.println(user.getUsername() + "'s account has been set to active.");
                }
            }
        }
    }

    private boolean checkAvailableAdminUsername(String username) {
        for (AdminUser admin : am.getAllAdmins()) { // TO-DO: this should iterate through Admin and User usernames
            if (admin.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private void createAdmin() throws InvalidAdminException {
        if (currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            amp.enterName("new Admin");
            String username = scanner.nextLine();
            if (checkAvailableAdminUsername(username)) {
                System.out.println("Please enter new Administrative User's password: ");
                String password = scanner.nextLine();
                am.addAdmin(username, password);
                System.out.println("New Admin User " + username + " successfully created.");
            }
            else {
                amp.usernameTaken();
            }
        }
        else { System.out.println("Permission denied, only the first admin can create new administrative user accounts.");} // TO-DO: get exception to print message
    }

    private void addItemToUser() {
        Scanner scanner = new Scanner(System.in);
        amp.enterName("new Item");
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName);
        amp.enterName("User");
        String username = scanner.nextLine();
        System.out.println("Would you like to add this item to the user's wishlist or inventory?");
        String whichList = scanner.nextLine();
        try {
            if (whichList.equals("wishlist")) {
                um.addItem(um.getUser(username), newItem, "wishlist");
                amp.successfullyAdded(newItem.toString(), username, "wishlist");
            }
            else if (whichList.equals("inventory")) {
                um.addItem(um.getUser(username), newItem, "wishlist");
                amp.successfullyAdded(newItem.toString(), username, "inventory");
            }
            else { List<String> optionList = Arrays.asList("wishlist", "inventory");
                amp.validOptions(optionList);}
        } catch(InvalidUserException e) {
            System.out.println("Username does not exist. Please enter an existing User's username."); // TO-DO: change so exception prints message
        }
    }

    private void changeUserThreshold() {
        Scanner scanner = new Scanner(System.in);
        amp.enterName("User");
        String username = scanner.nextLine();
        List<String> optionList = Arrays.asList("borrow", "weekly", "incomplete");
        amp.validOptions(optionList);
        String whichThreshold = scanner.nextLine();
        try {
            switch (whichThreshold) {
                case "borrow": {
                    amp.currentThreshold("minimum number of times that this user must lend something before they can borrow/trade", um.getUser(username).getBorrowThreshold());
                    amp.whichThreshold("borrow");
                    int newThreshold = scanner.nextInt();
                    um.changeThreshold(um.getUser(username), newThreshold, "borrow");
                    amp.successfullyChanged(whichThreshold, username);
                    break;
                }
                case "weekly": {
                    amp.currentThreshold("maximum number of transactions that this user can participate in a week", um.getUser(username).getWeeklyThreshold());
                    amp.whichThreshold("weekly");
                    int newThreshold = scanner.nextInt();
                    um.changeThreshold(um.getUser(username), newThreshold, "weekly");
                    amp.successfullyChanged(whichThreshold, username);
                    break;
                }
                case "incomplete": {
                    amp.currentThreshold("maximum number of incomplete transactions before this user's account is frozen", um.getUser(username).getIncompleteThreshold());
                    amp.whichThreshold("incomplete");
                    int newThreshold = scanner.nextInt();
                    um.changeThreshold(um.getUser(username), newThreshold, "incomplete");
                    amp.successfullyChanged(whichThreshold, username);
                    break;
                }
                default:
                    amp.validOptions(optionList);
                    break;
            }
        } catch(InvalidUserException e) {
            System.err.print("Username does not exist. Please enter an existing User's username.");
        }
    }

    private void checkPendingFrozenAccounts(){
        Scanner scanner = new Scanner(System.in);
        if(am.getPendingFrozenUsers().isEmpty()){
            amp.empty("Pending Frozen Users");
        }
        else {
            for (User user: am.getPendingFrozenUsers()) {
                System.out.println(user);
                System.out.println("1. Unfreeze Account. \n2. Go to next user.");
                input = scanner.nextInt();
                if (input == 1) {
                    um.unfreezeAccount(user);
                    System.out.println(user.getUsername() + "'s  account has been set to active.");
                }
            }
        }
    }
}
