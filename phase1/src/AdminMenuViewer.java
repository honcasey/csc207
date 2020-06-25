import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class AdminMenuViewer {
    private AdminMenu am;
    private int input;

    public AdminMenuViewer(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public void run() {
        System.out.println("1. Check Pending Items for Approval");
        System.out.println("2. Check Pending Users for Approval");
        System.out.println("3. Create New Admin User");
        System.out.println("4. Add Item to User Wishlist/Inventory");
        System.out.println("5. Change User Threshold");
        System.out.println("6. Logout");

        Scanner scanner = new Scanner(System.in);
        boolean variable = true;

        while (variable) {
            System.out.println("Pick an option.");
            input = scanner.nextInt();

            if (input == 1) {
                Iterator<Item> itemIterator = am.allPendingItems.keySet().iterator();

                while (itemIterator.hasNext()) {
                    System.out.println(itemIterator.next());
                    System.out.println("1. Approve item for User's inventory."); // TO-DO optionally: make this print the User's username
                    System.out.println("2. Decline item.");
                    System.out.println("3. Go to next item.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        am.checkPendingItems(am.allPendingItems.get(itemIterator.next()), itemIterator.next(), true);
                    }
                    else if (input == 2) {
                        am.checkPendingItems(am.allPendingItems.get(itemIterator.next()), itemIterator.next(), false);
                    }
                }

            } else if (input == 2) {
                for (User user : am.allPendingUsers) {
                    System.out.println(user); // TO-DO: how can we print why this user's account has been flagged?
                    System.out.println("1. Freeze account.");
                    System.out.println("2. Unfreeze account.");
                    System.out.println("3. Go to next user.");
                    input = scanner.nextInt();
                    if (input == 1) {
                        am.checkPendingUsers(user, true);
                    }
                    else if (input == 2) {
                        am.checkPendingUsers(user, false);
                    }
                }
            } else if (input == 3) {
                // TO-DO: check if this user is the firstAdmin before they can add new admins
                System.out.println("Please enter new Administrative User's username: ");
                String username = scanner.nextLine();
                while (!am.checkAvailableAdminUsername(username)) {
                    System.out.println("Username already taken.");
                }
                System.out.println("Please enter new Administrative User's password: ");
                String password = scanner.nextLine();
                am.createNewAdmin(username, password);
                System.out.println("New Admin User " + username + " successfully created.");


            } else if (input == 4) {
                // am.addItem()
            } else if (input == 5) {

                // am.changeThreshold()
            } else if (input == 6) {
                System.out.println("You have successfully logged out.");
                // stop the while loop
                variable = false;
            }
            else { System.out.println("Not a valid option."); }
        }
    }
}
