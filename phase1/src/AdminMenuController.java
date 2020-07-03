import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AdminMenuController {
    private AdminMenu am;
    private AdminMenuPresenter amp;
    private int input;

    public void run() {
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
                System.out.println("You have successfully logged out.");
                // stop the while loop
                userInteracting = false;
            } else { System.out.println("Not a valid option. Please enter a valid option."); }
        }
    }

    private void checkPendingItems() {
        Scanner scanner = new Scanner(System.in);
        if (am.getAllPendingItems().isEmpty()) {
            System.out.println(amp.empty("Pending Items"));
        }
        else {
            Iterator<Item> itemIterator = am.getAllPendingItems().keySet().iterator();
            while (itemIterator.hasNext()) {
                System.out.println(itemIterator.next());
                System.out.println("1. Approve item for User's inventory. \n2. Decline item. \n3. Go to next item.");
                input = scanner.nextInt();
                if (input == 1) {
                    am.checkPendingItems(am.getAllPendingItems().get(itemIterator.next()), itemIterator.next(), true);
                    System.out.println("Item has been approved."); // TO-DO optionally: make this print the item and user name
                }
                else if (input == 2) {
                    am.checkPendingItems(am.getAllPendingItems().get(itemIterator.next()), itemIterator.next(), false);
                    System.out.println("Item has been declined.");
                }
            }
        }
    }

    private void checkPendingUsers() {
        Scanner scanner = new Scanner(System.in);
        if (am.um.getFlaggedAccounts().isEmpty()) {
            System.out.println(amp.empty("Flagged Accounts"));
        }
        else {
            for (User user : am.um.getFlaggedAccounts()) {
                System.out.println(user); // TO-DO: how can we print why this user's account has been flagged?
                System.out.println("1. Freeze account. \n2. Unfreeze account. \n3. Go to next user.");
                input = scanner.nextInt();
                if (input == 1) {
                    am.checkPendingUsers(user, true);
                    System.out.println(user.getUsername() + "'s account has been set to frozen.");
                }
                else if (input == 2) {
                    am.checkPendingUsers(user, false);
                    System.out.println(user.getUsername() + "'s account has been set to active.");
                }
            }
        }
    }

    private void createAdmin() {
        if (am.currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(amp.enterName("new Admin"));
            String username = scanner.nextLine();
            System.out.println("Please enter new Administrative User's password: ");
            String password = scanner.nextLine();
            try {
                am.createNewAdmin(username, password);
                System.out.println("New Admin User " + username + " successfully created.");
            } catch(InvalidAdminException e) {
                System.out.println("Username already taken.");
            }
        }
        else { System.out.println("Permission denied, only the first admin can create new administrative user accounts.");}
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
                am.addItem(username, newItem, "wishlist");
                System.out.println(amp.successfullyAdded(newItem.toString(), username, "wishlist"));
            }
            else if (whichList.equals("inventory")) {
                am.addItem(username, newItem, "inventory");
                System.out.println(amp.successfullyAdded(newItem.toString(), username, "inventory"));
            }
            else { List<String> optionList = Arrays.asList("wishlist", "inventory");
                System.out.println(amp.validOptions(optionList));}
        } catch(InvalidUserException e) {
            System.out.println("Username does not exist. Please enter an existing User's username.");
        }
    }

    private void changeUserThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(amp.enterName("User"));
        String username = scanner.nextLine();
        List<String> optionList = Arrays.asList("borrow", "weekly", "incomplete");
        System.out.println(amp.validOptions(optionList));
        String whichThreshold = scanner.nextLine();
        try {
            switch (whichThreshold) {
                case "borrow": {
                    System.out.println("The current minimum number of times that this user must lend something before they can borrow/trade is: " + am.um.getUser(username).getBorrowThreshold());
                    System.out.println(amp.whichThreshold("borrow"));
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.um.getUser(username), newThreshold, "borrow");
                    System.out.println(amp.successfullyChanged(whichThreshold, username));
                    break;
                }
                case "weekly": {
                    System.out.println("The current maximum number of transactions that this user can participate in a week is: " + am.um.getUser(username).getWeeklyThreshold());
                    System.out.println(amp.whichThreshold("weekly"));
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.um.getUser(username), newThreshold, "weekly");
                    System.out.println(amp.successfullyChanged(whichThreshold, username));
                    break;
                }
                case "incomplete": {
                    System.out.println("The current maximum number of incomplete transactions before this user's account is frozen is: " + am.um.getUser(username).getIncompleteThreshold());
                    System.out.println(amp.whichThreshold("incomplete"));
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.um.getUser(username), newThreshold, "incomplete");
                    System.out.println(amp.successfullyChanged(whichThreshold, username));
                    break;
                }
                default:
                    System.out.println(amp.validOptions(optionList));
                    break;
            }
        } catch(InvalidUserException e) {
            System.err.print("Username does not exist. Please enter an existing User's username.");
        }
    }

    private void checkPendingFrozenAccounts(){
        Scanner scanner = new Scanner(System.in);
        if(am.getPendingFrozenUsers().isEmpty()){
            System.out.println(amp.empty("Pending Frozen Users"));
        }
        else {
            for (User user: am.getPendingFrozenUsers()) {
                System.out.println(user);
                System.out.println("1. Unfreeze Account. \n2. Go to next user.");
                input = scanner.nextInt();
                if (input == 1) {
                    am.um.unfreezeAccount(user);
                    System.out.println(user.getUsername() + "'s  account has been set to active.");
                }
            }
        }
    }
}
